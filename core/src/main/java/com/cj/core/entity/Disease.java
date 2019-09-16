package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/18
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "disease")
public class Disease {
    /**
     * 疾病类型
     */
    @ApiModelProperty(name = "diseaseId",value = "疾病类型",dataType = "java.lang.Long")
    private Long diseaseId;

    /**
     * 科室id
     */
    @ApiModelProperty(name = "departmentId",value = "科室id",dataType = "java.lang.Long")
    private Long departmentId;

    /**
     * 病名
     */
    @ApiModelProperty(name = "diseaseName",value = "病名",dataType = "java.lang.String")
    private String diseaseName;

    /**
     * 0-隐藏 1-显示
     */
    @ApiModelProperty(name = "isDisplay",value = "0-隐藏 1-显示",dataType = "java.lang.String")
    private String isDisplay;
}