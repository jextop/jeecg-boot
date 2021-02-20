package com.starter.auth.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starter.auth.config.AuthConfig;
import com.starter.auth.model.UserInfo;
import com.starter.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.system.controller.SysPermissionController;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingxl
 * @date 2/18/2021 12:54 PM
 */
@Component
@Slf4j
public class UserInfoHelper {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ISysBaseAPI sysBaseApi;
    @Autowired
    SysPermissionController permissionController;

    public void cacheUserInfo(String token, SysUser user) {
        if (user == null) {
            return;
        }

        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(user), UserInfo.class);

        // 保存角色信息
        List<String> roles = sysBaseApi.getRolesByUsername(user.getUsername());
        userInfo.setRoleList(roles);

        // 查询角色权限信息
        Result<?> result = permissionController.getUserPermissionByToken(token);
        JSONObject jsonObject = (JSONObject) result.getResult();
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray("auth");

            if (CollectionUtils.isNotEmpty(jsonArray)) {
                List<String> actionList = new ArrayList<>();
                for (int i = jsonArray.size() - 1; i >= 0; i--) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    actionList.add(object.getString("action"));
                }

                userInfo.setActionList(actionList);
            }
        }

        cacheUserInfo(token, userInfo);
    }

    public void cacheUserInfo(String token, UserInfo userInfo) {
        String cacheKey = getCacheKey(token);
        redisUtil.set(cacheKey, userInfo);
        redisUtil.expire(cacheKey, AuthConfig.USER_INFO_DURATION);
    }

    public void deleteUserInfo(String token) {
        redisUtil.del(getCacheKey(token));
    }

    public UserInfo getUserInfo() {
        return getUserInfo(RequestContext.getToken());
    }

    public UserInfo getUserInfo(String token) {
        try {
            return (UserInfo) redisUtil.get(getCacheKey(token));
        } catch (SerializationException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String getCacheKey(String token) {
        return String.format("%s_%s", AuthConfig.USER_INFO_CACHE_KEY, token);
    }
}
