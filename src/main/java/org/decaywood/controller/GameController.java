package org.decaywood.controller;

import org.apache.log4j.Logger;
import org.decaywood.entity.KeyEvent;
import org.decaywood.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author: decaywood
 * @date: 2015/6/15 20:22
 */
@Controller
public class GameController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "userService")
    UserService userService;

    @RequestMapping(value = "/keyDown")
    public void keyDown(KeyEvent event) {
        logger.debug(event);
    }
}
