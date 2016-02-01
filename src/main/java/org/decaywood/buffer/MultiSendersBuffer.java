package org.decaywood.buffer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.decaywood.KeyEvent;
import org.decaywood.buffer.handler.KeyEventSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/6/20
 *
 */

@Component(value = "MultiSendersBuffer")
public class MultiSendersBuffer extends MainBuffer {

    private int bufferSize;
    private Supplier<EventHandler<KeyEvent>[]> generator;

    @Resource
    public void setKeyEventSender(KeyEventSender sender) {
        this.keyEventSender = sender;
        initBuffer(bufferSize, generator);
    }
    private KeyEventSender keyEventSender;

    public MultiSendersBuffer() {
        this(() -> new EventHandler[]{});
    }

    /**
     * generate the handlers like logger handler, sql handler and so on
     */

    public MultiSendersBuffer(Supplier<EventHandler<KeyEvent>[]> generator) {
        this(1 << 10, generator);
    }

    /**
     * @param bufferSize ringBuffer Size
     */
    public MultiSendersBuffer(int bufferSize, Supplier<EventHandler<KeyEvent>[]> generator) {
        this.bufferSize = bufferSize;
        this.generator = generator;
    }

    private void initBuffer(int bufferSize, Supplier<EventHandler<KeyEvent>[]> generator) {

        int size = bufferSize > 0 ? bufferSize : 1 << 10;

        Disruptor<KeyEvent> disruptor = new Disruptor<>(
                KeyEvent::new,
                size,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new SleepingWaitStrategy());

        EventHandler<KeyEvent>[] src = generator.get();
        EventHandler[] handlers = new EventHandler[src.length + 1];
        handlers[0] = keyEventSender;
        System.out.println("key event sender : " + keyEventSender);
        System.arraycopy(src, 0, handlers, 1, src.length);
        System.out.println("handler len : " + handlers.length);
        disruptor.handleEventsWith(handlers);

        this.ringBuffer = disruptor.start();

    }
}
