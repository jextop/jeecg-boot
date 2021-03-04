package com.starter.ali.sms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.JeecgApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author dingxl
 * @date 3/4/2021 12:51 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SmsServiceTest {
    @Autowired
    SmsService smsService;

    @Test
    public void testSendSms() throws ClientException {
        Assert.assertEquals(
                "isv.TEMPLATE_MISSING_PARAMETERS",
                smsService.sendSms("13764356791", SmsTemplate.VERIFY, (String) null).getCode()
        );

        Assert.assertEquals(
                "isv.TEMPLATE_MISSING_PARAMETERS",
                smsService.sendSms("13764356791", SmsTemplate.LOGIN, new JSONObject() {{
                    put("code_test", "123456");
                }}).getCode()
        );
    }

    @Test
    public void testSendBatch() throws ClientException {
        Assert.assertEquals(
                "OK",
                smsService.sendBatch(SmsTemplate.LOGIN, new JSONArray() {{
                    add(new JSONObject() {{
                        put("code_test", "123456");
                    }});
                    add(new JSONObject() {{
                        put("code_test", "123456");
                        put("phone", "13764356791");
                    }});
                }}).getCode()
        );
    }

    @Test
    public void testQuerySms() throws ClientException {
        SmsModel ret = smsService.querySms("13764356791", LocalDate.now(), 1, 10);
        log.info(ret.toJSONString());

        Assert.assertEquals("OK", ret.getCode());
        Assert.assertEquals("OK", ret.getMessage());
        Assert.assertTrue(StringUtils.isNotEmpty(ret.getBizId()) || StringUtils.isNotEmpty(ret.getRequestId()));
    }

    @Test
    public void testSendRequest() throws ClientException {
        Assert.assertEquals("OK", smsService.sendRequest(new CommonRequest() {{
            setSysAction("QuerySendDetails");
            putQueryParameter("PhoneNumber", "13764356791");
            putQueryParameter("SendDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            putQueryParameter("PageSize", String.valueOf(10));
            putQueryParameter("CurrentPage", String.valueOf(1));
        }}).getCode());
    }
}
