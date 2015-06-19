package org.decaywood.buffer;

import com.lmax.disruptor.ExceptionHandler;
import org.decaywood.entity.KeyEvent;

/**
 * @author: decaywood
 * @date: 2015/6/19 15:33
 */
public class BufferExceptionHandler implements ExceptionHandler<KeyEvent> {

    @Override
    public void handleEventException(Throwable ex, long sequence, KeyEvent event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }

}
