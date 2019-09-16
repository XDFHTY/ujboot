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
@ApiModel(value = "department")
public class Department {
    /**
     * 科室id
     */
    @ApiModelProperty(name = "departmentId",value = "科室id",dataType = "java.lang.Long")
    private Long departmentId;

    /**
     * 科室名字
     */
    @ApiModelProperty(name = "departmentName",value = "科室名字",dataType = "java.lang.String")
    private String departmentName;

    /**
     * 0-隐藏 1-显示 
     */
    @ApiModelProperty(name = "isDisplay",value = "0-隐藏 1-显示 ",dataType = "java.lang.String")
    private String isDisplay;
}