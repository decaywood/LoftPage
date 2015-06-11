package org.decaywood.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.decaywood.entity.User;
import org.decaywood.exceptions.UserConflictException;
import org.decaywood.exceptions.ValueCantCastException;
import org.decaywood.service.UserService;
import org.decaywood.utils.NameDomainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: decaywood
 * @date: 2015/6/10 9:39
 */

@Controller
public class RegisterController extends BaseController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "userService")
    UserService userService;

    @RequestMapping(value = "/imgUpload")
    @ResponseBody
    public String imgUpload(HttpServletRequest request, String fileType) {
       String errorInfo = "";
        try {
            InputStream inputStream = request.getInputStream();
            String contextPath = request.getServletContext().getRealPath("/");

            String imagePath = userService.saveImage(inputStream, contextPath, fileType);
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute(NameDomainMapper.LOGO_PATH.getName(), imagePath);

        } catch (ValueCantCastException e) {
            errorInfo = NameDomainMapper.ERROR_INFO3.getName();
            logger.error(e.getMessage());
        } catch (IOException e) {
            errorInfo = NameDomainMapper.ERROR_INFO3.getName();
            logger.error(e.getMessage());
        }
        return errorInfo;
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(String userName, String password, String nickName, String email, String phone) {
        User user = new User();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String logoURL = (String) session.getAttribute(NameDomainMapper.LOGO_PATH.getName());
        String errorInfo = "";
        user.setUserLoginName(userName)
                .setUserPassword(password)
                .setUserNickName(nickName)
                .setUserEmail(email)
                .setUserLogoURL(logoURL);
        try {
            userService.registNewUser(user);
        } catch (UserConflictException e) {
            errorInfo = e.getMessage();
            logger.error(e.getMessage());
        }

        return errorInfo;
    }

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName(NameDomainMapper.REGISTER_PAGE.getName());
        return modelAndView;
    }

}
