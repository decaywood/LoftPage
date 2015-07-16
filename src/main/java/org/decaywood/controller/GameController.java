package org.decaywood.controller;

import org.apache.log4j.Logger;
import org.decaywood.buffer.MessageBuffer;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.ConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: decaywood
 * @date: 2015/6/15 20:22
 */
@Controller
public class GameController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "ConnectionManager")
    private ConnectionManager manager;

    @Resource(name = "MessageBuffer")
    private MessageBuffer messageBuffer;


    @RequestMapping(value = "/connectGame")
    @ResponseBody
    public String connectGame(HttpServletRequest request, KeyEvent keyEvent) {
        String result;
        System.out.println(Thread.currentThread().getName());
        keyEvent.setIPAddress(request.getRemoteAddr());
        result = manager.connect(keyEvent);
        return result;
    }

    @RequestMapping(value = "/keyDown")
    @ResponseBody
    public void keyDown(HttpServletRequest request, KeyEvent event) {

        event.setIPAddress(request.getRemoteAddr());
        this.messageBuffer.publishEvent(event);

    }





}
