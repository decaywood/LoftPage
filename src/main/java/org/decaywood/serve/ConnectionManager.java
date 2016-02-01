package org.decaywood.serve;

import org.decaywood.KeyEvent;
import org.decaywood.buffer.handler.KeyEventSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: decaywood
 * @date: 2015/6/29 21:48
 */

@Component(value = "ConnectionManager")
public class ConnectionManager {

    @Resource
    private SimpMessagingTemplate template;

    /**
     * connectionQueue is defined as a waiting queue
     * when two user is waiting for connection,these two
     * user can be matched into IDMapper
     */
    private Deque<URLMapper> connectionQueue;

    /**
     * IDMapper record the user mapping information,
     * when user1 send the message to user2, eventSender
     * can send the message to two users according to
     * the IDMapper
     */
    private Map<URLMapper, URLMapper> IDMapper;

    public static class URLMapper {

        private final String IPAddress;
        private final String userID;

        public URLMapper(String IPAddress, String userID) {
            this.IPAddress = IPAddress;
            this.userID = userID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            URLMapper urlMapper = (URLMapper) o;

            return IPAddress.equals(urlMapper.IPAddress);

        }

        @Override
        public int hashCode() {
            return IPAddress.hashCode();
        }
    }

    public ConnectionManager() {
        this.connectionQueue = new LinkedList<>();
        this.IDMapper = new ConcurrentHashMap<>();
    }

    public void disConnectGame(URLMapper mapper, KeyEvent event) {
        disConnectGame(event, mapper.IPAddress, mapper.userID);
    }

    public synchronized void disConnectGame(KeyEvent event, String IPAddress, String userID) {

        URLMapper urlMapper = new URLMapper(IPAddress, userID);

        if(!IDMapper.containsKey(urlMapper)) return;

        URLMapper remote = IDMapper.get(urlMapper);
        IDMapper.remove(urlMapper);

        if(IDMapper.containsKey(remote)) IDMapper.remove(remote);

        event.setGameState("game_disconnect");
        template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + userID, event);

        changEventContent(remote, event);
        template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + remote.userID, event);

        changEventContent(urlMapper, event);

    }


    public synchronized String connect(KeyEvent keyEvent) {

        String IPAddress = keyEvent.getIPAddress();
        String userID = keyEvent.getUserID();

        URLMapper mapper = new URLMapper(IPAddress, userID);

        while (true) {
            if (!connectionQueue.isEmpty() && !IDMapper.containsKey(mapper)) {

                if (mapper.equals(connectionQueue.peek())) return "Waiting For Remote Connection!";

                URLMapper remote = connectionQueue.poll();

                IDMapper.put(mapper, remote);
                IDMapper.put(remote, mapper);

                keyEvent.setGameState("game_connect_ack");

                template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + userID, keyEvent);

                changEventContent(remote, keyEvent);
                template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + remote.userID, keyEvent);

                return "Connect Game Success!";

            } else if (connectionQueue.isEmpty() && !IDMapper.containsKey(mapper)) {

                if (!IDMapper.containsKey(mapper))
                    connectionQueue.offer(mapper);
                return "You've joined the game, waiting For Remote Connection!";

            } else {
                disConnectGame(mapper, keyEvent);
                return connect(keyEvent);
            }
        }

    }


    public String getSendURL(String IPAddress, String userID) throws Exception {

        URLMapper urlMapper = new URLMapper(IPAddress, userID);

        if (!IDMapper.containsKey(urlMapper)) throw new Exception("URL doesn't match!");

        String sendURL;
        sendURL = KeyEventSender.ADDRESS_PREFIX + IDMapper.get(urlMapper).userID;
        return sendURL;

    }

    private void changEventContent(URLMapper mapper, KeyEvent event) {
        event.setUserID(mapper.userID);
        event.setIPAddress(mapper.IPAddress);
    }


}
