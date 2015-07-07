package org.decaywood.buffer.handler;

import com.lmax.disruptor.WorkHandler;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */

public class KeyEventSender implements WorkHandler<KeyEvent> {

    public static final String ADDRESS_PREFIX = "/message/responds/";

    @Resource(name = "KeyEventSequencer")
    private KeyEventSequencer sequencer;

    @Resource(name = "ConnectionManager")
    private ConnectionManager manager;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    public KeyEventSender(ConnectionManager manager, SimpMessagingTemplate simpMessagingTemplate) {
        this.manager = manager;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }



    @Override
    public void onEvent(KeyEvent event) throws Exception {
        execute(event);
    }

    private void execute(KeyEvent event) throws InterruptedException {
        sequencer.processKeyEvent(event, KeyEventSender.this::sendEvent);
    }

    public void sendEvent(KeyEvent event) {
        try {
//            String sendURL;
//            String IPAddress = event.getIPAddress();
//            String userID = event.getUserID();
//            sendURL = manager.getSendURL(IPAddress, userID);
//            simpMessagingTemplate.convertAndSend(sendURL, event);
            simpMessagingTemplate.convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), event); // forTest
        } catch (Exception e) {
            simpMessagingTemplate.convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), e.getMessage());
        }
    }

}
