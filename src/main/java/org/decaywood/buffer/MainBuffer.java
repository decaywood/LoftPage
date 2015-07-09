package org.decaywood.buffer;

import com.lmax.disruptor.RingBuffer;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/19 10:42
 */

public abstract class MainBuffer {

    protected ConnectionManager manager;

    protected SimpMessagingTemplate simpMessagingTemplate;

    protected KeyEventSequencer sequencer;

    private BufferGenerator generator;

    @Resource(name = "ConnectionManager")
    public void setManager(ConnectionManager manager) {
        this.manager = manager;
        buildBuffer();
    }
    @Resource
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        buildBuffer();
    }

    @Resource(name = "KeyEventSequencer")
    public void setSequencer(KeyEventSequencer sequencer) {
        this.sequencer = sequencer;
        buildBuffer();
    }

    private RingBuffer<KeyEvent> ringBuffer;

    public MainBuffer() {}


    /**
     * generate different configured ring buffer
     */
    @FunctionalInterface
    protected interface BufferGenerator {
        RingBuffer<KeyEvent> initRingBuffer(ConnectionManager manager,
                                            SimpMessagingTemplate simpMessagingTemplate,
                                            KeyEventSequencer sequencer);
    }

    protected void setGenerator(BufferGenerator generator) {
        this.generator = generator;
    }

    private void buildBuffer() {
        if(manager != null && simpMessagingTemplate != null && sequencer != null)
            this.ringBuffer = this.generator.initRingBuffer(manager, simpMessagingTemplate, sequencer);
    }


    public void publishKeyEvent(KeyEvent event) {

        long sequence = this.ringBuffer.next();
        try {
            KeyEvent keyEvent = this.ringBuffer.get(sequence);
            keyEvent.copyOf(event);
        } finally {
            this.ringBuffer.publish(sequence); // ensure that event can be published
        }

    }


}
