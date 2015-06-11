package org.decaywood.service;

import org.decaywood.dataAccess.UserDao;
import org.decaywood.entity.User;
import org.decaywood.exceptions.UserConflictException;
import org.decaywood.utils.CommonUtils;
import org.decaywood.utils.NameDomainMapper;
import org.decaywood.utils.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Date;

/**
 * Created by decaywood on 2015/5/23.
 */

@Service("userService")
public class UserService {

    @Resource(name = "userDataAccess")
    private UserDao dao;

    public void updateUserLastLoginTime(User user) {
        dao.updateUserLastLoginTime(user);
    }

    public User queryByUser(User user) {
        return dao.queryByUser(user);
    }

    public void registNewUser(User user) throws UserConflictException {

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


    public String saveImage( InputStream stream,
                              String rootPath,
                              String fileType ) throws IOException {
        String filePath = null;
        try {
            String suffix = fileType.replace("/image", "");
            BufferedInputStream fileIn = new BufferedInputStream(stream);

            filePath = rootPath + CommonUtils.generateUUID() + suffix;

            File file = new File(filePath);
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[1024 * 1024 * 2];
            int bytesIndex = fileIn.read(buf, 0, buf.length);
            while (bytesIndex != -1) {
                fileOut.write(buf, 0, bytesIndex);
                fileIn.read(buf, 0, buf.length);
            }
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            throw new IOException();
        }
        return filePath;
    }

    private void userFormatPadding(User user) {
        user.setUserID(CommonUtils.generateUUID());
        user.setUserRole(NameDomainMapper.ROLE_USER.getName());
        Date date = TimeUtils.getSqlTime();
        user.setUserLastLoginTime(date);
        user.setUserRegisterTime(date);
        user.setUserStatus(NameDomainMapper.STATUS_LOGIN.getName());
    }

}
