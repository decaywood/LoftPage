package org.decaywood.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.decaywood.entity.User;
import org.decaywood.service.UserService;
import org.decaywood.utils.NameDomainMapper;
import org.decaywood.utils.TimeUtils;
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

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();


        String userName = (String)authenticationToken.getPrincipal();  				//得到用户名
        char[] password = (char[])authenticationToken.getCredentials();


        String hashedPassowrd = new SimpleHash("SHA-1", userName, password).toString();

        User user = new User().setUserLoginName(userName).setUserPassword(hashedPassowrd);
        user = userService.queryByUser(user);
        if(user == null) {
            throw new AuthenticationException();
        }

        user.setUserLastLoginTime(TimeUtils.getSqlTime());
        userService.updateUserLastLoginTime(user);

        session.setAttribute(NameDomainMapper.SESSION_USER_LOGIN_NAME.name(), user);
        session.removeAttribute(NameDomainMapper.SESSION_SECURITY_CODE.name());

        return new SimpleAuthenticationInfo(userName, password, getName());

    }


}
