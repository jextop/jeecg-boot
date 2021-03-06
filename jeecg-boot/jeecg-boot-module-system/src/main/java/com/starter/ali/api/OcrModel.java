package com.starter.ali.api;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dingxl
 * @date 3/6/2021 8:46 AM
 */
public class OcrModel extends JSONObject {
    public boolean isSuccess() {
        return getBooleanValue("success");
    }
}
