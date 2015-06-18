package org.decaywood.buffer;

import org.decaywood.entity.KeyEvent;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author: decaywood
 * @date: 2015/6/18 20:42
 */


public class MessageBuffer {

    private ConcurrentHashMap<Integer, Deque<KeyEvent>> bufferPool;

    private int threshold;

    public MessageBuffer() {
        this(1 << 8);
    }

    public MessageBuffer(int threshold) {

        this.threshold = threshold > 0 ? threshold : 1 << 8;

        this.bufferPool = new ConcurrentHashMap<>(4);
        for (int i = 0; i < bufferPool.size(); i++) {
            this.bufferPool.put(i, new ConcurrentLinkedDeque<>());
        }
    }

    public void publishEvent(KeyEvent keyEvent) {

        Deque<KeyEvent> buffer = getMinBuffer();
        buffer.offer(keyEvent);

    }

    private synchronized Deque<KeyEvent> getMinBuffer() {
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

   

}
