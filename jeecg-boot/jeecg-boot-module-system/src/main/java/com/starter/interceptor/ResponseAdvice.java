package com.starter.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author dingxl
 */
@RestControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter param, Class clazz) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter param, MediaType mediaType, Class clazz,
            ServerHttpRequest request, ServerHttpResponse response
    ) {
        // 输出日志，API返回数据
        log.info("接口返回：{} {} 结果：{}",
                request.getMethodValue(), request.getURI().getPath(), JSON.toJSONString(body)
        );

        return body;
    }
}
