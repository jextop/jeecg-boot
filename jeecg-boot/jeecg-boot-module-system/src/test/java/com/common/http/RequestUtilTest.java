package com.common.http;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dingxl
 * @date 2/22/2021 4:41 PM
 */
@RunWith(SpringRunner.class)
public class RequestUtilTest {
    @Test
    public void testGetIp() {
        Assert.assertNull(RequestUtil.getIp(null));

        MockHttpServletRequest request = new MockHttpServletRequest();
        Assert.assertNull(RequestUtil.getIp(request));

        String ip = "192.168.1.3";
        request.setRemoteAddr(ip);
        Assert.assertEquals(ip, RequestUtil.getIp(request));

        request.addHeader("X-Forwarded-For", ip);
        Assert.assertEquals(ip, RequestUtil.getIp(request));
    }

    @Test
    public void testUtil() {
        // 保证测试覆盖率100%
        Assert.assertNotNull(new RequestUtil());
    }
}
