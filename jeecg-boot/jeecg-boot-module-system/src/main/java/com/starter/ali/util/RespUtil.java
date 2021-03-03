package com.starter.ali.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author dingxl
 * @date 3/2/2021 7:29 PM
 */
@Slf4j
public class RespUtil {
    public static JSONObject getResult(HttpResponse resp) throws IOException {
        String str = EntityUtils.toString(resp.getEntity());
        try {
            return JSON.parseObject(str);
        } catch (JSONException e) {
            log.error(e.getMessage());

            return new JSONObject() {{
                put("message", e.getMessage());
                put("result", str);
            }};
        }
    }

    public static boolean isSuccess(JSONObject result) {
        return result != null && result.getBooleanValue("success");
    }
}
