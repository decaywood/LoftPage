package org.decaywood.service;

import org.decaywood.dataAccess.DataAccessSupport;
import org.decaywood.utils.RequestDatas;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by decaywood on 2015/5/23.
 */

@Service("userService")
public class UserService {

    @Resource(name = "dataAccessSupport")
    private DataAccessSupport<RequestDatas, RequestDatas, Object, Object, RequestDatas> dao;

}
