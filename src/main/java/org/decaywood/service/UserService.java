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
    private DataAccessSupport<User, User, Object, Object, User> dao;


    /*
    * 通过id获取数据
    */
    public User queryByUserID(User User)throws Exception{
        return dao.selectOne("UserXMapper.queryByUserID", User);
    }
    /*
    * 通过loginname获取数据
    */
    public User queryByUserName(User User)throws Exception{
        return dao.selectOne("UserXMapper.queryByUserName", User);
    }

    /*
    * 通过邮箱获取数据
    */
    public User queryByUserEmail(User User)throws Exception{
        return dao.selectOne("UserXMapper.queryByUserEmail", User);
    }

    /*
    * 通过编号获取数据
    */
    public User queryByUserNumber(User User)throws Exception{
        return dao.selectOne("UserXMapper.queryByUserNumber", User);
    }

    /*
    * 保存用户
    */
    public void saveUser(User User)throws Exception{
        dao.insert("UserXMapper.saveUser", User);
    }
    /*
    * 修改用户
    */
    public void updateUser(User User)throws Exception{
        dao.update("UserXMapper.updateUser", User);
    }
    /*
    * 换皮肤
    */
    public void updateSkin(User User)throws Exception{
        dao.update("UserXMapper.updateSkin", User);
    }
    /*
    * 删除用户
    */
    public void deleteUser(User User)throws Exception{
        dao.delete("UserXMapper.deleteUser", User);
    }
    /*
    * 批量删除用户
    */
    public void batchDeleteUser(List<User> users)throws Exception{
        dao.batchDelete("UserXMapper.batchDeleteUser", users);
    }



    /*
    * 保存用户IP
    */
    public void updateIPAddress(User User)throws Exception{
        dao.update("UserXMapper.updateIPAddress", User);
    }

    /*
    * 登录判断
    */
    public User getUserWithAuth(User User)throws Exception{
        return dao.selectOne("UserXMapper.getUserWithAuth", User);
    }
    /*
    * 跟新登录时间
    */
    public void updateLastLoginTime(User User)throws Exception{
        dao.update("UserXMapper.updateLastLoginTime", User);
    }

    /*
    *通过id获取数据
    */
    public User getUserAndRoleById(User user) throws Exception {
        return dao.selectOne("UserMapper.getUserAndRoleById", user);
    }
    

}
