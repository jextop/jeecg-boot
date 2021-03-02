package com.starter.ali.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingxl
 * @date 3/1/2021 5:35 PM
 */
@Configuration
@ConfigurationProperties("ali.oss")
public class OssConfig {
    private String endpoint;
    private String accessKey;
    private String secretKey;

    public static final long PRE_SIGNED_URL_EXPIRATION = 10 * 60;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
