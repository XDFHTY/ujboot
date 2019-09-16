package com.cj.sbasic.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 推送消息的时候，接收POST请求参数的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "msgExportReq")
public class MsgSendReq {
    /**
     * 消息类型
     */
    @ApiModelProperty(name = "msgType",value = "消息类型",dataType = "java.lang.String")
    private String msgType;

    /**
     * 用户类型
     */
    @ApiModelProperty(name = "userType",value = "用户类型",dataType = "java.lang.String")
    private String userType;

    /**
     * 最小年龄
     */
    @ApiModelProperty(name = "minAge",value = "最小年龄",dataType = "java.lang.String")
    private String minAge;

    /**
     * 最大年龄
     */
    @ApiModelProperty(name = "maxAge",value = "最大年龄",dataType = "java.lang.String")
    private String maxAge;

    /**
     * 部门
     */
    @ApiModelProperty(name = "department",value = "部门",dataType = "java.lang.String")
    private String department;

    /**
     * 职称
     */
    @ApiModelProperty(name = "title",value = "职称",dataType = "java.lang.String")
    private String title;

    /**
     * 保留天数
     */
    @ApiModelProperty(name = "days",value = "保留天数",dataType = "java.lang.String")
    private String days;

    /**
     * 消息标题
     */
    @ApiModelProperty(name = "msgTitle",value = "消息标题",dataType = "java.lang.String")
    private String msgTitle;

    /**
     * 消息内容
     */
    @ApiModelProperty(name = "msgContent",value = "消息内容",dataType = "java.lang.String")
    private String msgContent;
}
