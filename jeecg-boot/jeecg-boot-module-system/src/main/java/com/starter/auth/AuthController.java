package com.starter.auth;

import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.system.util.RandImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author dingxl
 * @date 2/18/2021 12:49 PM
 */
@Api(tags = "登录认证")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("获取图片验证码")
    @GetMapping("/captcha/{key}")
    public Result<String> captcha(@PathVariable String key) throws IOException {
        Result<String> result = txtCaptcha(key);
        if (result.isSuccess()) {
            result.setResult(RandImageUtil.generate(result.getResult()));
        }
        return result;
    }

    @ApiOperation("获取文本验证码。注意生成环境限制访问。")
    @GetMapping("/captcha/txt/{key}")
    public Result<String> txtCaptcha(@PathVariable String key) {
        String code = RandomUtil.randomString(AuthConfig.CAPTCHA_CODE, AuthConfig.CAPTCHA_LENGTH);
        String lowerCaseCode = code.toLowerCase();

        String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
        redisUtil.set(realKey, lowerCaseCode, AuthConfig.CAPTCHA_DURATION);

        return new Result<String>() {{
            setResult(lowerCaseCode);
            setSuccess(true);
        }};
    }

    @ApiOperation("获取短信验证码，接口返回，不真实发送。注意安全措施：1，限制手机号码白名单；2，生产环境清理白名单。")
    @PostMapping("/sms/fake")
    public Result<String> fakeSms(@RequestParam String mobile) {
        // 白名单校验
        if (!ArrayUtils.contains(AuthConfig.PHONE_WHITE_ARRAY, mobile)) {
            return new Result<String>() {{
                setMessage("限制手机号码白名单");
                setSuccess(false);
            }};
        }

        Object smsCache = redisUtil.get(mobile);
        if (smsCache != null) {
            // 验证码10分钟内，仍然有效！
            return new Result<String>() {{
                setResult(String.valueOf(smsCache));
                setSuccess(true);
            }};
        }

        // 生成随机数
        String smsCode = RandomUtil.randomNumbers(AuthConfig.SMS_LENGTH);

        // 缓存验证码，10分钟内有效
        redisUtil.set(mobile, smsCode, AuthConfig.SMS_LIFETIME);

        return new Result<String>() {{
            setResult(String.valueOf(smsCode));
            setSuccess(true);
        }};
    }
}
