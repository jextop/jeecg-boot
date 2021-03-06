package com.starter.ali.api;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dingxl
 * @date 3/6/2021 10:25 AM
 */
public class BankNumberModel extends JSONObject {
    /**
     * 字段名称
     */
    public static final String VALIDATED_KEY = "validated";
    public static final String BANK_CODE_KEY = "bank";
    public static final String BANK_NAME_KEY = "name";

    public boolean isValid() {
        return getBooleanValue(VALIDATED_KEY);
    }

    public String getBankCode() {
        return getString(BANK_CODE_KEY);
    }

    public void setBankName(String bankName) {
        put(BANK_NAME_KEY, bankName);
    }

    public String getBankName() {
        return getString(BANK_NAME_KEY);
    }
}
