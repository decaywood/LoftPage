package org.decaywood.buffer;

import org.decaywood.entity.KeyEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author: decaywood
 * @date: 2015/6/18 20:42
 */


public class MessageBuffer {

    private ConcurrentHashMap<Integer, ConcurrentLinkedDeque<KeyEvent>> pool;

    private int maxSizePerDeque;

    public MessageBuffer() {
        this(1 << 8);
    }

    public MessageBuffer(int maxSizePerDeque) {

        this.maxSizePerDeque = maxSizePerDeque > 0 ? maxSizePerDeque : 1 << 8;

        this.pool = new ConcurrentHashMap<>(4);
        for (int i = 0; i < pool.size(); i++) {
            this.pool.put(i, new ConcurrentLinkedDeque<>());
        }
    }

    public void publishEvent(KeyEvent keyEvent) {

        long id = keyEvent.getID();


    }

    private int averageSize() {
        int sizeSum = pool.reduceToInt(
                Integer.MAX_VALUE,
                (first, second) -> second.size(),
                0,
                (first, second) -> first + second);
        return sizeSum / pool.size();
    }

}
