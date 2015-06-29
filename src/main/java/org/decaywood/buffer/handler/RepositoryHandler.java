package org.decaywood.buffer.handler;

import com.lmax.disruptor.EventHandler;
import org.decaywood.entity.KeyEvent;
import org.decaywood.entity.User;
import org.decaywood.service.UserService;
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
public class RepositoryHandler implements EventHandler<KeyEvent> {


    public enum CacheType {

        TIME_STAMP_CACHE(() -> new TimeStampCache<String, KeyEvent>(1000 * 60)), // timeout : 1 min
        LRU_CACHE(() -> new LRUCache<String, KeyEvent>(500));

        CacheType(Supplier<ICache<String, KeyEvent>> supplier) {
            this.supplier = supplier;
        }

        private Supplier<ICache<String, KeyEvent>> supplier;

        private ICache<String, KeyEvent> getInstance() {
            return supplier.get();
        }
    }

    public interface EventFilter {
        boolean filter(ICache<String, KeyEvent> cache, KeyEvent event);
    }

    private EventFilter eventFilter;
    private ICache<String, KeyEvent> cache;


    @Resource(name = "userService")
    private UserService userService;

//    public RepositoryHandler() {
//        this((cache, event) -> {
//            return !cache.containsKey(event.getUserID()) || event.getGameState().equalsIgnoreCase("Terminate");
//        });
//    }

    public RepositoryHandler(EventFilter filter) {
        this(filter, CacheType.LRU_CACHE);
    }

    public RepositoryHandler(EventFilter filter, Supplier<ICache<String, KeyEvent>> cacheSupplier) {
        this(filter, cacheSupplier.get());
    }

    public RepositoryHandler(EventFilter filter, CacheType cacheType) {
        this(filter, cacheType.getInstance());
    }

    public RepositoryHandler(EventFilter filter, ICache<String, KeyEvent> cache) {
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
//        user.setUserID(event.getUserID()).setUserHighestScore(event.getHighestScore());

//        this.userService.

    }

}
