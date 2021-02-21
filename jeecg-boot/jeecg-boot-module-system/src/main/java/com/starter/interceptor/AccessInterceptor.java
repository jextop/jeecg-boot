package com.starter.interceptor;

import com.common.http.RequestUtil;
import com.starter.annotation.AccessLimited;
import com.starter.exception.AccessLimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author dingxl
 */
@Component
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {
    final private RedisUtil redisUtil;

    @Autowired
    public AccessInterceptor(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AccessLimited accessLimited = method.getAnnotation(AccessLimited.class);
        if (accessLimited == null) {
            return true;
        }

        String key = String.format("%s%s_%s:%s",
                !accessLimited.ip() ? "" : RequestUtil.getIp(request),
                !accessLimited.session() ? "" : request.getSession().getId(),
                request.getMethod(),
                StringUtils.isNotEmpty(accessLimited.key()) ? accessLimited.key() : request.getRequestURI()
        );
        try {
            long count = redisUtil.incr(key, 1);
            if (count <= accessLimited.count()) {
                if (count == 1) {
                    redisUtil.expire(key, accessLimited.seconds());
                }
                return true;
            }
        } catch (RedisConnectionFailureException e) {
            log.error(e.getMessage());
            return true;
        }
        throw new AccessLimitException();
    }

    @Override
    public void postHandle(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView v
    ) throws Exception {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e
    ) throws Exception {
    }
}
