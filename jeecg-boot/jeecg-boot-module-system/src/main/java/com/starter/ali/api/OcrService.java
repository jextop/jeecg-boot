package com.starter.ali.api;

import com.alibaba.fastjson.JSONObject;
import com.starter.ali.util.HttpUtils;
import com.starter.ali.util.RespUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dingxl
 * @date 3/2/2021 7:22 PM
 */
@Service
public class OcrService {
    private static final String ID_CARD_HOST = "https://dm-51.data.aliyun.com";
    private static final String ID_CARD_PATH = "/rest/160601/ocr/ocr_idcard.json";

    private static final String BANKCARD_HOST = "https://yhk.market.alicloudapi.com";
    private static final String BANKCARD_PATH = "/rest/160601/ocr/ocr_bank_card.json";

    private static final String LICENSE_HOST = "https://dm-58.data.aliyun.com";
    private static final String LICENSE_PATH = "/rest/160601/ocr/ocr_business_license.json";

    @Autowired
    ApiConfig config;

    /**
     * side: face, back
     */
    public JSONObject idCard(String b64Image, String side) throws IOException {
        HttpResponse response = HttpUtils.doPost(
                ID_CARD_HOST, ID_CARD_PATH, getHeaders(), null, new JSONObject() {{
                    put("image", b64Image);

                    if (StringUtils.isNotEmpty(side)) {
                        put("configure", new JSONObject() {{
                            put("side", side);
                        }}.toString());
                    }
                }}.toJSONString()
        );

        return RespUtil.getResult(response);
    }

    public JSONObject bankcard(String b64Image) throws IOException {
        HttpResponse response = HttpUtils.doPost(
                BANKCARD_HOST, BANKCARD_PATH, getHeaders(), null, new JSONObject() {{
                    put("image", b64Image);
                }}.toJSONString()
        );

        return RespUtil.getResult(response);
    }

    public JSONObject businessLicense(String b64Image) throws IOException {
        HttpResponse response = HttpUtils.doPost(
                LICENSE_HOST, LICENSE_PATH, getHeaders(), null, new JSONObject() {{
                    put("image", b64Image);
                }}.toJSONString()
        );

        return RespUtil.getResult(response);
    }

    private Map<String, String> getHeaders() {
        return new HashMap<String, String>() {{
            put("Authorization", "APPCODE " + config.getAppCode());
            put("Content-Type", "application/json; charset=UTF-8");
        }};
    }
}
