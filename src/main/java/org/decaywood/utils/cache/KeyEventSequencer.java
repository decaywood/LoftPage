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

    public interface Operator {
        void operate();
    }

    /**
     * this queue is just for collect the keyEvents,
     * it is for reusing the queue rather than new a object when execute the method
     * every time so that it can reduce garbage recycle
     * it is thread safety
     */
    ThreadLocal<Queue<KeyEvent>> threadLocalQueue;


    private ConcurrentHashMap<String, KeyEvent> keyEventBuffer;

    public KeyEventSequencer() {
        this.keyEventBuffer = new ConcurrentHashMap<>(1 << 10);
        this.threadLocalQueue = new ThreadLocal<>();
    }

    public Queue<KeyEvent> processKeyEvent(KeyEvent keyEvent, Consumer<KeyEvent> operator) {
        initThreadLocal();
        Queue<KeyEvent> queue = threadLocalQueue.get();
//        queue
        return queue;
    }

    private synchronized void initThreadLocal() {
        Queue<KeyEvent> queue = threadLocalQueue.get();
        if (queue == null) {
            queue = new LinkedList<>();
            threadLocalQueue.set(queue);
        }
    }
}
