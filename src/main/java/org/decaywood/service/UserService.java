package org.decaywood.service;

import org.decaywood.dataAccess.UserDao;
import org.decaywood.entity.User;
import org.decaywood.exceptions.UserConflictException;
import org.decaywood.utils.CommonUtils;
import org.decaywood.utils.NameDomainMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

/**
 * Created by decaywood on 2015/5/23.
 */

@Service("userService")
public class UserService {

    @Resource(name = "userDataAccess")
    private UserDao dao;

    public void registNewUser(User user) throws Exception {

        User queryUser = dao.queryByUser(user);
        String errorInfo = null;

        if(queryUser != null){
            StringBuilder builder = new StringBuilder();
            String userEmail = queryUser.getUserEmail();
            String userName = queryUser.getUserName();
            String userLoginName = queryUser.getUserLoginName();
            if(userLoginName.equals(user.getUserLoginName()))
                builder.append("User Login Name Has Already Exist!");
            if(userName.equals(user.getUserName()))
                builder.append("User Name Has Already Exist!");
            if(userEmail.equals(user.getUserEmail()))
                builder.append("User Email Has Already Exist!");
            errorInfo = builder.toString();
        }

        if(errorInfo != null) throw new UserConflictException(errorInfo);

        dao.saveUser(user);

    }


    private void userFormatPadding(User user) {
        user.setUserID(CommonUtils.generateUUID());
        user.setUserRole(NameDomainMapper.ROLE_USER.getName());
        Date date = new Date(new DateTime().getMillis());
        user.setUserLastLoginTime(date);
        user.setUserRegisterTime(date);
        user.setUserStatus(NameDomainMapper.STATUS_LOGIN.getName());
    }

}
