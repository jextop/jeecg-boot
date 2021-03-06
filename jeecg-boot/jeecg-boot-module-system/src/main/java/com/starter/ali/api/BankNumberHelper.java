package com.starter.ali.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.common.util.BankNameUtil;
import com.starter.ali.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author dingxl
 * @date 3/5/2021 8:50 PM
 */
@Slf4j
public class BankNumberHelper {
    /**
     * 请求接口和参数，免费公开，不需要配置权限
     */
    private static final String CARD_URL = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";
    private static final String CARD_NO = "cardNo";
    private static final String CARD_BIN_CHECK = "cardBinCheck";
    private static final String CARD_BIN_CHECK_FLAG = "true";

    /**
     * 返回字段
     */
    public static final String CARD_VALIDATED_FLAG = "validated";
    public static final String BANK_CODE_FLAG = "bank";
    public static final String BANK_NAME_FLAG = "name";

    /**
     * 调用支付宝接口校验
     */
    public static JSONObject isValid(String bankNumber) throws IOException {
        HttpResponse response = HttpUtils.doPost(CARD_URL, null, null, new HashMap<String, String>() {{
            put(CARD_NO, bankNumber);
            put(CARD_BIN_CHECK, CARD_BIN_CHECK_FLAG);
        }}, null);

        JSONObject ret = getResult(response);

        // 根据银行代码查找名称
        if (ret.getBooleanValue(CARD_VALIDATED_FLAG)) {
            String code = ret.getString(BANK_CODE_FLAG);
            ret.put(BANK_NAME_FLAG, BankNameUtil.getName(code));
        }
        return ret;
    }

    private static JSONObject getResult(HttpResponse resp) throws IOException {
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
}
