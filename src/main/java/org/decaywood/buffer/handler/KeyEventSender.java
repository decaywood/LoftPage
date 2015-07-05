package org.decaywood.buffer.handler;

import com.lmax.disruptor.WorkHandler;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/19 9:56
 */

public class KeyEventSender implements WorkHandler<KeyEvent> {

    public static final String ADDRESS_PREFIX = "/message/responds/";

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
        execute(event, 0, true);
    }

    private void execute(KeyEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        System.out.println("Event -> " + event +"Sequence -> " + sequence
                + "Batch -> " + endOfBatch + "  thread ====> " + Thread.currentThread().getId());

        String sendURL;

        try {
            String IPAddress = event.getIPAddress();
            String userID = event.getUserID();
//            sendURL = manager.getSendURL(IPAddress, userID);
//            simpMessagingTemplate.convertAndSend(sendURL, event);
            simpMessagingTemplate.convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), event.getIPAddress()); // forTest
        } catch (Exception e) {
            simpMessagingTemplate.convertAndSend(KeyEventSender.ADDRESS_PREFIX
                    + event.getUserID(), e.getMessage());
        }
    }


}
