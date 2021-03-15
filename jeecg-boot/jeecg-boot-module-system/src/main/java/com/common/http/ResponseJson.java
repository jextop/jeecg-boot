package com.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author dingxl
 */
public class ResponseJson implements ResponseHandler<JSONObject> {
    @Override
    public JSONObject handleResponse(HttpResponse resp) throws IOException {
        HttpEntity entity = resp.getEntity();
        if (entity == null) {
            throw new ClientProtocolException("Response contains no content");
        }

        // read content
        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        String jsonStr = EntityUtils.toString(entity, charset == null ? StandardCharsets.UTF_8 : charset);

        // parse JSON object
        return JSON.parseObject(jsonStr);
    }
}
