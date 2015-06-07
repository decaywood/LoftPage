package org.decaywood.dataAccess;

import org.decaywood.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/6/7 20:14
 */

@Repository(value = "userDataAccess")
public class UserDao extends DataAccessSupport<User, User, Object, Object, Object> {

    public User queryByUser(User user) {
        return selectOne("UserMapper.queryByUser", user);
    }
    /*
    * 通过id获取数据
    */
    public User queryByUserID(User user) {
        return queryByUserID(user.getUserID());
    }

    public User queryByUserID(String userID) {
        return selectOne("UserMapper.queryByUserID", userID);
    }
    /*
    * 通过loginname获取数据
    */
    public User queryByUserLoginName(User user) {
        return queryByUserLoginName(user.getUserLoginName());
    }

    public User queryByUserLoginName(String userLoginName) {
        return selectOne("UserMapper.queryByUserLoginName", userLoginName);
    }

    /*
   * 通过name获取数据
   */
    public User queryByUserName(User user) {
        return queryByUserName(user.getUserName());
    }

    public User queryByUserName(String userLoginName) {
        return selectOne("UserMapper.queryByUserName", userLoginName);
    }

    /*
    * 通过邮箱获取数据
    */
    public User queryByUserEmail(User user) {
        return queryByUserEmail(user.getUserEmail());
    }

    public User queryByUserEmail(String userEmail) {
        return selectOne("UserMapper.queryByUserEmail", userEmail);
    }

    /*
    * 通过编号获取数据
    */

    public User queryByUserNumber(User user) {
        return queryByUserNumber(user.getUserPhoneNumber());
    }


    public User queryByUserNumber(String userNumber) {
        return selectOne("UserMapper.queryByUserNumber", userNumber);
    }

    /*
    * 保存用户
    */
    public void saveUser(User User) {
        insert("UserMapper.saveUser", User);
    }
    /*
    * 修改用户
    */
    public void updateUser(User User) {
        update("UserMapper.updateUser", User);
    }

    public void updateUserLastLoginTime(User user) {
        update("UserMapper.updateUserLastLoginTime", user.getUserLastLoginTime());
    }

    /*
    * 换皮肤
    */
    public void updateSkin(User User) {
        update("UserMapper.updateSkin", User);
    }
    /*
    * 删除用户
    */
    public void deleteUser(User User) {
        delete("UserMapper.deleteUser", User);
    }
    /*
    * 批量删除用户
    */
    public void batchDeleteUser(List<User> users) {
        batchDelete("UserMapper.batchDeleteUser", users);
    }



    /*
    * 保存用户IP
    */
    public void updateIPAddress(User User) {
        update("UserMapper.updateIPAddress", User);
    }

    /*
    * 登录判断
    */
    public User getUserWithAuth(User User) {
        return selectOne("UserMapper.getUserWithAuth", User);
    }
    /*
    * 跟新登录时间
    */
    public void updateLastLoginTime(User User) {
        update("UserMapper.updateLastLoginTime", User);
    }

    /*
    *通过id获取数据
    */
    public User getUserAndRoleById(User user)   {
        return selectOne("UserMapper.getUserAndRoleById", user);
    }
    
}
