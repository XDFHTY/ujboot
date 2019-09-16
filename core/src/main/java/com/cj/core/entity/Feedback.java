package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/26
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "feedback")
public class Feedback {
    /**
     * 意见反馈
     */
    @ApiModelProperty(name = "feedbackId",value = "意见反馈",dataType = "java.lang.Long")
    private Long feedbackId;

    /**
     * 提交人id
     */
    @ApiModelProperty(name = "userId",value = "提交人id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 反馈内容
     */
    @ApiModelProperty(name = "content",value = "反馈内容",dataType = "java.lang.String")
    private String content;

    /**
     * 0-设备 1-医生 2-平台
     */
    @ApiModelProperty(name = "object",value = "0-设备 1-医生 2-平台",dataType = "java.lang.String")
    private String object;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email",value = "邮箱",dataType = "java.lang.String")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(name = "phone",value = "电话",dataType = "java.lang.String")
    private String phone;

    /**
     * 反馈提交时间
     */
    @ApiModelProperty(name = "time",value = "反馈提交时间",dataType = "java.config.Date")
    private Date time;

    /**
     * 0-未处理，1-已处理
     */
    @ApiModelProperty(name = "state",value = "0-未处理，1-已处理",dataType = "java.lang.String")
    private String state;
}