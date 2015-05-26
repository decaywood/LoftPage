package org.decaywood.interceptor;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.decaywood.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * @author: decaywood
 * @date: 2015/5/26 14:48
 */
@Transactional
public class ShiroRealm extends AuthorizingRealm {

    @Resource(name = "userService")
    private UserService userService;
    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Iterator<String> iterator = principalCollection.fromRealm(getName()).iterator();
        String loginName = iterator.next();
//        User user = userService.queryByUserName(loginName);
//        if (user != null) {
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            info.setRoles();
//        }
        return null;

    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String)authenticationToken.getPrincipal();  				//得到用户名
        String password = new String((char[])authenticationToken.getCredentials()); 	//得到密码

        if(null != username && null != password){
            return new SimpleAuthenticationInfo(username, password, getName());
        }else{
            return null;
        }

    }


}
