package org.decaywood.buffer;

import com.lmax.disruptor.RingBuffer;
import org.decaywood.KeyEvent;

/**
 * @author: decaywood
 * @date: 2015/6/19 10:42
 *
 * the super class in which the disruptor is embedded and configured
 *
 * ringBuffer can be init (lazy) via BufferGenerator which is offered by subclass
 *
 */

public abstract class MainBuffer {

    protected RingBuffer<KeyEvent> ringBuffer;

    public MainBuffer() {
        System.out.println("init");
    }


    public void publishKeyEvent(KeyEvent event) {
        System.out.println("publish Event");
        long sequence = this.ringBuffer.next();
        try {
            KeyEvent keyEvent = this.ringBuffer.get(sequence);
            keyEvent.copyOf(event);
        } finally {
            this.ringBuffer.publish(sequence); // ensure that event can be published
        }

    }


}
