package org.decaywood.buffer.handler;

import com.lmax.disruptor.WorkHandler;
import org.apache.log4j.Logger;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */

public class KeyEventSender implements WorkHandler<KeyEvent> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public static final String ADDRESS_PREFIX = "/message/responds/";


    private Optional<KeyEventSequencer> sequencer;

    private Optional<ConnectionManager> manager;


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
    private Optional<SimpMessagingTemplate> simpMessagingTemplate;

    public KeyEventSender(ConnectionManager manager,
                          SimpMessagingTemplate simpMessagingTemplate,
                          KeyEventSequencer sequencer) {
        this.manager = Optional.of(manager);
        this.simpMessagingTemplate = Optional.of(simpMessagingTemplate);
        this.sequencer = Optional.of(sequencer);
    }



    @Override
    public void onEvent(KeyEvent event) throws Exception {
        execute(event);
    }

    private void execute(KeyEvent event) throws InterruptedException {

        if(!this.simpMessagingTemplate.isPresent()
                || !this.manager.isPresent() || sequencer.isPresent()) return;

        Optional<Consumer<KeyEvent>> optional = Optional.of(this::sendEvent);
        sequencer.get().processKeyEvent(event, optional);


    }

    public void sendEvent(KeyEvent event) {



        try {

            String sendURL;
            String IPAddress = event.getIPAddress();
            String userID = event.getUserID();
            sendURL = manager.get().getSendURL(IPAddress, userID);
            simpMessagingTemplate.get().convertAndSend(sendURL, event);

        } catch (Exception e) {
            simpMessagingTemplate.get().convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), e.getMessage());
        }
    }

}
