package com.starter.auth.config;

import com.starter.auth.helper.UserInfoHelper;
import com.starter.auth.model.UserInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author dingxl
 * @date 2/28/2021 6:08 PM
 */
@RunWith(SpringRunner.class)
public class AuthFilterTest {
    @Mock
    UserInfoHelper userInfoHelper;

    AuthFilter authFilter;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        authFilter = new AuthFilter(userInfoHelper);
    }

    @Test
    public void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "file") {{
            addHeader("Origin", "Origin");
            addHeader("Access-Control-Request-Headers", "Access-Control-Request-Headers");
        }};
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 用户没有登录
        Mockito.when(userInfoHelper.getUserInfo()).thenReturn(null);
        Assert.assertTrue(authFilter.preHandle(request, response));

        // 管理员登录
        Mockito.when(userInfoHelper.getUserInfo()).thenReturn(new UserInfo() {{
            setRoleList(new ArrayList<String>() {{
                add(UserRole.ADMIN);
            }});
        }});

        request.setRequestURI("file/");
        Assert.assertTrue(authFilter.preHandle(request, response));

        request.setRequestURI("sys/");
        Assert.assertTrue(authFilter.preHandle(request, response));

        // 普通用户登录
        Mockito.when(userInfoHelper.getUserInfo()).thenReturn(new UserInfo() {{
            setRoleList(new ArrayList<String>() {{
                add("user");
            }});
        }});

        request.setRequestURI("file/");
        Assert.assertTrue(authFilter.preHandle(request, response));

        request.setRequestURI("sys/");
        expectedEx.expect(AuthenticationException.class);
        expectedEx.expectMessage("非法请求，需要管理员权限!");
        authFilter.preHandle(request, response);
    }
}
