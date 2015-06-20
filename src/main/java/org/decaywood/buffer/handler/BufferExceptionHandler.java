package org.decaywood.buffer.handler;

import com.lmax.disruptor.ExceptionHandler;
import org.apache.log4j.Logger;
import org.decaywood.entity.KeyEvent;

/**
 * @author: decaywood
 * @date: 2015/6/19 15:33
 */
public class BufferExceptionHandler implements ExceptionHandler<KeyEvent> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void handleEventException(Throwable ex, long sequence, KeyEvent event) {
        logger.error(ex.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        logger.error(ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        logger.error(ex.getMessage());
    }

}
