package org.decaywood.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.decaywood.service.UserService;
import org.decaywood.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: decaywood
 * @date: 2015/5/23 14:08
 */
@Controller
public class LoginController extends BaseController {

    @Resource(name = "userService")
    UserService userService;

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();
        requestDatas.put(NameDomainMapper.SYSTEM_NAME.getName(), CommonUtils.readFile(SystemConfigure.SYSTEM_NAME));
//        modelAndView.setViewName(NameDomainMapper.LOGIN_PAGE.getName()); // 调试状态，暂时不用注册页面
        modelAndView.setViewName("2048");
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
        session.setAttribute(NameDomainMapper.SESSION_SECURITY_CODE.getName(), validateCodeString);

        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.writeTo(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/validate")
    @ResponseBody
    public String validate(String validateCode) {

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();

        String respectValidateCode = (String) session.getAttribute(NameDomainMapper.SESSION_SECURITY_CODE.name());
        if (CommonUtils.isEmpty(respectValidateCode) || !respectValidateCode.equalsIgnoreCase(validateCode)) {
            return NameDomainMapper.ERROR_INFO1.getName();
        } else {
            return "";
        }

    }




    @RequestMapping(value = "/authenticate")
    @ResponseBody
    public String authenticate(String userName, String password) {

        String errorInfo = "";
        try {
            UsernamePasswordToken passwordToken = new UsernamePasswordToken(userName, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(passwordToken);
        } catch (AuthenticationException e) {
            return NameDomainMapper.ERROR_INFO2.getName();
        } catch (Exception e){
            return NameDomainMapper.ERROR_INFO + e.toString();
        }

        return errorInfo;

    }

    @RequestMapping(value = "/mainPage")
    public ModelAndView mainPage() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName(NameDomainMapper.MAIN_PAGE.getName());
        return modelAndView;
    }

}