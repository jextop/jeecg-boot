package com.starter.ali.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.ali.sms.entity.ApiCallSms;
import com.starter.ali.sms.service.IApiCallSmsService;
import com.starter.common.FieldName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author dingxl
 * @date 3/11/2021 10:33 AM
 */
@Component
@Slf4j
public class SmsJob {
    IApiCallSmsService smsService;

    @Autowired
    public SmsJob(IApiCallSmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * 每天凌晨4点定时运行一次，清理不再需要的数据
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void task() {
        log.info("清理短信发送记录");
        QueryWrapper<ApiCallSms> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt(FieldName.CREATE_TIME, LocalDate.now());
        smsService.remove(queryWrapper);
    }
}
