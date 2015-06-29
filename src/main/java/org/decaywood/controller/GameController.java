package org.decaywood.controller;

import org.apache.log4j.Logger;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Resource(name = "userService")
    UserService userService;

    @Resource
    SimpMessagingTemplate simpMessagingTemplate;


    @RequestMapping(value = "/keyDown")
    @ResponseBody
    public void keyDown(HttpServletRequest request, KeyEvent event) {
        logger.debug("=====================   " + event + "   =============================");
        simpMessagingTemplate.convertAndSend("/message/responds", event);
    }

}
