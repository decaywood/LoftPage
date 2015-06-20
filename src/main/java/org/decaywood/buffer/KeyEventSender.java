package org.decaywood.buffer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.decaywood.entity.KeyEvent;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */
public class KeyEventSender implements WorkHandler<KeyEvent> {

    private static LongAdder longAdder = new LongAdder();

    private static LongAdder handlerIDGenerator = new LongAdder();

    private long handlerID;

    public KeyEventSender() {
        handlerID = handlerIDGenerator.longValue();
        handlerIDGenerator.increment();
    }



    @Override
    public void onEvent(KeyEvent event) throws Exception {
        execute(event, 0, true);
    }

    private void execute(KeyEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        longAdder.increment();
        System.out.println("count : " + longAdder.intValue() + "  thread: " + Thread.currentThread().getId());
//        System.out.println("Event -> " + event +"Sequence -> " + sequence
//                + "Batch -> " + endOfBatch + "  thread ====> " + Thread.currentThread().getId());
    }


}
