package com.starter.auth.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.YesNo;
import com.starter.auth.config.AuthConfig;
import com.starter.auth.model.UserInfo;
import com.common.http.RequestContext;
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

    public void soloLogin(SysUser user) {
        if (!YesNo.isYes(user.getSoloLogin())) {
            return;
        }

        // 读取用户信息
        String userId = user.getId();
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo == null || CollectionUtils.isEmpty(userInfo.getTokenSet())) {
            return;
        }

        // 删除token
        for (String token : userInfo.getTokenSet()) {
            String cacheKey = getCacheKey(token);
            redisUtil.del(cacheKey);
        }

        // 删除用户信息
        String cacheKey = getCacheKey(userId);
        redisUtil.del(cacheKey);
    }

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
        // 设置用户登录标志，根据token查找userId
        String cacheKey = getCacheKey(token);
        String userId = userInfo.getId();
        redisUtil.set(cacheKey, userId);
        redisUtil.expire(cacheKey, AuthConfig.USER_INFO_DURATION);

        // 获取已经登录的token信息
        cacheKey = getCacheKey(userId);
        UserInfo preInfo = (UserInfo) redisUtil.get(cacheKey);
        if (preInfo != null) {
            userInfo.addToken(preInfo.getTokenSet());
        }

        // 缓存用户信息，根据userId查找用户信息
        userInfo.addToken(token);
        redisUtil.set(cacheKey, userInfo);
        redisUtil.expire(cacheKey, AuthConfig.USER_INFO_DURATION);
    }

    public void deleteUserInfo(String token) {
        // 删除用户信息，注：多点登录时缓存共享
        UserInfo userInfo = getUserInfo(token);
        if (userInfo != null) {
            String cacheKey = getCacheKey(userInfo.getId());
            userInfo.deleteToken(token);

            if (CollectionUtils.isNotEmpty(userInfo.getTokenSet())) {
                redisUtil.set(cacheKey, userInfo);
                redisUtil.expire(cacheKey, AuthConfig.USER_INFO_DURATION);
            } else {
                redisUtil.del(cacheKey);
            }
        }

        // 删除用户登录标志
        redisUtil.del(getCacheKey(token));
    }

    public UserInfo getUserInfo() {
        return getUserInfo(RequestContext.getToken());
    }

    public UserInfo getUserInfo(String token) {
        // token -> userId
        String userId = (String) redisUtil.get(getCacheKey(token));
        return getUserInfoById(userId);
    }

    public UserInfo getUserInfoById(String userId) {
        try {
            // userId -> userInfo
            return (UserInfo) redisUtil.get(getCacheKey(userId));
        } catch (SerializationException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String getCacheKey(String token) {
        return String.format("%s_%s", AuthConfig.USER_INFO_CACHE_KEY, token);
    }
}
