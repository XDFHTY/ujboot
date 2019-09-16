package com.cj.sbasic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FeedbackVO")
public class FeedbackVO {
    /**
     * feedbackId
     */
    @ApiModelProperty(name = "feedbackId",value = "feedbackId",dataType = "java.lang.Long")
    private Long feedbackId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username",value = "用户名",dataType = "java.lang.String")
    private String username = "";

    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name = "";

    /**
     * 用户类型 1-用户 2-家庭医生 3-肾病医生
     */
    @ApiModelProperty(name = "userType",value = "用户类型 1-用户 2-家庭医生 3-肾病医生",dataType = "java.lang.String")
    private String userType = "";

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email",value = "邮箱",dataType = "java.lang.String")
    private String email = "";

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号",dataType = "java.lang.String")
    private String phone = "";

    /**
     * 申请时间
     */
    @ApiModelProperty(name = "time",value = "申请时间",dataType = "java.config.Date")
    private Date time;

    /**
     * 投诉类别 0-设备 1-医生 2-平台
     */
    @ApiModelProperty(name = "feedbackType",value = "投诉类别 0-设备 1-医生 2-平台",dataType = "java.lang.String")
    private String feedbackType = "";

    /**
     * 投诉说明(内容)
     */
    @ApiModelProperty(name = "content",value = "投诉说明(内容)",dataType = "java.lang.String")
    private String content = "";

    /**
     * 投诉配图(最多四张)
     */
    @ApiModelProperty(name = "imageList",value = "投诉配图(最多四张)",dataType = "java.config.List")
    private List<String> imageList = new ArrayList<>();

    /**
     * 状态 0-未处理，1-已处理
     */
    @ApiModelProperty(name = "state",value = "状态 0-未处理，1-已处理",dataType = "java.lang.String")
    private String state = "";
}
