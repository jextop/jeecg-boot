package com.starter.auth.config;

import com.starter.auth.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dingxl
 * @date 2/20/2021 9:54 AM
 *
 * 使用示例：
 * @RequiresRoles(UserRole.ADMIN)
 * @RequiresRoles(value = {UserRole.ADMIN, UserRole.TENANT}, logical = Logical.OR)
 */
public class UserRole {
    public static final String ADMIN = "admin";
    public static final String TENANT = "tenant";

    public static Set<String> getRoles(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }

        // 获取角色
        List<String> roles = userInfo.getRoleList();
        return new HashSet<String>() {{
            if (CollectionUtils.isNotEmpty(roles)) {
                addAll(roles);
            }
        }};
    }
}
