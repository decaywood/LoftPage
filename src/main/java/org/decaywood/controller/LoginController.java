package org.decaywood.controller;

import org.decaywood.dataAccess.IDataAccess;
import org.decaywood.utils.CommonUtils;
import org.decaywood.utils.NameDomainMapper;
import org.decaywood.utils.RequestDatas;
import org.decaywood.utils.SystemConfigure;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

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
    public ModelAndView loginValidate() {
        ModelAndView modelAndView = getModelAndView();
        RequestDatas requestDatas = getRequestDatas();
        String errorInfo = null;
        String validateCode = requestDatas.get(NameDomainMapper.VALIDATE_CODE.name());

        if (validateCode == null || "".equals(validateCode)) {
            errorInfo = "validate code is invalid";
        } else {
//            String userName = requestDatas;
        }

        return modelAndView;
    }

}