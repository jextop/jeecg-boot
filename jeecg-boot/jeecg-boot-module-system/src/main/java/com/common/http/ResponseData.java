package com.common.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author dingxl
 */
@Slf4j
public class ResponseData implements ResponseHandler<byte[]> {
    private byte[] bytes;
    private String fileName;
    private String fileExt;
    private String contentType;
    private String contentLength;

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public String getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return StringUtils.isEmpty(contentLength) ? 0 : Integer.valueOf(contentLength);
    }

    public String saveFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            filePath = fileName;
        }
        if (StringUtils.isEmpty(filePath) || bytes == null || bytes.length <= 0) {
            return null;
        }

        // Check path
        File file = new File(filePath);
        String fileName = file.getName();
        filePath = file.getParent();

        file = new File(filePath == null ? "tmp" : filePath);
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }

        // Write to disc
        Path path = Paths.get(file.getPath(), fileName);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("Fail to save file: {}, {}", path.toString(), e.getMessage());
            return null;
        }
        return path.toString();
    }

    public void read(HttpServletResponse response) {
        if (response == null || bytes == null) {
            return;
        }

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            log.error("Error when RespData.read() {}", e.getMessage());
        }

        response.setContentLength(getContentLength());
        response.setContentType(contentType);
    }

    private String getHeader(HttpResponse response, String header) {
        return response.containsHeader(header) ? response.getFirstHeader(header).getValue() : null;
    }

    @Override
    public byte[] handleResponse(HttpResponse response) throws IOException {
        // 判断响应状态
        if (response.getStatusLine().getStatusCode() >= 300) {
            throw new IOException("HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
        }

        // 读取文件名称，Header: Content-Disposition: attachment;fileName=abc.txt
        String disposition = getHeader(response, "Content-Disposition");
        if (StringUtils.isNotEmpty(disposition) && disposition.contains("=")) {
            fileName = disposition.split("=")[1];
        }

        // 读取ContentType: audio/mp3
        contentType = getHeader(response, "Content-Type");
        if (StringUtils.isNotEmpty(contentType) && contentType.contains("/")) {
            fileExt = contentType.split("/")[1];
        }

        contentLength = getHeader(response, "Content-Length");

        // 读取返回内容
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new ClientProtocolException("Response contains no content");
        }

        bytes = EntityUtils.toByteArray(entity);
        return bytes;
    }
}
