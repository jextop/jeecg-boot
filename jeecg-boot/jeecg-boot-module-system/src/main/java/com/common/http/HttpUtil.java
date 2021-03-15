package com.common.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dingxl
 */
@Slf4j
public class HttpUtil {
    private static final String CHARSET_UTF_8 = "utf-8";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";

    private static final int MAX_TOTAL = 100;
    private static final int MAX_PER_ROUTE = 20;

    private static final int SOCKET_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int REQUEST_TIMEOUT = 10000;

    private static PoolingHttpClientConnectionManager connectionPool;
    private static RequestConfig requestConfig;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();

            connectionPool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connectionPool.setMaxTotal(MAX_TOTAL);
            connectionPool.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(connectionPool)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }

    private static <T> T sendHttpRequest(HttpRequestBase httpRequest, ResponseHandler<T> handler) {
        try {
            return getHttpClient().execute(httpRequest, handler);
        } catch (ClientProtocolException e) {
            log.error("Error when sendHttpRequest", e.getMessage());
        } catch (IOException e) {
            log.error("Error when sendHttpRequest", e.getMessage());
        }
        return null;
    }

    public static <T> T sendHttpGet(String httpUrl, ResponseHandler<T> handler) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpRequest(httpGet, handler);
    }

    public static String sendHttpGet(String httpUrl) {
        return sendHttpGet(httpUrl, new ResponseString());
    }

    public static <T> T sendHttpGet(String httpUrl, Map<String, Object> headers, ResponseHandler<T> handler) {
        HttpGet httpGet = new HttpGet(httpUrl);
        fillHeaders(httpGet, headers);
        return sendHttpRequest(httpGet, handler);
    }

    public static String sendHttpGet(String httpUrl, Map<String, Object> headers) {
        HttpGet httpGet = new HttpGet(httpUrl);
        fillHeaders(httpGet, headers);
        return sendHttpRequest(httpGet, new ResponseString());
    }

    public static <T> T sendHttpGet(
            String httpUrl, Map<String, Object> headers, Map<String, Object> params, ResponseHandler<T> handler
    ) {
        if (!MapUtils.isEmpty(params)) {
            List<String> paramList = new ArrayList<String>();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                Object value = param.getValue();
                if (value != null) {
                    try {
                        paramList.add(String.format("%s=%s",
                                param.getKey(), URLEncoder.encode(String.valueOf(value), CHARSET_UTF_8)
                        ));
                    } catch (UnsupportedEncodingException e) {
                        log.error("Error when sendHttpGet: {}", e.getMessage());
                    }
                }
            }

            String paramStr = StringUtils.join(paramList, "&");
            httpUrl = String.format("%s%s%s", httpUrl, httpUrl.indexOf("?") > 0 ? "&" : "?", paramStr);
        }
        return sendHttpGet(httpUrl, headers, handler);
    }

    public static String sendHttpGet(String httpUrl, Map<String, Object> headers, Map<String, Object> params) {
        return sendHttpGet(httpUrl, headers, params, new ResponseString());
    }

    public static <T> T sendHttpPost(
            String httpUrl, Map<String, Object> headers, Map<String, Object> params, ResponseHandler<T> handler
    ) {
        HttpPost httpPost = new HttpPost(httpUrl);
        fillHeaders(httpPost, headers);

        if (!MapUtils.isEmpty(params)) {
            String jsonStr = JSON.toJSONString(params);
            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentType(CONTENT_TYPE_JSON);
            httpPost.setEntity(stringEntity);
        }
        return sendHttpRequest(httpPost, handler);
    }

    public static String sendHttpPost(String httpUrl, Map<String, Object> headers, Map<String, Object> params) {
        return sendHttpPost(httpUrl, headers, params, new ResponseString());
    }

    public static <T> T sendHttpForm(
            String httpUrl, Map<String, Object> headers, String paramStr, ResponseHandler<T> handler
    ) {
        HttpPost httpPost = new HttpPost(httpUrl);
        fillHeaders(httpPost, headers);

        if (StringUtils.isNotEmpty(paramStr)) {
            StringEntity formEntity = new StringEntity(paramStr, CHARSET_UTF_8);
            httpPost.setEntity(formEntity);
        }
        return sendHttpRequest(httpPost, handler);
    }

    public static <T> T sendHttpForm(
            String httpUrl, Map<String, Object> headers, Map<String, Object> params, ResponseHandler<T> handler
    ) {
        HttpPost httpPost = new HttpPost(httpUrl);
        fillHeaders(httpPost, headers);

        if (!MapUtils.isEmpty(params)) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> param : params.entrySet()) {
                Object value = param.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(value)));
                }
            }

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            } catch (Exception e) {
                log.error("Error when sendHttpForm: {}", e.getMessage());
            }
        }
        return sendHttpRequest(httpPost, handler);
    }

    public static <T> T sendHttpPut(
            String httpUrl, Map<String, Object> headers, Map<String, Object> params, ResponseHandler<T> handler
    ) {
        HttpPut httpPut = new HttpPut(httpUrl);
        fillHeaders(httpPut, headers);

        if (!MapUtils.isEmpty(params)) {
            String jsonStr = JSON.toJSONString(params);
            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentType(CONTENT_TYPE_JSON);
            httpPut.setEntity(stringEntity);
        }
        return sendHttpRequest(httpPut, handler);
    }

    public static <T> T sendHttpDelete(String httpUrl, Map<String, Object> headers, ResponseHandler<T> handler) {
        HttpDelete httpDelete = new HttpDelete(httpUrl);
        fillHeaders(httpDelete, headers);
        return sendHttpRequest(httpDelete, handler);
    }

    private static void fillHeaders(HttpRequestBase request, Map<String, Object> headers) {
        if (request == null || MapUtils.isEmpty(headers)) {
            return;
        }

        for (Map.Entry<String, Object> header : headers.entrySet()) {
            request.addHeader(header.getKey(), String.valueOf(header.getValue()));
        }
    }
}
