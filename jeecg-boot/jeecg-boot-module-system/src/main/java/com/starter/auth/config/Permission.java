package com.starter.auth.config;

import com.starter.auth.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dingxl
 * @date 2/20/2021 9:57 AM
 *
 * 使用示例：
 * @RequiresPermissions(Permission.USER_ADD)
 * @RequiresPermissions(value = {Permission.USER_ADD, Permission.TENANT_ADD}, logical = Logical.OR)
 */
public class Permission {
    public static final String USER_ADD = "user:add";
    public static final String TENANT_ADD = "tenant:add";

    public static Set<String> getPermissions(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }

        // 获取权限
        List<String> permissions = userInfo.getActionList();
        return new HashSet<String>() {{
            if (CollectionUtils.isNotEmpty(permissions)) {
                addAll(permissions);
            }
        }};
    }
}
