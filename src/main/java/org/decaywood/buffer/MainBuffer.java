package org.decaywood.buffer;

import com.lmax.disruptor.RingBuffer;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/19 10:42
 */

public abstract class MainBuffer {

    protected ConnectionManager manager;

    protected SimpMessagingTemplate simpMessagingTemplate;

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

    private RingBuffer<KeyEvent> ringBuffer;

    public MainBuffer() {}


    /**
     * generate different configured ring buffer
     */
    @FunctionalInterface
    protected interface BufferGenerator {
        RingBuffer<KeyEvent> initRingBuffer(ConnectionManager manager, SimpMessagingTemplate simpMessagingTemplate);
    }

    protected void setGenerator(BufferGenerator generator) {
        this.generator = generator;
    }

    private void buildBuffer() {
        if(manager != null && simpMessagingTemplate != null)
            this.ringBuffer = this.generator.initRingBuffer(manager, simpMessagingTemplate);
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
        acceptor.setIPAddress(publisher.getIPAddress());
        acceptor.setUserID(publisher.getUserID());
        acceptor.setHighestScore(publisher.getHighestScore());

    }

}
