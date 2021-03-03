package com.starter.ali.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgApplication;
import org.jeecg.common.api.vo.Result;
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
 * @date 3/2/2021 8:40 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OcrControllerTest {
    @Autowired
    OcrController ocrController;


    private MockMultipartFile getFile(String fileName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(fileName);
        return new MockMultipartFile(
                "image", null, null, stream
        );
    }

    @Test
    public void testIdCard() throws IOException {
        Assert.assertFalse(ocrController.idCard((String) null, "face").isSuccess());
        Assert.assertFalse(ocrController.idCard("id-card", "back").isSuccess());

        MockMultipartFile image = getFile("id-card.jpg");
        Result<?> ret = ocrController.idCard(image, "face");
        log.info(JSON.toJSONString(ret.getResult()));
        Assert.assertTrue(ret.isSuccess());
    }

    @Test
    public void testBankcard() throws IOException {
        Assert.assertFalse(ocrController.bankcard((String) null).isSuccess());
        Assert.assertFalse(ocrController.bankcard("bankcard").isSuccess());

        MockMultipartFile image = getFile("bankcard.jpg");
        Result<?> ret = ocrController.bankcard(image);
        log.info(JSON.toJSONString(ret.getResult()));
        Assert.assertTrue(ret.isSuccess());
    }

    @Test
    public void testBusinessLicense() throws IOException {
        Assert.assertFalse(ocrController.businessLicense((String) null).isSuccess());
        Assert.assertFalse(ocrController.businessLicense("business-license").isSuccess());

        MockMultipartFile image = getFile("business-license.jpg");
        Result<?> ret = ocrController.businessLicense(image);
        log.info(JSON.toJSONString(ret.getResult()));
        Assert.assertTrue(ret.isSuccess());
    }
}
