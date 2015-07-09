package org.decaywood.utils.cache;

import org.decaywood.entity.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author: decaywood
 * @date: 2015/7/7 22:43.
 *
 * KeyEventSequencer is used to ensure the sending order of keyEvent
 * according to keyEvent currentNum and expectedNum, it is easy to ensure
 * the order. for example if a keyEvent in which currentNum is 1 and expectedNum is 2
 * but the coming keyEvent in which currentNum is 3, the No3 keyEvent would be cached
 * in sequencer and waiting for No2, when No2 is coming, No2 and No3 will be send at
 * the same time.
 *
 */

@Component("KeyEventSequencer")
public class KeyEventSequencer {

    private static final KeyEvent NULL_EVENT = new KeyEvent();

    private static class BufferKey {

        boolean marker;
        String userID;
        int key;

        Consumer<BufferKey> operator;

        public BufferKey(String userID, int key, Consumer<BufferKey> operator) {
            this.userID = userID;
            this.key = key;
            this.operator = operator;
        }

        BufferKey mark() {
            this.marker = true;
            return this;
        }

        BufferKey unmark() {
            this.marker = false;
            return this;
        }

        public boolean isMarker() {
            return marker;
        }

        void recycle() {
            this.userID = null;
            this.key  = 0;
            this.marker = false;
            operator.accept(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BufferKey bufferKey = (BufferKey) o;

            if (marker != bufferKey.marker) return false;
            if (key != bufferKey.key) return false;
            return !(userID != null ? !userID.equals(bufferKey.userID) : bufferKey.userID != null);

        }

        @Override
        public int hashCode() {
            int result = (marker ? 1 : 0);
            result = 31 * result + (userID != null ? userID.hashCode() : 0);
            result = 31 * result + key;
            return result;
        }
    }

    /**
     * this queue is just for collect the keyEvents,
     * it is for reusing the queue rather than new a object when execute the method
     * every time so that it can reduce garbage recycle
     * it is thread safety
     */
    ThreadLocal<Queue<KeyEvent>> threadLocalKeyEventCollector;

    /**
     * pool can cached the bufferKey so that it can be reuse
     */
    ThreadLocal<Queue<BufferKey>> threadLocalBufferKeyPool;


    private ConcurrentHashMap<BufferKey, KeyEvent> keyEventBuffer;

    public KeyEventSequencer() {
        this.keyEventBuffer = new ConcurrentHashMap<>(1 << 10);
        this.threadLocalKeyEventCollector = new ThreadLocal<>();
    }

    public Queue<KeyEvent> processKeyEvent(KeyEvent keyEvent, Consumer<KeyEvent> operator) {

        initThreadLocal();


        Queue<KeyEvent> queue = threadLocalKeyEventCollector.get();

        if (keyEvent.getCurrentNum() == 0) {
            BufferKey bufferKey = getBufferKey(keyEvent.getUserID(), keyEvent.getExpectNum());
            keyEventBuffer.put(bufferKey.mark(), NULL_EVENT);
            operator.accept(keyEvent);
        } else {
            BufferKey bufferKey = getBufferKey(keyEvent.getUserID(), keyEvent.getCurrentNum()).mark();
            if (keyEventBuffer.containsKey(bufferKey)) {

            } else {
                bufferKey.unmark();
                keyEventBuffer.put(bufferKey, keyEvent);
            }

        }

//        BufferKey key = getBufferKey(keyEvent);

        return queue;
    }

    private synchronized void initThreadLocal() {
        Queue<KeyEvent> collector = threadLocalKeyEventCollector.get();
        if (collector == null) {
            collector = new LinkedList<>();
            threadLocalKeyEventCollector.set(collector);
        }

        Queue<BufferKey> pool = threadLocalBufferKeyPool.get();
        if (pool == null) {
            pool = new LinkedList<>();
            threadLocalBufferKeyPool.set(pool);
        }
    }

    private BufferKey getBufferKey(String userID, int key) {

        Queue<BufferKey> pool = threadLocalBufferKeyPool.get();
        BufferKey bufferKey;
        if (pool.isEmpty()) {
            bufferKey = new BufferKey(userID, key, pool::offer);
        } else {
            bufferKey = pool.poll();
            bufferKey.userID = userID;
            bufferKey.key = key;
        }

        return bufferKey;

    }

}
