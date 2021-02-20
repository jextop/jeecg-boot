package com.starter.auth.config;

import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import org.jeecg.modules.shiro.authc.aop.JwtFilter;
import org.jeecg.modules.system.util.TenantContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author dingxl
 * @date 2/19/2021 6:02 PM
 */
public class AuthFilter extends JwtFilter {
    UserInfoHelper userInfoHelper;

    public AuthFilter(UserInfoHelper userInfoHelper) {
        this.userInfoHelper = userInfoHelper;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        boolean ret = super.preHandle(request, response);

        // 设置多租户信息，如果由客户端传入，存在安全漏洞
        UserInfo userInfo = userInfoHelper.getUserInfo();
        TenantContext.setTenant(userInfo == null ? null : userInfo.getRelTenantIds());

        return ret;
    }
}
