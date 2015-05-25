package org.decaywood.controller;

import org.apache.log4j.Logger;
import org.decaywood.utils.RequestDatas;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by decaywood on 2015/5/23.
 */
public class BaseController {

    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 得到PageData
     */
    public RequestDatas getRequestDatas(){
        return new RequestDatas(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request;
    }

    /**
     * 得到32位的uuid
     * @return
     */
    public String get32UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }




}
