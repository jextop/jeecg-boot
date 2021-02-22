package com.starter.interceptor;

import com.alibaba.fastjson.JSON;
import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
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
