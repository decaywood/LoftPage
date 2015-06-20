package org.decaywood.buffer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.decaywood.entity.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @author: decaywood
 * @date: 2015/6/19 10:42
 */

@Component
public abstract class MainBuffer {

    private RingBuffer<KeyEvent> ringBuffer;

    public MainBuffer() {}


    /**
     * generate different configured ring buffer
     */
    @FunctionalInterface
    protected interface BufferGenerator {
        RingBuffer<KeyEvent> initRingBuffer();
    }

    protected void buildRingBuffer(BufferGenerator generator) {
        this.ringBuffer = generator.initRingBuffer();
    }


    public void publishKeyEvent(KeyEvent event) {

        long sequence = this.ringBuffer.next();
        try {
            KeyEvent keyEvent = this.ringBuffer.get(sequence);
            setKeyEventValue(event, keyEvent);
        } finally {
            this.ringBuffer.publish(sequence); // ensure that event can be published
        }

    }


    /*
        method to copy KeyEvent
     */
    private void setKeyEventValue(KeyEvent publisher, KeyEvent acceptor) {
        acceptor.setAltKey(publisher.getAltKey());
        acceptor.setCtrlKey(publisher.getCtrlKey());
        acceptor.setMetaKey(publisher.getMetaKey());
        acceptor.setShiftKey(publisher.getShiftKey());
        acceptor.setWhich(publisher.getWhich());
    }

}
