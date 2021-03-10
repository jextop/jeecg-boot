package com.starter.ali.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: SMS发送记录
 * @Author: jeecg-boot
 * @Date: 2021-03-10
 * @Version: V1.0
 */
@Data
@TableName("mvp_api_call_sms")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "mvp_api_call_sms对象", description = "SMS发送记录")
public class ApiCallSms implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 手机号
     */
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    /**
     * 短信码
     */
    @Excel(name = "短信码", width = 15)
    @ApiModelProperty(value = "短信码")
    private String smsCode;
    /**
     * received
     */
    @Excel(name = "received", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "received")
    private Integer received;
    /**
     * err_code
     */
    @Excel(name = "err_code", width = 15)
    @ApiModelProperty(value = "err_code")
    private String errCode;
    /**
     * err_msg
     */
    @Excel(name = "err_msg", width = 15)
    @ApiModelProperty(value = "err_msg")
    private String errMsg;
    /**
     * request_id
     */
    @Excel(name = "request_id", width = 15)
    @ApiModelProperty(value = "request_id")
    private String requestId;
    /**
     * request_body
     */
    @Excel(name = "request_body", width = 15)
    @ApiModelProperty(value = "request_body")
    private String requestBody;
    /**
     * biz_id
     */
    @Excel(name = "biz_id", width = 15)
    @ApiModelProperty(value = "biz_id")
    private String bizId;
    /**
     * out_id
     */
    @Excel(name = "out_id", width = 15)
    @ApiModelProperty(value = "out_id")
    private String outId;
}
