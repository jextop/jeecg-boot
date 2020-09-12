package com.starter.auth.config;

import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import com.starter.auth.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.jeecg.modules.shiro.authc.aop.JwtFilter;
import org.jeecg.modules.system.util.TenantContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author dingxl
 * @date 2/19/2021 6:02 PM
 */
@Slf4j
public class AuthFilter extends JwtFilter {
    static final String[] ADMIN_URL_ARRAY = new String[] {
            "sys/",
            "online/"
    };

    UserInfoHelper userInfoHelper;

    public AuthFilter(UserInfoHelper userInfoHelper) {
        this.userInfoHelper = userInfoHelper;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    /**
     * 登录用户不是管理员时，限制调用JeecgBoot项目的sys接口和online在线表单开发
     *
     * 验证步骤：
     * 1，使用非管理员账号登录，保存token，建议使用Postman工具
     * 2，设置请求头信息传递token，调用管理员接口，比如用户管理、权限配置
     * 3，验证结果：非管理员token，可以调用管理员接口
     * 4，影响：JeecgBoot开源项目使用广泛，广大用户对API非常熟悉，很容易使用普通的Postman工具，获取系统权限
     *
     * 修复方法：
     * 1，登录成功时，缓存用户信息
     * 2，每次接口调用时，验证用户是否管理员，决定是否允许调用sys接口和online在线表单开发
     * 3，除了使用拦截器统一处理权限逻辑，也可以采用通场的Shiro权限方案，缺点是改动代码较多，需要逐一为这些接口添加权限注解
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        boolean ret = super.preHandle(request, response);

        UserInfo userInfo = userInfoHelper.getUserInfo();
        if (userInfo != null && !UserUtil.isAdmin(userInfo)) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String requestUrl = servletRequest.getRequestURI();
            for(String adminUrl : ADMIN_URL_ARRAY) {
                if (requestUrl.contains(adminUrl)) {
                    log.error("接口：{} {} 非法请求，需要管理员权限，当前用户：{}, {}, {}",
                            servletRequest.getMethod(), requestUrl,
                            userInfo.getUsername(), userInfo.getPhone(), userInfo.getRoleList()
                    );
                    throw new AuthenticationException("非法请求，需要管理员权限!");
                }
            }
        }

        // 设置多租户信息，如果由客户端传入，存在安全漏洞
        TenantContext.setTenant(userInfo == null ? null : userInfo.getRelTenantIds());
        return ret;
    }
}
