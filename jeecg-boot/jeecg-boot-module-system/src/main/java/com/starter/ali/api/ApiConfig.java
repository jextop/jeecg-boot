package com.starter.ali.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingxl
 * @date 3/2/2021 7:22 PM
 */
@Configuration
@ConfigurationProperties("ali.api")
public class ApiConfig {
    private String appCode;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
