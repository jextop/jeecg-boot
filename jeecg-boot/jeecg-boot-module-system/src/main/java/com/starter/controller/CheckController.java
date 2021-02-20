package com.starter.controller;

import com.starter.auth.annotation.RequiresAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: dingxl@xsheep.vip
 * @since 2020-10-04 13:07
 */
@Api(tags = {"检查运行状态"})
@RestController
@RequestMapping("/chk")
public class CheckController {
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("检查服务是否运行")
    @GetMapping
    public String chk() {
        return "ok";
    }

    @RequiresAdmin
    @ApiOperation("检查缓存系统")
    @GetMapping("/cache")
    public Object cache(@RequestAttribute(required = false) String ip, @RequestParam(required = false) String text) {
        // Get a unique key
        String key = String.format("cache_test_%s_%s_缓存_%s", ip, System.currentTimeMillis(), text);

        // Set cache
        redisUtil.set(key, key, 3);

        // Get cache
        String str = (String) redisUtil.get(key);

        // Delete key
        redisUtil.del(key);

        return new HashMap<String, Object>() {{
            put("chk", "cache");
            put("msg", str);
            put("status", key.equals(str));
        }};
    }
}
