package org.decaywood.buffer.handler;

import com.lmax.disruptor.EventHandler;
import jodd.cache.TimedCache;
import org.decaywood.entity.KeyEvent;
import org.decaywood.entity.User;
import org.decaywood.service.UserService;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/20
 *
 */
public class RepositoryHandler implements EventHandler<KeyEvent> {

    private EventFilter eventFilter;
//    private TimedCache

    @Resource(name = "userService")
    private UserService userService;

    public RepositoryHandler(EventFilter filter) {
        this.eventFilter = filter;
    }

    public interface EventFilter {
        boolean filter(KeyEvent event);
    }



    @Override
    public void onEvent(KeyEvent event, long sequence, boolean endOfBatch) throws Exception {
        execute(event);
    }

    private void execute(KeyEvent event) {

        if(!eventFilter.filter(event)) return;

        User user = this.userService.queryByUserID(event.getUserID());


    }

}
