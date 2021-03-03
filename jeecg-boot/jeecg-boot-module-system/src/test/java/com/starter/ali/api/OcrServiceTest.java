package com.starter.ali.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starter.ali.util.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jeecg.JeecgApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dingxl
 * @date 3/2/2021 7:45 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OcrServiceTest {
    @Autowired
    OcrService ocrService;

    private String getB64File(String fileName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(fileName);
        MockMultipartFile image = new MockMultipartFile(
                "image", null, null, stream
        );
        return Base64.encodeBase64String(image.getBytes());
    }

    @Test
    public void testIdCard() throws IOException {
        String b64 = getB64File("id-card.jpg");
        JSONObject ret = ocrService.idCard(b64, "face");
        log.info(JSON.toJSONString(ret));
        Assert.assertTrue(RespUtil.isSuccess(ret));
    }

    @Test
    public void testBankcard() throws IOException {
        String b64 = getB64File("bankcard.jpg");
        JSONObject ret = ocrService.bankcard(b64);
        log.info(JSON.toJSONString(ret));
        Assert.assertTrue(RespUtil.isSuccess(ret));
    }

    @Test
    public void testBusinessLicense() throws IOException {
        String b64 = getB64File("business-license.jpg");
        JSONObject ret = ocrService.businessLicense(b64);
        log.info(JSON.toJSONString(ret));
        Assert.assertTrue(RespUtil.isSuccess(ret));
    }
}
