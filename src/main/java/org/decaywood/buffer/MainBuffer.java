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
public class MainBuffer {

    private RingBuffer<KeyEvent> ringBuffer;

    public MainBuffer() {
        this(1 << 10, 10);
    }



    public MainBuffer(int bufferSize, int consumerFactor) {

        int size = bufferSize > 0 ? bufferSize : 1 << 10;

        Disruptor<KeyEvent> disruptor = new Disruptor<>(
                KeyEvent::new,
                size,
                Executors.newCachedThreadPool(),
                ProducerType.MULTI,
                new SleepingWaitStrategy());

        int threadsCount = Math.max(1, Runtime.getRuntime().availableProcessors());
        int consumerCount = threadsCount * consumerFactor;

        KeyEventSender[] senders = initSenders(consumerCount);

        disruptor.handleEventsWith(senders);
        disruptor.handleExceptionsWith(new BufferExceptionHandler());
        this.ringBuffer = disruptor.start();

    }


    private KeyEventSender[] initSenders(int consumerCount) {
        KeyEventSender[] senders = new KeyEventSender[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            senders[i] = new KeyEventSender();
        }
        return senders;
    }


    public void publishKeyEvent(KeyEvent event) {

        long sequence = this.ringBuffer.next();
        KeyEvent keyEvent = this.ringBuffer.get(sequence);
        setKeyEventValue(event, keyEvent);
        this.ringBuffer.publish(sequence);

    }

    private void setKeyEventValue(KeyEvent publisher, KeyEvent acceptor) {
        acceptor.setAltKey(publisher.getAltKey());
        acceptor.setCtrlKey(publisher.getCtrlKey());
        acceptor.setMetaKey(publisher.getMetaKey());
        acceptor.setShiftKey(publisher.getShiftKey());
        acceptor.setWhich(publisher.getWhich());
    }

}
