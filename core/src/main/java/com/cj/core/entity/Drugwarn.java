package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "drugwarn")
public class Drugwarn {
    /**
     * 用药提醒
     */
    @ApiModelProperty(name = "drugwarnId",value = "用药提醒",dataType = "java.lang.Long")
    private Long drugwarnId;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 药品名称、规格
     */
    @ApiModelProperty(name = "drugName",value = "药品名称、规格",dataType = "java.lang.String")
    private String drugName;

    /**
     * 单位计量
     */
    @ApiModelProperty(name = "drugMeasure",value = "单位计量",dataType = "java.lang.String")
    private String drugMeasure;

    /**
     * 服用次数
     */
    @ApiModelProperty(name = "takeNumber",value = "服用次数",dataType = "java.lang.String")
    private String takeNumber;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.config.Date")
    private Date createTime;

    /**
     * 0-已删除，1-正常，默认为1
     */
    @ApiModelProperty(name = "state",value = "0-已删除，1-正常，默认为1",dataType = "java.lang.String")
    private String state;
}