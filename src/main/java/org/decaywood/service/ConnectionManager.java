package org.decaywood.service;

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

    private Deque<URLMapper> connectionQueue;
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

    public String disConnectGame(URLMapper mapper) {
        return disConnectGame(mapper.IPAddress, mapper.userID);
    }

    public synchronized String disConnectGame(String IPAddress, String userID) {

        URLMapper urlMapper = new URLMapper(IPAddress, userID);

        if(!IDMapper.containsKey(urlMapper)) return "Not Connect Other Player!";

        URLMapper remote = IDMapper.get(urlMapper);
        IDMapper.remove(urlMapper);


        if(IDMapper.containsKey(remote)) IDMapper.remove(remote);
        String info = "Remote Player Disconnected!";
        template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + userID, info);
        template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + remote.userID, info);

        return "Game Disconnected!";

    }


    public synchronized String connect(String IPAddress, String userID) {

        URLMapper mapper = new URLMapper(IPAddress, userID);

        while (true) {
            if (!connectionQueue.isEmpty() && !IDMapper.containsKey(mapper)) {

                URLMapper remote = connectionQueue.poll();

                IDMapper.put(mapper, remote);
                IDMapper.put(remote, mapper);

                template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + userID, "Match successful!");
                template.convertAndSend(KeyEventSender.ADDRESS_PREFIX + remote.userID, "Match successful!");

                return "Connect Game Success!";

            } else if (connectionQueue.isEmpty() && !IDMapper.containsKey(mapper)) {

                if (!IDMapper.containsKey(mapper))
                    connectionQueue.offer(mapper);
                return "Waiting For Connection!";
            } else {
                disConnectGame(mapper);
                return connect(IPAddress, userID);
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




}
