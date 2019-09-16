package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/14
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "urine_protein")
public class UrineProtein {
    /**
     * 24小时尿蛋白
     */
    @ApiModelProperty(name = "urineProteinId",value = "24小时尿蛋白",dataType = "java.lang.Long")
    private Long urineProteinId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 0-异常 1-正常
     */
    @ApiModelProperty(name = "abnormal",value = "0-异常 1-正常",dataType = "java.lang.String")
    private String abnormal;

    /**
     * 蛋白质量g/d
     */
    @ApiModelProperty(name = "protein",value = "蛋白质量g/d",dataType = "java.lang.Double")
    private Double protein;

    /**
     * 尿量
     */
    @ApiModelProperty(name = "amount",value = "尿量",dataType = "java.lang.Long")
    private Long amount;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.config.Date")
    private Date createTime;
}