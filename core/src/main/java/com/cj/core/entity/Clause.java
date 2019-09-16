package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "clause")
public class Clause {
    /**
     * 隐私与服务条款
     */
    @ApiModelProperty(name = "clauseId",value = "隐私与服务条款",dataType = "java.lang.Long")
    private Long clauseId;

    /**
     * 标题
     */
    @ApiModelProperty(name = "subject",value = "标题",dataType = "java.lang.String")
    private String subject;

    /**
     * 显示状态 0-隐藏 1-显示
     */
    @ApiModelProperty(name = "isDisplay",value = "显示状态 0-隐藏 1-显示",dataType = "java.lang.String")
    private String isDisplay;

    /**
     * 内容
     */
    @ApiModelProperty(name = "content",value = "内容",dataType = "java.lang.String")
    private String content;
}