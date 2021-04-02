package com.common.http;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dingxl
 * @date 4/2/2021 5:37 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
public class ResponseJsonTest {
    ResponseJson handler = new ResponseJson();

    @Mock
    HttpEntity entity;
    @Mock
    HttpResponse response;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(response.getEntity()).thenReturn(entity);
    }

    @Test
    public void testHandleResponse() throws IOException {
        // 空数据
        Mockito.when(entity.getContent()).thenReturn(null);

        JSONObject ret = handler.handleResponse(response);
        Assert.assertNull(ret);

        // JSON数据
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("test.json");
        Mockito.when(entity.getContent()).thenReturn(inputStream);

        ret = handler.handleResponse(response);
        log.info(ret.toJSONString());
        Assert.assertNotNull(ret);

        // 无效JSON数据
        ByteArrayInputStream byteStream = new ByteArrayInputStream("字符串".getBytes());
        Mockito.when(entity.getContent()).thenReturn(byteStream);

        expectedException.expect(JSONException.class);
        handler.handleResponse(response);
    }

    @Test
    public void testHandleResponseException() throws IOException {
        // 异常Response
        Mockito.when(response.getEntity()).thenReturn(null);

        expectedException.expect(ClientProtocolException.class);
        handler.handleResponse(response);
    }
}
