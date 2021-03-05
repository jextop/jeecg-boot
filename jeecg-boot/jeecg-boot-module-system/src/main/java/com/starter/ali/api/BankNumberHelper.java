package com.starter.ali.api;

import com.alibaba.fastjson.JSONObject;
import com.starter.ali.util.BankNameUtil;
import com.starter.ali.util.HttpUtils;
import com.starter.ali.util.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dingxl
 * @date 3/5/2021 8:50 PM
 */
@Slf4j
public class BankNumberHelper {
    /**
     * 请求接口和参数
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

        JSONObject ret = RespUtil.getResult(response);

        // 根据银行代码查找名称
        if (ret.getBooleanValue(CARD_VALIDATED_FLAG)) {
            String code = ret.getString(BANK_CODE_FLAG);
            ret.put(BANK_NAME_FLAG, BankNameUtil.nameJson.getString(code));
        }
        return ret;
    }
}
