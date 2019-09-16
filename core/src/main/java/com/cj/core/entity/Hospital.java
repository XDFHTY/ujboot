package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "hospital")
public class Hospital {
    /**
     * 医院信息
     */
    @ApiModelProperty(name = "hospitalId",value = "医院信息",dataType = "java.lang.Long")
    private Long hospitalId;

    /**
     * 医院名字
     */
    @ApiModelProperty(name = "hospitalName",value = "医院名字",dataType = "java.lang.String")
    private String hospitalName;

    /**
     * 所在省
     */
    @ApiModelProperty(name = "provinceId",value = "所在省",dataType = "java.lang.Long")
    private Long provinceId;

    /**
     * 所在市
     */
    @ApiModelProperty(name = "cityId",value = "所在市",dataType = "java.lang.Long")
    private Long cityId;

    /**
     * 所在区
     */
    @ApiModelProperty(name = "areaId",value = "所在区",dataType = "java.lang.Long")
    private Long areaId;

    /**
     * 医院详细地址
     */
    @ApiModelProperty(name = "address",value = "医院详细地址",dataType = "java.lang.String")
    private String address;
}