package com.starter.auth.util;

import com.starter.auth.config.UserRole;
import com.starter.auth.model.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingxl
 * @date 2/22/2021 5:13 PM
 */
@RunWith(SpringRunner.class)
public class UserUtilTest {
    @Test
    public void testIsAdmin() {
        Assert.assertFalse(UserUtil.isAdmin(null));

        // mock
        UserInfo userInfo = Mockito.mock(UserInfo.class);
        Mockito.when(userInfo.getRoleList()).thenReturn(null);

        // test
        Assert.assertFalse(UserUtil.isAdmin(userInfo));

        // verify
        Mockito.verify(userInfo, Mockito.times(1)).getRoleList();

        // 测试数据，不是管理员
        List<String> roleList = new ArrayList<String>() {{
            add("abc");
        }};

        Mockito.when(userInfo.getRoleList()).thenReturn(roleList);
        Assert.assertFalse(UserUtil.isAdmin(userInfo));
        Mockito.verify(userInfo, Mockito.times(2)).getRoleList();

        // 是管理员
        roleList.add(UserRole.ADMIN);
        Assert.assertTrue(UserUtil.isAdmin(userInfo));
        Mockito.verify(userInfo, Mockito.times(3)).getRoleList();
    }

    @Test
    public void testUtil() {
        // 保证测试覆盖率100%
        Assert.assertNotNull(new UserUtil());
    }
}
