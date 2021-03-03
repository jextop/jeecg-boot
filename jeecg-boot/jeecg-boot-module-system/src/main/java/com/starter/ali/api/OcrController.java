package com.starter.ali.api;

import com.alibaba.fastjson.JSONObject;
import com.starter.ali.util.RespUtil;
import com.starter.annotation.AccessLimited;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author dingxl
 * @date 3/2/2021 7:22 PM
 */
@Api(tags = "OCR识别")
@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    OcrService ocrService;

    @AccessLimited(count = 1)
    @ApiOperation("身份证ocr")
    @PostMapping(value = "/id/card")
    public Result<?> idCard(@RequestParam MultipartFile image, @RequestParam String side) throws IOException {
        return idCard(image == null || image.isEmpty() ? null : Base64.encodeBase64String(image.getBytes()), side);
    }

    @AccessLimited(count = 1)
    @ApiOperation("身份证ocr")
    @PostMapping(value = "/id/card/b64")
    public Result<?> idCard(@RequestParam String b64Image, @RequestParam String side) throws IOException {
        if (StringUtils.isEmpty(b64Image)) {
            return Result.error("身份证不能为空！");
        }

        JSONObject result = ocrService.idCard(b64Image, side);
        if (RespUtil.isSuccess(result)) {
            return Result.OK(result);
        }

        return Result.error("请传入有效身份证图片！");
    }

    @AccessLimited(count = 1)
    @ApiOperation("银行卡ocr")
    @PostMapping(value = "/bankcard")
    public Result<?> bankcard(@RequestParam MultipartFile image) throws IOException {
        return bankcard(image == null || image.isEmpty() ? null : Base64.encodeBase64String(image.getBytes()));
    }

    @AccessLimited(count = 1)
    @ApiOperation("银行卡ocr")
    @PostMapping(value = "/bankcard/b64")
    public Result<?> bankcard(@RequestParam String b64Image) throws IOException {
        if (StringUtils.isEmpty(b64Image)) {
            return Result.error("银行卡不能为空！");
        }

        JSONObject result = ocrService.bankcard(b64Image);
        if (RespUtil.isSuccess(result)) {
            return Result.OK(result);
        }

        return Result.error("请传入有效银行卡图片！");
    }

    @AccessLimited(count = 1)
    @ApiOperation("营业执照ocr")
    @PostMapping(value = "/business/license")
    public Result<?> businessLicense(@RequestParam MultipartFile image) throws IOException {
        return businessLicense(image == null || image.isEmpty() ? null : Base64.encodeBase64String(image.getBytes()));
    }

    @AccessLimited(count = 1)
    @ApiOperation("营业执照ocr")
    @PostMapping(value = "/business/license/b64")
    public Result<?> businessLicense(@RequestParam String b64Image) throws IOException {
        if (StringUtils.isEmpty(b64Image)) {
            return Result.error("营业执照不能为空！");
        }

        JSONObject result = ocrService.businessLicense(b64Image);
        if (RespUtil.isSuccess(result)) {
            return Result.OK(result);
        }

        return Result.error("请传入有效营业执照图片！");
    }
}
