package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "system_msg")
public class SystemMsg {
    /**
     * 系统消息ID
     */
    @ApiModelProperty(name = "systemMsgId",value = "系统消息ID",dataType = "java.lang.Long")
    private Long systemMsgId;

    /**
     * 标题
     */
    @ApiModelProperty(name = "systemMsgTatil",value = "标题",dataType = "java.lang.String")
    private String systemMsgTatil;

    /**
     * 内容
     */
    @ApiModelProperty(name = "systemMsg",value = "内容",dataType = "java.lang.String")
    private String systemMsg;

    /**
     * 推送时间
     */
    @ApiModelProperty(name = "sendTime",value = "推送时间",dataType = "java.config.Date")
    private Date sendTime;

    /**
     * 操作员ID
     */
    @ApiModelProperty(name = "operationId",value = "操作员ID",dataType = "java.lang.Long")
    private Long operationId;
}