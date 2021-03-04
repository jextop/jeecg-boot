package com.starter.ali.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author dingxl
 * @date 3/4/2021 12:01 PM
 */
@Service
@Slf4j
public class SmsService {
    SmsConfig smsConfig;
    IAcsClient client;

    @Autowired
    public SmsService(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;

        DefaultProfile profile = DefaultProfile.getProfile(
                smsConfig.getRegion(), smsConfig.getAccessKey(), smsConfig.getSecretKey()
        );
        this.client = new DefaultAcsClient(profile);
    }

    public SmsModel sendSms(String phone, SmsTemplate template, String code) throws ClientException {
        return sendSms(phone, template, new JSONObject() {{
            put("code", code);
        }});
    }

    public SmsModel sendSms(String phone, SmsTemplate template, JSONObject params) throws ClientException {
        return sendRequest(new CommonRequest() {{
            setSysAction("SendSms");
            putQueryParameter("SignName", smsConfig.getSignName());
            putQueryParameter("PhoneNumbers", phone);
            putQueryParameter("TemplateCode", template.getCode());

            if (MapUtils.isNotEmpty(params)) {
                putQueryParameter("TemplateParam", JSON.toJSONString(params));
            }
        }});
    }

    public SmsModel sendBatch(SmsTemplate template, JSONArray params) throws ClientException {
        JSONArray signArr = new JSONArray(), phoneArr = new JSONArray(), paramArr = new JSONArray();

        for (int i = 0; i < params.size(); i++) {
            JSONObject param = params.getJSONObject(i);
            String phone = param.getString("phone");
            if (StringUtils.isEmpty(phone)) {
                continue;
            }

            signArr.add(smsConfig.getSignName());
            phoneArr.add(phone);
            paramArr.add(param);
        }

        return sendRequest(new CommonRequest() {{
            setSysAction("SendBatchSms");
            putQueryParameter("SignNameJson", signArr.toJSONString());
            putQueryParameter("PhoneNumberJson", phoneArr.toJSONString());
            putQueryParameter("TemplateCode", template.getCode());
            putQueryParameter("TemplateParamJson", paramArr.toJSONString());
        }});
    }

    public SmsModel querySms(String phone, LocalDate date, int pageNo, int pageSize) throws ClientException {
        return sendRequest(new CommonRequest() {{
            setSysAction("QuerySendDetails");
            putQueryParameter("PhoneNumber", phone);
            putQueryParameter("SendDate", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            putQueryParameter("PageSize", String.valueOf(pageSize));
            putQueryParameter("CurrentPage", String.valueOf(pageNo));
        }});
    }

    protected SmsModel sendRequest(CommonRequest request) throws ClientException {
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId", smsConfig.getRegion());

        CommonResponse response = client.getCommonResponse(request);
        String retStr = response.getData();

        log.info(retStr);
        return JSON.parseObject(retStr, SmsModel.class);
    }
}
