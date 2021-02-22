package com.common.http;

import org.jeecg.modules.shiro.vo.DefContants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author dingxl
 * @date 2/22/2021 4:38 PM
 */
@RunWith(SpringRunner.class)
public class RequestContextTest {
    @Test
    public void testToken() {
        Assert.assertNull(RequestContext.getToken(null));

        String token = String.valueOf(System.currentTimeMillis());
        MockHttpServletRequest request = new MockHttpServletRequest() {{
            addHeader(DefContants.X_ACCESS_TOKEN, token);
        }};

        Assert.assertEquals(token, RequestContext.getToken(request));

        RequestContextHolder.setRequestAttributes(null, false);
        Assert.assertNull(RequestContext.getToken());
    }

    @Test
    public void testUtil() {
        // 保证测试覆盖率100%
        Assert.assertNotNull(new RequestContext());
    }
}
