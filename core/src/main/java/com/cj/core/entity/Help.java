package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "help")
public class Help {
    /**
     * 使用帮助
     */
    @ApiModelProperty(name = "helpId",value = "使用帮助",dataType = "java.lang.Long")
    private Long helpId;

    /**
     * 问题
     */
    @ApiModelProperty(name = "problem",value = "问题",dataType = "java.lang.String")
    private String problem;

    /**
     * 端口类型 1-个人 2-医生
     */
    @ApiModelProperty(name = "type",value = "端口类型 1-个人 2-医生",dataType = "java.lang.String")
    private String type;

    /**
     * 显示状态 0-隐藏 1-显示
     */
    @ApiModelProperty(name = "isDisplay",value = "显示状态 0-隐藏 1-显示",dataType = "java.lang.String")
    private String isDisplay;

    /**
     * 答案
     */
    @ApiModelProperty(name = "answer",value = "答案",dataType = "java.lang.String")
    private String answer;
}