package org.decaywood.service;

import org.decaywood.dataAccess.DataAccessSupport;
import org.decaywood.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by decaywood on 2015/5/23.
 */

@Service("userService")
public class UserService {

    @Resource(name = "dataAccessSupport")
    private DataAccessSupport<User, User, Object, Object, Object> dao;


    /*
    * 通过id获取数据
    */
    public User queryByUserID(User user) {
        return queryByUserID(user.getUserID());
    }

    public User queryByUserID(String userID) {
        return dao.selectOne("UserXMapper.queryByUserID", userID);
    }
    /*
    * 通过loginname获取数据
    */
    public User queryByUserName(User user) {
        return queryByUserName(user.getUserName());
    }

    public User queryByUserName(String userName) {
        return dao.selectOne("UserXMapper.queryByUserName", userName);
    }

    /*
    * 通过邮箱获取数据
    */
    public User queryByUserEmail(User user) {
        return queryByUserEmail(user.getUserEmail());
    }

    public User queryByUserEmail(String userEmail) {
        return dao.selectOne("UserXMapper.queryByUserEmail", userEmail);
    }

    /*
    * 通过编号获取数据
    */

    public User queryByUserNumber(User user) {
        return queryByUserNumber(user.getUserPhoneNumber());
    }
    
    
    public User queryByUserNumber(String userNumber) {
        return dao.selectOne("UserXMapper.queryByUserNumber", userNumber);
    }

    /*
    * 保存用户
    */
    public void saveUser(User User) {
        dao.insert("UserXMapper.saveUser", User);
    }
    /*
    * 修改用户
    */
    public void updateUser(User User) {
        dao.update("UserXMapper.updateUser", User);
    }
    /*
    * 换皮肤
    */
    public void updateSkin(User User) {
        dao.update("UserXMapper.updateSkin", User);
    }
    /*
    * 删除用户
    */
    public void deleteUser(User User) {
        dao.delete("UserXMapper.deleteUser", User);
    }
    /*
    * 批量删除用户
    */
    public void batchDeleteUser(List<User> users) {
        dao.batchDelete("UserXMapper.batchDeleteUser", users);
    }



    /*
    * 保存用户IP
    */
    public void updateIPAddress(User User) {
        dao.update("UserXMapper.updateIPAddress", User);
    }

    /*
    * 登录判断
    */
    public User getUserWithAuth(User User) {
        return dao.selectOne("UserXMapper.getUserWithAuth", User);
    }
    /*
    * 跟新登录时间
    */
    public void updateLastLoginTime(User User) {
        dao.update("UserXMapper.updateLastLoginTime", User);
    }

    /*
    *通过id获取数据
    */
    public User getUserAndRoleById(User user)   {
        return dao.selectOne("UserMapper.getUserAndRoleById", user);
    }
    

}
