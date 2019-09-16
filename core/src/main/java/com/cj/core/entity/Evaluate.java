package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "evaluate")
public class Evaluate {
    /**
     * 评价记录表
     */
    @ApiModelProperty(name = "evaluateId",value = "评价记录表",dataType = "java.lang.Long")
    private Long evaluateId;

    /**
     * 医生ID
     */
    @ApiModelProperty(name = "docterId",value = "医生ID",dataType = "java.lang.Long")
    private Long docterId;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 分数
     */
    @ApiModelProperty(name = "score",value = "分数",dataType = "java.lang.Double")
    private Double score;
}