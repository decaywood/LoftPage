package org.decaywood.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.decaywood.entity.User;
import org.decaywood.service.UserService;
import org.decaywood.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: decaywood
 * @date: 2015/5/23 14:08dapa
 */
@Controller
public class LoginController extends BaseController {

    @Resource(name = "userService")
    UserService userService;

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();
        requestDatas.put(NameDomainMapper.SYSTEM_NAME.name(), CommonUtils.readFile(SystemConfigure.SYSTEM_NAME));
        modelAndView.setViewName("login");
        modelAndView.addObject(NameDomainMapper.REQUEST_DATAS.name(), requestDatas);
        return modelAndView;
    }

    @RequestMapping(value = "/validateCode")
    public void getValidateCode(HttpServletResponse response) {

        ByteOutputStream outputStream = new ByteOutputStream();
        ValidateCode validateCode = new ValidateCode(125, 38, 4, 10);

        BufferedImage bufferedImage = validateCode.getBufferedImage();
        String validateCodeString = validateCode.getCode();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(NameDomainMapper.SESSION_SECURITY_CODE.name(), validateCodeString);

        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.writeTo(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/validate")
    public ModelAndView validate(HttpServletRequest request) {
        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String respectValidateCode = (String) session.getAttribute(NameDomainMapper.SESSION_SECURITY_CODE.name());

        String validateCode = requestDatas.get("validateCode");

        String errorInfo = null;
        if(CommonUtils.isEmpty(respectValidateCode) || !respectValidateCode.equalsIgnoreCase(validateCode)) {
            errorInfo = "validate code is empty!";
        }

        String userName = requestDatas.get("userName");
        char[] password = requestDatas.get("password").toCharArray();
        String hashedPassowrd = new SimpleHash("SHA-1", userName, password).toString();

        User user = new User().setUserLoginName(userName).setUserPassWord(hashedPassowrd);
//        user = userService.queryByUser(user);

        if(user == null){
            errorInfo = "password or userName is fault!";
        }

        if (errorInfo != null) {
            modelAndView.setViewName("login");
            modelAndView.addObject("userName", userName);
            modelAndView.addObject("password", requestDatas.get("password"));
            modelAndView.addObject("errorInfo", errorInfo);
            return modelAndView;
        }

        user.setUserLastLoginTime(TimeUtils.getTime().toString());
        userService.updateUserLastLoginTime(user);

        session.setAttribute(NameDomainMapper.SESSION_USER_LOGIN_NAME.name(), user);
        session.removeAttribute(NameDomainMapper.SESSION_SECURITY_CODE.name());

        UsernamePasswordToken passwordToken = new UsernamePasswordToken(userName, password);
        clearArray(password);


        currentUser.login(passwordToken);
        modelAndView.setViewName("welcome");
        return modelAndView;

    }

    @RequestMapping(value = "/mainPage")
    public void mainPage(){

    }



    private void clearArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = '0';
        }
    }
}