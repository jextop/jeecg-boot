package com.starter.config;

import com.starter.interceptor.AccessInterceptor;
import com.starter.interceptor.ApiLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ding
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    ApiLogInterceptor apiLogInterceptor;
    @Autowired
    AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注意顺序，先添加的先执行
        registry.addInterceptor(apiLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(accessInterceptor).addPathPatterns("/**");
    }
}
