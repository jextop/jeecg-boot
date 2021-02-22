package com.starter.auth.config;

import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.jeecg.modules.shiro.authc.ShiroRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author dingxl
 * @date 2/20/2021 10:11 PM
 */
@Component
@Slf4j
public class AuthRealm extends ShiroRealm {
    @Autowired
    @Lazy
    UserInfoHelper userInfoHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 不再调用super.doGetAuthorizationInfo()， 使用缓存的用户信息，不查数据库
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = userInfoHelper.getUserInfo();

        // 获取角色: admin, tenant
        Set<String> roleSet = UserRole.getRoles(userInfo);
        authInfo.addRoles(roleSet);

        // 获取权限: user_add, tenant_add
        Set<String> permissionSet = Permission.getPermissions(userInfo);
        authInfo.addStringPermissions(permissionSet);

        return authInfo;
    }
}
