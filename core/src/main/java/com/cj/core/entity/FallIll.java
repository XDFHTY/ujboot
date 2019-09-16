package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/16
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "fall_ill")
public class FallIll {
    /**
     * 生病信息记录表ID
     */
    @ApiModelProperty(name = "fallIllId",value = "生病信息记录表ID",dataType = "java.lang.Long")
    private Long fallIllId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 医院id
     */
    @ApiModelProperty(name = "hospitalId",value = "医院id",dataType = "java.lang.Long")
    private Long hospitalId;

    /**
     * 疾病id
     */
    @ApiModelProperty(name = "diseaseId",value = "疾病id",dataType = "java.lang.Long")
    private Long diseaseId;

    /**
     * 高血压0-否 1-是
     */
    @ApiModelProperty(name = "hypertension",value = "高血压0-否 1-是",dataType = "java.lang.String")
    private String hypertension;

    /**
     * 过敏史0-无 1-有
     */
    @ApiModelProperty(name = "allergy",value = "过敏史0-无 1-有",dataType = "java.lang.String")
    private String allergy;

    /**
     * 慢性病类型
     */
    @ApiModelProperty(name = "chronic",value = "慢性病类型",dataType = "java.lang.String")
    private String chronic;

    /**
     * 肾病类型
     */
    @ApiModelProperty(name = "nephropathyType",value = "肾病类型",dataType = "java.lang.String")
    private String nephropathyType;

    /**
     * 病情描述
     */
    @ApiModelProperty(name = "fallMsg",value = "病情描述",dataType = "java.lang.String")
    private String fallMsg;

    /**
     * 检查时间
     */
    @ApiModelProperty(name = "checkTime",value = "检查时间",dataType = "java.config.Date")
    private Date checkTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "addTime",value = "创建时间",dataType = "java.config.Date")
    private Date addTime;

    /**
     * 状态，0-已删除，1-正常，默认为1
     */
    @ApiModelProperty(name = "state",value = "状态，0-已删除，1-正常，默认为1",dataType = "java.lang.String")
    private String state;
}