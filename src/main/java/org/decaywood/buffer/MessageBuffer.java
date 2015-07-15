package org.decaywood.buffer;

import org.decaywood.entity.KeyEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/6/18 20:42
 */

@Component(value = "MessageBuffer")
public class MessageBuffer implements Runnable {

    @Resource(name = "MultiSendersBuffer")
    private MainBuffer mainBuffer;

    /**
     * Queue is maintained by bufferPool, Queues can reduce the thread write conflict
     *
     *
     *  client -> Queue 1                                                client
     *                   \__                                          __/
     *                      \__             __________             __/
     *                         \           |          |           /
     *  client -> Queue 2-------+==========*MainBuffer*==> Sender =====+------ client
     *              *        __/           |__________|           \__
     *              *     __/                                        \__
     *              *    /                                              \
     *  client -> Queue N                                                client
     */


    private ConcurrentHashMap<Integer, Queue<KeyEvent>> bufferPool;
    private ExecutorService service;
    private LongAdder messageCounter;

    /**
     * if the average buffer size overweight the threshold
     */
    private int bufferIncreaseThreshold;
    private int flushThreshold;



    public MessageBuffer() {
        this(1 << 10, 1, Runtime.getRuntime().availableProcessors());
    }


    /**
     *
     * @param bufferIncreaseThreshold when average size per buffer outweight the shredshold,
     *                                new buffer will be added into the pool.
     * @param flushThreshold when total message in the buffer pool outweight the shredshold,
     *                       every buffer will be flushed into main buffer.
     * @param cacheSize the origin buffer count.
     *
     */
    public MessageBuffer(int bufferIncreaseThreshold, int flushThreshold, int cacheSize) {

        init(bufferIncreaseThreshold, flushThreshold, cacheSize);

    }

    private void init(int bufferIncreaseThreshold, int flushThreshold, int cacheSize) {

        this.service = Executors.newSingleThreadExecutor();

        this.bufferIncreaseThreshold = bufferIncreaseThreshold > 0 ? bufferIncreaseThreshold : 1 << 8;
        this.flushThreshold = flushThreshold;

        this.messageCounter = new LongAdder();
        this.bufferPool = new ConcurrentHashMap<>();
        for (int i = 0; i < cacheSize; i++) {
            this.bufferPool.put(i, new ConcurrentLinkedDeque<>());
        }

    }



    public void publishEvent(KeyEvent keyEvent) {

        messageCounter.increment();
        Queue<KeyEvent> buffer = getMinBuffer();
        buffer.offer(keyEvent);
        flushBufferToMainBuffer();

    }

    private void flushBufferToMainBuffer() {

        if(messageCounter.intValue() < flushThreshold) return;
        this.service.execute(this);

    }

    private Queue<KeyEvent> getMinBuffer() {
        if(averageSize() > this.bufferIncreaseThreshold){
            this.bufferPool.put(this.bufferPool.size(), new ConcurrentLinkedDeque<>());
        }
        return bufferPool.reduceValues(
                Integer.MAX_VALUE,
                (first, second) -> first.size() < second.size() ? first : second);
    }
    

    private int averageSize() {
        int sizeSum = bufferPool.reduceToInt(
                Integer.MAX_VALUE,
                (key, value) -> value.size(),
                0,
                (first, second) -> first + second);
        return sizeSum / bufferPool.size();
    }


    public int getPoolSize() {
        return this.bufferPool.size();
    }

    @Override
    public void run() {

        this.bufferPool.forEachValue(Integer.MAX_VALUE,
                (value) -> {
                    while (value.size() > 0) {
                        messageCounter.decrement();
                        KeyEvent event = value.poll();
                        this.mainBuffer.publishKeyEvent(event);
                    }
                });

    }


}
