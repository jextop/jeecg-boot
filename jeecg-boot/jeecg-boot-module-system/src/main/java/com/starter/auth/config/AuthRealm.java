package com.starter.auth.config;

import com.common.util.YesNo;
import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.jeecg.common.system.vo.LoginUser;
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

    @Override
    public LoginUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        LoginUser user = super.checkUserTokenIsEffect(token);

        // 判断是否限制单点登录
        if (user != null && YesNo.isYes(user.getSoloLogin())) {
            UserInfo userInfo = userInfoHelper.getUserInfo(token);
            if (userInfo == null) {
                log.info("多客户端登录: " + user.getId() + "，无效token：" + token);
                throw new AuthenticationException("仅允许单点登录，请联系管理员!");
            }
        }

        return user;
    }
}
