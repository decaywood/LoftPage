package org.decaywood.buffer.handler;

import com.lmax.disruptor.WorkHandler;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */

public class KeyEventSender implements WorkHandler<KeyEvent> {

    public static final String ADDRESS_PREFIX = "/message/responds/";


    private KeyEventSequencer sequencer;

    private ConnectionManager manager;


    /**
     *
     * The messages were sending in ascending order from the application implementation perspective
     * (I.e, convertAndSend() are called in one thread or at least thread safe fashion").
     * However, Springframework web socket uses reactor-tcp implementation which will process the messages
     * on clientOutboundChannel from the thread pool. Thus the messages can be written to the tcp socket
     * in different order that they are arrived.
     *
     * solution:
     * 1. configured the web socket to limit one thread for the clientOutboundChannel.
     * 2. write our own logical to preserve the order --> we accept this solution,the main reason as follow:
     *
     *    * back end required high react time,so processing messages by threadPool is needed.
     *    * we don't care the cost of frontend(relatively).
     *
     */
    private SimpMessagingTemplate simpMessagingTemplate;

    public KeyEventSender(ConnectionManager manager,
                          SimpMessagingTemplate simpMessagingTemplate,
                          KeyEventSequencer sequencer) {
        this.manager = manager;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sequencer = sequencer;
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
