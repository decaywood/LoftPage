package org.decaywood.buffer;

import org.decaywood.entity.KeyEvent;

import javax.annotation.Resource;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/6/18 20:42
 */


public class MessageBuffer implements Runnable {

    @Resource
    private MainBuffer mainBuffer;

    private ConcurrentHashMap<Integer, Deque<KeyEvent>> bufferPool;
    private ExecutorService service;
    private LongAdder messageCounter;
    private int threshold;

    public MessageBuffer() {
        this(1 << 8, Runtime.getRuntime().availableProcessors());
    }

    public MessageBuffer(int threshold, int cacheSize) {

        this.service = Executors.newSingleThreadExecutor();

        this.threshold = threshold > 0 ? threshold : 1 << 8;

        this.messageCounter = new LongAdder();
        this.bufferPool = new ConcurrentHashMap<>(Math.max(1, cacheSize));
        for (int i = 0; i < bufferPool.size(); i++) {
            this.bufferPool.put(i, new ConcurrentLinkedDeque<>());
        }

    }

    public void publishEvent(KeyEvent keyEvent) {

        messageCounter.increment();
        Deque<KeyEvent> buffer = getMinBuffer();
        buffer.offer(keyEvent);


    }

    private void flushBufferToMainBuffer() {
        this.service.execute(this);
    }

    private Deque<KeyEvent> getMinBuffer() {
        if(averageSize() > this.threshold){
            this.bufferPool.put(this.bufferPool.size(), new ConcurrentLinkedDeque<>());
        }
        Deque<KeyEvent> minSizeBuffer = bufferPool.reduceValues(
                Integer.MAX_VALUE,
                (first, second) -> first.size() < second.size() ? first : second);
        return minSizeBuffer;
    }
    

    private int averageSize() {
        int sizeSum = bufferPool.reduceToInt(
                Integer.MAX_VALUE,
                (key, value) -> value.size(),
                0,
                (first, second) -> first + second);
        return sizeSum / bufferPool.size();
    }


    @Override
    public void run() {

        if(messageCounter.intValue() == 0) return;

        ConcurrentLinkedDeque<KeyEvent> queue = (ConcurrentLinkedDeque<KeyEvent>) this.bufferPool.reduceValues(Integer.MAX_VALUE,
                (first, second) -> {
                    while (first.size() > 0) {
                        second.offer(first.poll());
                        messageCounter.decrement();
                    }
                    return second;
                });

    }


}
