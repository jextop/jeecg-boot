package com.starter.ali.sms;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dingxl
 * @date 3/4/2021 12:10 PM
 */
public class SmsModel extends JSONObject {
    public String getCode() {
        return getString("Code");
    }

    public String getBizId() {
        return getString("BizId");
    }

    public String getMessage() {
        return getString("Message");
    }

    public String getRequestId() {
        return getString("RequestId");
    }
}
