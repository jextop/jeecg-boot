package com.starter.ali.sms;

/**
 * @author dingxl
 * @date 3/4/2021 12:00 PM
 */
public enum SmsTemplate {
    /**
     * 短信模板，在阿里云SMS管理后台配置
     */
    VERIFY("SMS_204900011"),
    LOGIN("SMS_204900010"),
    REGISTER("SMS_204900008");

    SmsTemplate(String code) {
        this.code = code;
    }

    String code;

    public String getCode() {
        return code;
    }
}
