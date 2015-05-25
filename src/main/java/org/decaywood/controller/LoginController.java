package org.decaywood.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.decaywood.dataAccess.IDataAccess;
import org.decaywood.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: decaywood
 * @date: 2015/5/23 14:08
 */
@Controller
public class LoginController extends BaseController {

    @Resource(name = "dataAccessSupport")
    IDataAccess dataAccess;

    @RequestMapping(value = "/login.do")
    public ModelAndView login() {
        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();
        requestDatas.put(NameDomainMapper.SYSTEM_NAME.name(), CommonUtils.readFile(SystemConfigure.SYSTEM_NAME));
        modelAndView.setViewName("login");
        modelAndView.addObject(NameDomainMapper.REQUEST_DATAS.name(), requestDatas);
        return modelAndView;
    }

    @RequestMapping(value = "/loginValidate.do")
    public void loginValidate(HttpServletResponse response) {
        ByteOutputStream outputStream = new ByteOutputStream();
        ValidateCode validateCode = new ValidateCode(125, 40, 4, 10);
        BufferedImage bufferedImage = validateCode.getBufferedImage();
        String validateCodeString = validateCode.getCode();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(NameDomainMapper.SESSION_SECURITY_CODE, validateCodeString);
        try {
            ImageIO.write(bufferedImage, "jpg", new FileOutputStream("D:\\Work\\IntelliJProjects"));
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.writeTo(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}