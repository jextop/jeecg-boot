package com.starter.util;

import org.jeecg.modules.shiro.vo.DefContants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dingxl
 * @date 2/19/2021 5:35 PM
 */
public class RequestContext {
    public static String getToken(HttpServletRequest request) {
        // 从请求头中读取token
        return request == null ? null : request.getHeader(DefContants.X_ACCESS_TOKEN);
    }

    public static String getToken() {
        // SpringBoot封装的RequestContextHolder，包含了请求信息
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs == null ? null : getToken(attrs.getRequest());
    }
}
