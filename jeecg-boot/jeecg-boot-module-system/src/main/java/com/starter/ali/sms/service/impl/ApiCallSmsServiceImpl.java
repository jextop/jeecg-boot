package com.starter.ali.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.ali.sms.entity.ApiCallSms;
import com.starter.ali.sms.mapper.ApiCallSmsMapper;
import com.starter.ali.sms.service.IApiCallSmsService;
import org.springframework.stereotype.Service;

/**
 * @Description: SMS发送记录
 * @Author: jeecg-boot
 * @Date: 2021-03-10
 * @Version: V1.0
 */
@Service
public class ApiCallSmsServiceImpl extends ServiceImpl<ApiCallSmsMapper, ApiCallSms> implements IApiCallSmsService {

}
