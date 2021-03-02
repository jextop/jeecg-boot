package com.starter.ali.oss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.JeecgApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dingxl
 * @date 3/1/2021 5:52 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeecgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OssServiceTest {
    @Autowired
    OssService ossService;

    @Test
    public void testUploadStream() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("test.txt");

        MockMultipartFile multipart = new MockMultipartFile(
                "file", "test.txt", null, stream
        );

        String fileName = ossService.upload(multipart);
        log.info(fileName);
        Assert.assertNotNull(fileName);
    }

    @Test
    public void testUploadStr() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("字符串".getBytes());
        String fileName = ossService.upload(inputStream, "字符串.txt");
        log.info(fileName);
        Assert.assertNotNull(fileName);
    }

    @Test
    public void testUploadFile() throws IOException {
        File txtFile = File.createTempFile("tmp", ".txt");

        DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(txtFile.getPath())));
        writer.write("临时 file".getBytes());
        writer.close();

        String fileName = ossService.upload(new FileInputStream(txtFile), null);
        log.info(fileName);
        Assert.assertNotNull(fileName);
    }

    @Test
    public void testUploadUrl() throws IOException {
        String fileName = ossService.upload(
                "https://gitee.com/jiekebu/jeecg-boot/blob/master/.gitignore", "url.txt", OssBucket.DOC
        );
        log.info(fileName);
        Assert.assertNotNull(fileName);
    }

    @Test
    public void testGetFileName() {
        MockMultipartFile multipart = new MockMultipartFile("file", "test.txt", null, (byte[]) null);
        Assert.assertTrue(StringUtils.isNotEmpty(OssService.getFileName(multipart, "def")));

        multipart = new MockMultipartFile("file", null, null, (byte[]) null);
        Assert.assertTrue(StringUtils.isNotEmpty(OssService.getFileName(multipart, "def")));
    }

    @Test
    public void testGetUrl() {
        Assert.assertTrue(StringUtils.isNotEmpty(ossService.getUrl("abc", OssBucket.UPLOAD)));
    }

    @Test
    public void testGetAccessControl() {
        Assert.assertNull(ossService.getAccessControl(null));
        Assert.assertNotNull(ossService.getAccessControl(OssBucket.UPLOAD.getName()));
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
    }
}
