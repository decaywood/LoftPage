package org.decaywood.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
        requestDatas.put(NameDomainMapper.SYSTEM_NAME.getName(), CommonUtils.readFile(SystemConfigure.SYSTEM_NAME));
        modelAndView.setViewName("login");
        modelAndView.addObject(NameDomainMapper.REQUEST_DATAS.getName(), requestDatas);
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
    public ModelAndView validate(HttpServletRequest request) {

        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();

        String userName = requestDatas.get(NameDomainMapper.USER_LOGIN_NAME.getName());
        char[] password = requestDatas.get(NameDomainMapper.USER_PASSWORD.getName()).toCharArray();

        String errorInfo = null;
        try {
            UsernamePasswordToken passwordToken = new UsernamePasswordToken(userName, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(passwordToken);
            clearArray(password);
        } catch (CredentialsException e) {
            errorInfo = NameDomainMapper.ERROR_INFO1.getName();
        } catch (AuthenticationException e) {
            errorInfo = NameDomainMapper.ERROR_INFO2.getName();
        } catch (Exception e){
            modelAndView.setViewName(NameDomainMapper.ERROR_PAGE.getName());
        } finally {
            String viewName = errorInfo != null ? NameDomainMapper.LOGIN_PAGE.getName()
                    : NameDomainMapper.MAIN_PAGE.getName();
           
            modelAndView.setViewName(viewName);
            modelAndView.addObject(NameDomainMapper.USER_NAME.getName(), userName);
            modelAndView.addObject(NameDomainMapper.USER_PASSWORD.getName(),
                    requestDatas.get(NameDomainMapper.USER_PASSWORD.getName()));
            modelAndView.addObject(NameDomainMapper.ERROR_INFO.getName(), errorInfo);
            return modelAndView;
        }
    }




    private void clearArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = '0';
        }
    }
}