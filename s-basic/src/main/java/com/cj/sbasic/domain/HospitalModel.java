package com.cj.sbasic.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author： 刘磊
 * @Description: 医院实体类
 * @date： 2019/3/20 14:42
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "医院实体类")
public class HospitalModel implements Serializable{
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
    @ApiModelProperty(name = "province",value = "所在省",dataType = "java.lang.String")
    private String province;

    /**
     * 所在市
     */
    @ApiModelProperty(name = "city",value = "所在市",dataType = "java.lang.String")
    private String city;

    /**
     * 所在区
     */
    @ApiModelProperty(name = "area",value = "所在区",dataType = "java.lang.String")
    private String area;
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
