package com.springboot.shiro.shiro;

import com.springboot.shiro.bean.Permission;
import com.springboot.shiro.bean.Role;
import com.springboot.shiro.bean.User;
import com.springboot.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获取登录名的权限和角色
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取用户名
        String userName = (String)principalCollection.getPrimaryPrincipal();
        User user = userService.getUserByName(userName);
        for (Role role : user.getRoles()){
            //添加该用户所有的角色
            authorizationInfo.addRole(role.getRole());
            for (Permission permission : role.getPermissions()){
                //添加该用户所有的权限
                authorizationInfo.addStringPermission(permission.getPermissionsName());
            }
        }
        return authorizationInfo;
    }

    /**
     * 用户身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取被验证的用户名
        String userName = (String)token.getPrincipal();

        if(userName == null){
            return null;
        }
        User user = userService.getUserByName(userName);

        /*
            参数：
                第一个：用户名
                第二个：用户密码
                第三个：盐：用于密码加密
                第四个：当前realm的名字
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
        return authenticationInfo;
    }
}
