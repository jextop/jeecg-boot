package com.starter.interceptor;

import com.alibaba.fastjson.JSON;
import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import com.starter.auth.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ding
 */
@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    static final String[] ADMIN_URL_ARRAY = new String[] {
            "sys/",
            "online/"
    };

    final private UserInfoHelper userInfoHelper;

    @Autowired
    public RequestInterceptor(UserInfoHelper userInfoHelper) {
        this.userInfoHelper = userInfoHelper;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        String method = request.getMethod();
        if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
            return true;
        }

        // 输出日志，API调用信息
        UserInfo userInfo = userInfoHelper.getUserInfo();
        log.info("接口：{} {} 入参：{}; 用户：{}",
                method, request.getRequestURI(), JSON.toJSONString(request.getParameterMap()),
                userInfo == null ? null : JSON.toJSONString(userInfo)
        );

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
        if (userInfo != null && !UserUtil.isAdmin(userInfo)) {
            String requestUrl = request.getRequestURI();
            for(String adminUrl : ADMIN_URL_ARRAY) {
                if (requestUrl.contains(adminUrl)) {
                    log.error("接口：{} {} 非法请求，需要管理员权限，当前用户：{}, {}, {}",
                            method, requestUrl, userInfo.getUsername(), userInfo.getPhone(), userInfo.getRoleList()
                    );
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView v
    ) throws Exception {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex
    ) throws Exception {
    }
}
