package org.decaywood.buffer.handler;

import com.lmax.disruptor.EventHandler;
import org.decaywood.entity.KeyEvent;
import org.decaywood.entity.User;
import org.decaywood.service.UserService;
import org.decaywood.utils.CommonUtils;
import org.decaywood.utils.cache.ICache;
import org.decaywood.utils.cache.LRUCache;
import org.decaywood.utils.cache.TimeStampCache;

import javax.annotation.Resource;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/6/20
 *
 */

/**
 * use cache to cache the processed data, if cache doesn't contain the data, we say
 * that the data is out of date.
 */
public class RepositoryHandler implements EventHandler<KeyEvent> {


    public enum CacheType {

        TIME_STAMP_CACHE(() -> new TimeStampCache<Long, KeyEvent>(1000 * 60)), // timeout : 1 min
        LRU_CACHE(() -> new LRUCache<Long, KeyEvent>(500));

        CacheType(Supplier<ICache<Long, KeyEvent>> supplier) {
            this.supplier = supplier;
        }

        /**
         * use function programming style to ensure lazy init
         */
        private Supplier<ICache<Long, KeyEvent>> supplier;

        private ICache<Long, KeyEvent> getInstance() {
            return supplier.get();
        }
    }

    public interface EventFilter {
        boolean filter(ICache<Long, KeyEvent> cache, KeyEvent event);
    }

    private EventFilter eventFilter;
    private ICache<Long, KeyEvent> cache;


    @Resource(name = "userService")
    private UserService userService;

    public RepositoryHandler() {
        this((cache, event) -> cache.containsKey(
                CommonUtils.generateHashCode(event.getIPAddress(), event.getUserID())));
    }

    public RepositoryHandler(EventFilter filter) {
        this(filter, CacheType.LRU_CACHE);
    }

    public RepositoryHandler(EventFilter filter, Supplier<ICache<Long, KeyEvent>> cacheSupplier) {
        this(filter, cacheSupplier.get());
    }

    public RepositoryHandler(EventFilter filter, CacheType cacheType) {
        this(filter, cacheType.getInstance());
    }

    public RepositoryHandler(EventFilter filter, ICache<Long, KeyEvent> cache) {
        this.eventFilter = filter;
        this.cache = cache;
    }





    @Override
    public void onEvent(KeyEvent event, long sequence, boolean endOfBatch) throws Exception {
        execute(event);
    }

    private void execute(KeyEvent event) {

        if(eventFilter.filter(cache, event)) return;

        User user = new User();
        matchUserInfo(event, user);
        this.userService.updateUser(user);

    }

    private void matchUserInfo(KeyEvent keyEvent, User user) {

        user.setUserID(keyEvent.getUserDatabaseID());
        user.setUserEmail(keyEvent.getUserEmail());
        user.setUserIPAddress(keyEvent.getIPAddress());
        user.setUserLoginName(keyEvent.getUserLoginName());
        user.setUserHighestScore(keyEvent.getHighestScore());


    }


}
