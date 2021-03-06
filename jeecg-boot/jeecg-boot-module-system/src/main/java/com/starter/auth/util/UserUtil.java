package com.starter.auth.util;

import com.starter.auth.config.AuthConfig;
import com.starter.auth.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * @author dingxl
 * @date 2/22/2021 9:34 AM
 */
public class UserUtil {
    /**
     * 根据用户角色名称判断是否Admin
     */
    public static boolean isAdmin(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }

        List<String> roles = userInfo.getRoleList();
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        for (String role : roles) {
            if (ArrayUtils.contains(AuthConfig.ADMIN_ROLE_CODE_ARRAY, role)) {
                return true;
            }
        }
        return false;
    }
}
