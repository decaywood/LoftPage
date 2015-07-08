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

        String userID;
        int expectNum;
        Consumer<BufferKey> operator;

        public BufferKey(String userID, int expectNum, Consumer<BufferKey> operator) {
            this.userID = userID;
            this.expectNum = expectNum;
            this.operator = operator;
        }

        void recycle() {
            this.userID = null;
            this.expectNum  = 0;
            operator.accept(this);
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
            BufferKey bufferKey = getBufferKey(keyEvent);
            keyEventBuffer.put(bufferKey, NULL_EVENT);
            operator.accept(keyEvent);
        }

        BufferKey key = getBufferKey(keyEvent);

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

    private BufferKey getBufferKey(KeyEvent keyEvent) {

        int expectNum = keyEvent.getExpectNum();
        String userID = keyEvent.getUserID();
        Queue<BufferKey> pool = threadLocalBufferKeyPool.get();
        BufferKey bufferKey;
        if (pool.isEmpty()) {
            bufferKey = new BufferKey(userID, expectNum, pool::offer);
        } else {
            bufferKey = pool.poll();
            bufferKey.userID = userID;
            bufferKey.expectNum = expectNum;
        }

        return bufferKey;

    }

}
