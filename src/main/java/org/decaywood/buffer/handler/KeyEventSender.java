package org.decaywood.buffer.handler;

import com.lmax.disruptor.EventHandler;
import org.decaywood.KeyEvent;
import org.decaywood.KeyEventSequencer;
import org.decaywood.serve.ConnectionManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */

@Component(value = "KeyEventSender")
public class KeyEventSender implements EventHandler<KeyEvent> {

    public static final String ADDRESS_PREFIX = "/message/responds/";

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
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * it is used to manage the connection, mapping URL between two gamer
     */
    @Resource(name = "ConnectionManager")
    protected ConnectionManager manager;

    /**
     * mainBuffer would disorder the keyEvent,sequencer can
     * reorder the keyEvent passed through the mainBuffer
     */
    @Resource(name = "KeyEventSequencer")
    protected KeyEventSequencer sequencer;

    public KeyEventSender() {}

    private void execute(KeyEvent event) throws InterruptedException {
        Consumer<KeyEvent> optional = this::sendEvent;
        sequencer.processKeyEvent(event, optional);
    }

    public void sendEvent(KeyEvent event) {

        try {

            String sendURL;
            String IPAddress = event.getIPAddress();
            String userID = event.getUserID();
            sendURL = manager.getSendURL(IPAddress, userID);
            simpMessagingTemplate.convertAndSend(sendURL, event);
            System.out.println("send Event : " + event.getGameState() + "  " + sendURL);

        } catch (Exception e) {
            simpMessagingTemplate.convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), e.getMessage());
        }
    }

    @Override
    public void onEvent(KeyEvent event, long sequence, boolean endOfBatch) throws Exception {
        execute(event);
    }
}
