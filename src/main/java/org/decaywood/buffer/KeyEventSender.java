package org.decaywood.buffer;

import com.lmax.disruptor.EventHandler;
import org.decaywood.entity.KeyEvent;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */
public class KeyEventSender implements EventHandler<KeyEvent> {

    private static LongAdder handlerIDGenerator = new LongAdder();

    private long handlerID;

    public KeyEventSender() {
        handlerID = handlerIDGenerator.longValue();
        handlerIDGenerator.increment();
    }

    @Override
    public void onEvent(KeyEvent event, long sequence, boolean endOfBatch) throws Exception {
        execute(event, sequence, endOfBatch);
    }



    private void execute(KeyEvent event, long sequence, boolean endOfBatch) {

        System.out.println("Event -> " + event +"Sequence -> " + sequence + "Batch -> " + endOfBatch);
    }
}
