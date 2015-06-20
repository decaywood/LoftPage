package org.decaywood.buffer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.decaywood.entity.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @author: decaywood
 * @date: 2015/6/20
 *
 */

@Component(value = "MultiSendersBuffer")
public class MultiSendersBuffer extends MainBuffer {


    /**
     * generate the handlers like logger handler, sql handler and so on
     */
    @FunctionalInterface
    public interface HandlerGenerator {
        EventHandler<KeyEvent>[] generateEventHandlers();
    }

    public MultiSendersBuffer() {
        HandlerGenerator generator = () -> {
            return null;
        };
    }

    public MultiSendersBuffer(HandlerGenerator generator) {
        this(1 << 10, 2, generator);
    }

    /**
     *
     * @param bufferSize ringBuffer Size
     * @param consumerFactor use a WorkerPool to allow multiple pooled worker threads to work on a single consumer
     *                       worker count is consumerFactor multiply availableProcessors count
     */
    public MultiSendersBuffer(int bufferSize, int consumerFactor, HandlerGenerator generator) {
        buildRingBuffer(() -> initBuffer(bufferSize, consumerFactor, generator));
    }




    public WorkHandler<KeyEvent>[] initSenders(int senderSize) {
        WorkHandler[] workHandlers = new WorkHandler[senderSize];
        for (int i = 0; i < senderSize; i++) {
            workHandlers[i] = new KeyEventSender();
        }

        return workHandlers;
    }

    private RingBuffer<KeyEvent> initBuffer(int bufferSize, int consumerFactor, HandlerGenerator generator) {

        int size = bufferSize > 0 ? bufferSize : 1 << 10;

        Disruptor<KeyEvent> disruptor = new Disruptor<>(
                KeyEvent::new,
                size,
                Executors.newCachedThreadPool(),
                ProducerType.MULTI,
                new SleepingWaitStrategy());

        int threadsCount = Math.max(1, Runtime.getRuntime().availableProcessors());
        int consumerCount = threadsCount * consumerFactor;

        WorkHandler<KeyEvent>[] senders = initSenders(consumerCount);

        disruptor.handleEventsWithWorkerPool(senders).then(generator.generateEventHandlers());
        disruptor.handleExceptionsWith(new BufferExceptionHandler());
        return disruptor.start();

    }
}
