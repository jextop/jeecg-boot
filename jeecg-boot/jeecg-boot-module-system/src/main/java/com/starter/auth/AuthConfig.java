package com.starter.auth;

/**
 * @author dingxl
 * @date 2/18/2021 12:54 PM
 */
public class AuthConfig {
    public static final int CAPTCHA_LENGTH = 4;
    public static final int CAPTCHA_DURATION = 60;
    public static final String CAPTCHA_CODE = "qwertyuipkjhgfdsazxcvbnmQWERTYUPKJHGFDSAZXCVBNM1234567890";

    public static final int SMS_LENGTH = 6;
    public static final int SMS_LIFETIME = 60 * 10;
    public static final String[] PHONE_WHITE_ARRAY = new String[]{"13800001604", "18611111111"};
}
