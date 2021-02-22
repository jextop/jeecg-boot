package com.starter.auth.config;

import com.starter.auth.model.UserInfo;
import com.starter.auth.util.UserUtil;
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
        Set<String> roleSet = new HashSet<String>() {{
            if (CollectionUtils.isNotEmpty(roles)) {
                addAll(roles);
            }
        }};

        // 自定义角色，或者数据库角色名称和代码中不一致
        if (UserUtil.isAdmin(userInfo)) {
            roleSet.add(ADMIN);
        }
        return roleSet;
    }
}
