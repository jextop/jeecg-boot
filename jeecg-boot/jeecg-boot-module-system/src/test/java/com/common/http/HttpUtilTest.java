package com.common.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dingxl
 * @date 3/15/2021 4:38 PM
 */
@RunWith(SpringRunner.class)
@Slf4j
public class HttpUtilTest {
    @Test
    public void testHttpGet() {
        String ret = HttpUtil.sendHttpGet("https://blog.51cto.com/13851865");
        log.info(ret);
        Assert.assertNotNull(ret);
    }

    @Test
    public void testHttpForm() throws UnsupportedEncodingException {
        String url = "https://openapi.baidu.com/oauth/2.0/token";
        Map<String, Object> headers = new HashMap<String, Object>() {{
            put("Content-Type", "application/x-www-form-urlencoded");
        }};

        Map<String, Object> params = new HashMap<String, Object>() {{
            put("grant_type", "client_credentials"); // 固定为“client_credentials”
            put("client_id", "kVcnfD9iW2XVZSMaLMrtLYIz"); // 应用的API Key
            put("client_secret", "O9o1O213UgG5LFn0bDGNtoRN3VWl2du6");  // 应用的Secret Key
        }};

        JSONObject ret = HttpUtil.sendHttpForm(url, headers, params, new ResponseJson());
        log.info(ret.toJSONString());
        Assert.assertNotNull(ret);

        String token = ret.getString("access_token");
        log.info(token);
        testBaiduTts(token);
    }

    private void testBaiduTts(String token) throws UnsupportedEncodingException {
        String url = "https://tsn.baidu.com/text2audio";
        Map<String, Object> headers = new HashMap<String, Object>() {{
            put("Content-Type", "application/x-www-form-urlencoded");
        }};

        Map<String, Object> params = new HashMap<String, Object>() {{
            put("tex", URLEncoder.encode("调用百度AI语音处理", "utf-8")); // 合成文本，UTF-8编码，2048个中文字或者英文数字
            put("tok", token); // 调用鉴权认证接口获取到的access_token
            put("cuid", "starter_api_http_util"); // 用户唯一标识，用来计算UV值，长度为60字符，常用用户MAC地址或IMEI码
            put("ctp", "1"); // 客户端类型选择，web端填写固定值1
            put("lan", "zh"); // 语言选择,目前只有中英文混合模式，固定值zh
            put("spd", "6"); // 语速，取值0-15，默认为5中语速
            put("pit", "5"); // 音调，取值0-15，默认为5中语调
            put("vol", "5"); // 音量，取值0-15，默认为5中音量
            put("per", "1"); // 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫
            put("aue", "6"); // 3为mp3格式(默认)； 4为pcm-16k；5为pcm-8k；6为wav（内容同pcm-16k）
        }};

        ResponseData resp = new ResponseData();
        byte[] ret = HttpUtil.sendHttpForm(url, headers, params, resp);
        Assert.assertNotNull(ret);

        String file = resp.saveFile(String.format("http_util_test.%s", resp.getFileExt()));
        log.info(file);

        long len = resp.getContentLength();
        String b64Str = Base64.encodeBase64String(resp.getBytes());
        String format = resp.getFileExt();
        testBaiduAsr(token, format, b64Str, len);
    }

    private void testBaiduAsr(String token, String format, String b64Data, long len) {
        String url = "http://vop.baidu.com/server_api";
        Map<String, Object> headers = new HashMap<String, Object>() {{
            put("Content-Type", "application/json");
        }};

        Map<String, Object> params = new HashMap<String, Object>() {{
            put("format", format); // 音频格式：pcm/wav/amr/m4a，推荐pcm
            put("rate", 16000); // 音频采样频率，固定值16000
            put("dev_pid", 1537); // 语音模型，默认1537普通话，1737英语
            put("channel", 1); // 声道数量，仅支持单声道1
            put("cuid", "starter_api_http_util"); // 用户唯一标识，用来计算UV值，长度为60字符，常用用户MAC地址或IMEI码
            put("token", token); // 调用鉴权认证接口获取到的access_token
            put("len", len); // 音频长度，base64前
            put("speech", b64Data); // 音频数据，base64（FILE_CONTENT）
        }};

        JSONObject ret = HttpUtil.sendHttpPost(url, headers, params, new ResponseJson());
        log.info(ret.toJSONString());
        Assert.assertNotNull(ret);

        JSONArray textArr = ret.getJSONArray("result");
        if (CollectionUtils.isEmpty(textArr)) {
            Assert.assertNotNull(ret.getString("err_msg"));
        } else {
            log.info(textArr.toJSONString());
            Assert.assertNotNull(textArr);
        }
    }
}
