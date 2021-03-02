package com.starter.ali.oss;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.JeecgApplication;
import org.jeecg.common.api.vo.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author dingxl
 * @date 3/1/2021 5:51 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OssControllerTest {
    @Autowired
    OssController controller;

    @Test
    public void testUpload() throws IOException {
        // 无效文件
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        Assert.assertFalse(controller.upload(request).isSuccess());
        Assert.assertFalse(controller.uploadMulti(request).isSuccess());

        // 上传文件
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("test.txt");

        MockMultipartFile multipart = new MockMultipartFile(
                "file", "test.txt", null, stream
        );
        request.addFile(multipart);

        Result<?> ret = controller.upload(request);
        String url = (String) ret.getResult();
        log.info(url);
        Assert.assertTrue(StringUtils.isNotEmpty(url));

        // 上传多个文件
        request.addFile(multipart);

        ret = controller.uploadMulti(request);
        List<String> urlList = (List<String>) ret.getResult();
        log.info(JSON.toJSONString(urlList));
        Assert.assertTrue(CollectionUtils.isNotEmpty(urlList));
    }
}
