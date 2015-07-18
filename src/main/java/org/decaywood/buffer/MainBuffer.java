package org.decaywood.buffer;

import com.lmax.disruptor.RingBuffer;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;
import java.util.Optional;

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

    protected Optional<ConnectionManager> manager;

    protected Optional<SimpMessagingTemplate> simpMessagingTemplate;

    protected Optional<KeyEventSequencer> sequencer;

    /**
     * used for lazy init
     */
    protected BufferGenerator generator;

    protected RingBuffer<KeyEvent> ringBuffer;


    /**
     * it is used to manage the connection, mapping URL between two gamer
     */
    @Resource(name = "ConnectionManager")
    public void setManager(ConnectionManager manager) {
        this.manager = Optional.of(manager);
        buildBuffer();
    }

    @Resource
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = Optional.of(simpMessagingTemplate);
        buildBuffer();
    }

    /**
     * mainBuffer would disorder the keyEvent,sequencer can
     * reorder the keyEvent passed through the mainBuffer
     */
    @Resource(name = "KeyEventSequencer")
    public void setSequencer(KeyEventSequencer sequencer) {
        this.sequencer = Optional.of(sequencer);
        buildBuffer();
    }



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

    protected void buildBuffer() {
        if(manager.isPresent() && simpMessagingTemplate.isPresent() && sequencer.isPresent())
            this.ringBuffer = this.generator.initRingBuffer(
                    manager.get(),
                    simpMessagingTemplate.get(),
                    sequencer.get());
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
