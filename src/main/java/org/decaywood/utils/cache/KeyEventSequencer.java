package org.decaywood.utils.cache;

import org.apache.log4j.Logger;
import org.decaywood.entity.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Optional;
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

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static class BufferKey {

        String IPAddress;
        boolean marker;
        final String userID;
        final int key;

        public BufferKey(String IPAddress, String userID, int key) {
            this.IPAddress = IPAddress;
            this.userID = userID;
            this.key = key;
        }

        BufferKey mark() {
            this.marker = true;
            return this;
        }

        BufferKey unMark() {
            this.marker = false;
            return this;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BufferKey bufferKey = (BufferKey) o;

            if (marker != bufferKey.marker) return false;
            else if (key != bufferKey.key) return false;
            else if (!IPAddress.equals(bufferKey.IPAddress)) return false;
            return userID.equals(bufferKey.userID);

        }

        @Override
        public int hashCode() {
            int result = IPAddress.hashCode();
            result = 31 * result + (marker ? 1 : 0);
            result = 31 * result + userID.hashCode();
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
    private ThreadLocal<Queue<KeyEvent>> eventCollector = ThreadLocal.withInitial(LinkedList::new);



    private ConcurrentHashMap<BufferKey, KeyEvent> keyEventBuffer;

    public KeyEventSequencer() {
        this.keyEventBuffer = new ConcurrentHashMap<>(1 << 10);
    }

    public synchronized void processKeyEvent(KeyEvent keyEvent, Optional<Consumer<KeyEvent>> operator) {

        if(!operator.isPresent()) return;

        if(!keyEvent.canBuffered()){
            operator.get().accept(keyEvent);
            return;
        }

        BufferKey markKey = getBufferKey(keyEvent.getIPAddress(), keyEvent.getUserID(), 0).mark();
        if (!keyEventBuffer.containsKey(markKey)) {
            keyEventBuffer.put(markKey, keyEvent);
            clearUserData(keyEvent.getIPAddress(), keyEvent.getUserID());
        }

        Queue<KeyEvent> queue = eventCollector.get();

        BufferKey bufferKey = getBufferKey(keyEvent.getIPAddress(), keyEvent.getUserID(), keyEvent.getCurrentNum()).mark();

        if (keyEventBuffer.containsKey(bufferKey)) {

            collectKeyEvent(queue, keyEvent);
            if(keyEvent.getCurrentNum() != 0)
                keyEventBuffer.remove(bufferKey);

        } else {
            bufferKey.unMark();
            keyEventBuffer.put(bufferKey, keyEvent);

        }

        sendEvent(queue, operator.get());

    }

    public void clearUserData(String IPAddress, String userID) {

        logger.info("clearing user history data!");

        keyEventBuffer.forEachKey(Integer.MAX_VALUE, bufferKey -> {

            if (!bufferKey.IPAddress.equalsIgnoreCase(IPAddress)) return;
            if (bufferKey.userID.equalsIgnoreCase(userID)) return;
            keyEventBuffer.remove(bufferKey);

        });

    }


    private BufferKey getBufferKey(String IPAddress, String userID, int key) {

        return new BufferKey(IPAddress, userID, key);

    }

    private void collectKeyEvent(Queue<KeyEvent> queue, KeyEvent event) {

        queue.offer(event);
        String ip = event.getIPAddress();
        String userID = event.getUserID();
        int expectNum = event.getExpectNum();
        BufferKey bufferKey = getBufferKey(ip, userID, expectNum);

        KeyEvent nextEvent = null;

        while (keyEventBuffer.containsKey(bufferKey)) {

            nextEvent = keyEventBuffer.get(bufferKey);
            queue.offer(nextEvent);
            expectNum = nextEvent.getExpectNum();
            keyEventBuffer.remove(bufferKey);
            bufferKey = getBufferKey(ip, userID, expectNum);

        }

        bufferKey.mark();
        keyEventBuffer.put(bufferKey, nextEvent == null ? event : nextEvent);

    }

    private void sendEvent(Queue<KeyEvent> queue, Consumer<KeyEvent> operator) {
        while (!queue.isEmpty()) {
            KeyEvent event = queue.poll();
            logger.info("event send =======> " + event.getCurrentNum());
            operator.accept(event);
        }
    }


}
