package com.starter.ali.oss;

import com.starter.annotation.AccessLimited;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dingxl
 * @date 3/1/2021 5:35 PM
 */
@Api(tags = "OSS云存储服务")
@RestController
@RequestMapping("/oss")
@Slf4j
public class OssController {
    @Autowired
    OssService ossService;

    @AccessLimited
    @ApiOperation("上传单个文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<?> upload(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");
        if (null == file) {
            return Result.error("请选择文件");
        }

        String fileUrl = ossService.upload(file, OssBucket.UPLOAD);
        return Result.OK(fileUrl);
    }

    @AccessLimited
    @ApiOperation("上传多个文件")
    @RequestMapping(value = "/upload/multi", method = RequestMethod.POST)
    public Result<?> uploadMulti(MultipartHttpServletRequest request) throws IOException {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        if (MapUtils.isEmpty(fileMap)) {
            return Result.error("请选择文件");
        }

        List<String> fileList = new ArrayList<>();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();
            String fileUrl = ossService.upload(file, OssBucket.UPLOAD);
            fileList.add(fileUrl);
        }

        return Result.OK(fileList);
    }
}
