package com.starter.ali.oss;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author dingxl
 * @date 3/1/2021 5:35 PM
 */
@Service
@Slf4j
public class OssService {
    @Autowired
    OssConfig config;

    private static OSS client = null;
    private static final String OSS_URL = "https://%s.%s/%s";

    private static OSS inst(OssConfig config) {
        if (client == null) {
            synchronized (OssService.class) {
                if (client == null) {
                    client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());
                }
            }
        }
        return client;
    }

    public static String getFileName(MultipartFile file) {
        return StringUtils.isEmpty(file.getOriginalFilename())
                ? file.getName() : file.getOriginalFilename();
    }

    public static String getFileName(MultipartFile file, String appendMd5) {
        String fileName = getFileName(file);

        return String.format("%s_%s.%s",
                FilenameUtils.getBaseName(fileName),
                appendMd5,
                FilenameUtils.getExtension(fileName)
        );
    }

    public String upload(MultipartFile file) throws IOException {
        return upload(file, OssBucket.UPLOAD);
    }

    public String upload(MultipartFile file, OssBucket bucket) throws IOException {
        return upload(file, bucket.getName());
    }

    public String upload(MultipartFile file, String bucketName) throws IOException {
        String fileName = getFileName(file);
        fileName = new File(fileName).getName();
        return upload(file.getInputStream(), fileName, bucketName);
    }

    public String upload(InputStream inputStream, String fileName) throws IOException {
        OssBucket bucket = OssBucket.UPLOAD;
        return upload(inputStream, fileName, bucket.getName());
    }

    public String upload(String fileUrl, String fileName, OssBucket bucket) throws IOException {
        return upload(fileUrl, fileName, bucket.getName());
    }

    public String upload(String fileUrl, String fileName, String bucketName) throws IOException {
        InputStream inputStream = new URL(fileUrl).openStream();
        return upload(inputStream, fileName, bucketName);
    }

    public String upload(InputStream inputStream, String fileName, String bucketName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            fileName = DigestUtils.md5Hex(inputStream);
        }

        OSS ossClient = inst(config);
        PutObjectResult ret = ossClient.putObject(bucketName, fileName, inputStream);
        log.info(JSON.toJSONString(ret));

        return getUrl(ossClient, fileName, bucketName);
    }

    /**
     * 获得url链接
     */
    public String getUrl(String fileName, OssBucket bucket) {
        return getUrl(fileName, bucket.getName());
    }

    public String getUrl(String fileName, String bucketName) {
        OSS ossClient = inst(config);
        return getUrl(ossClient, fileName, bucketName);
    }

    private String getUrl(OSS ossClient, String fileName, String bucketName) {
        CannedAccessControlList ac = getAccessControl(ossClient, bucketName);
        if (CannedAccessControlList.Private != ac) {
            // 公共读
            return String.format(OSS_URL, bucketName, config.getEndpoint(), fileName);
        }

        // 如果是私有bucket，生成临时URL，设置过期时间，单位毫秒
        Date expiration = new Date(System.currentTimeMillis() + OssConfig.PRE_SIGNED_URL_EXPIRATION * 1000);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        return url == null ? null : url.toString().replace("http://", "https://");
    }

    public CannedAccessControlList getAccessControl(String bucketName) {
        OSS ossClient = inst(config);
        return getAccessControl(ossClient, bucketName);
    }

    private CannedAccessControlList getAccessControl(OSS ossClient, String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return null;
        }

        AccessControlList ac = ossClient.getBucketAcl(bucketName);
        return ac == null ? null : ac.getCannedACL();
    }
}
