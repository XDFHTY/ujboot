package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/15
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "fall_ill_img")
public class FallIllImg {
    /**
     * 检查报告表
     */
    @ApiModelProperty(name = "fallIllImgId",value = "检查报告表",dataType = "java.lang.Long")
    private Long fallIllImgId;

    /**
     * 生病信息id
     */
    @ApiModelProperty(name = "fallIllId",value = "生病信息id",dataType = "java.lang.Long")
    private Long fallIllId;

    /**
     * 检查报告路径
     */
    @ApiModelProperty(name = "routeUrl",value = "检查报告路径",dataType = "java.lang.String")
    private String routeUrl;
}