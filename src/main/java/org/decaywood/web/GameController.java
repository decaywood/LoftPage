package org.decaywood.web;

import org.decaywood.KeyEvent;
import org.decaywood.buffer.MessageBuffer;
import org.decaywood.serve.ConnectionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: decaywood
 * @date: 2015/6/15 20:22
 */
@RestController
public class GameController {

    @Resource(name = "ConnectionManager")
    private ConnectionManager manager;

    @Resource(name = "MessageBuffer")
    private MessageBuffer messageBuffer;


    @RequestMapping(value = "/connectGame")
    public String connectGame(HttpServletRequest request, KeyEvent keyEvent) {
        String result;
        keyEvent.setIPAddress(request.getRemoteAddr());
        result = manager.connect(keyEvent);
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/keyDown")
    public void keyDown(HttpServletRequest request, KeyEvent event) {
        System.out.println("keydown : " + event);
        event.setIPAddress(request.getRemoteAddr());
        this.messageBuffer.publishEvent(event);

    }





}
