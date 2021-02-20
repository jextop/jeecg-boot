package com.starter.auth.helper;

import com.alibaba.fastjson.JSON;
import com.starter.auth.config.AuthConfig;
import com.starter.auth.model.UserInfo;
import com.starter.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

/**
 * @author dingxl
 * @date 2/18/2021 12:54 PM
 */
@Component
@Slf4j
public class UserInfoHelper {
    @Autowired
    RedisUtil redisUtil;

    public void cacheUserInfo(String token, SysUser user) {
        if (user == null) {
            return;
        }

        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(user), UserInfo.class);
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
