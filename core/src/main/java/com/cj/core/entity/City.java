package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "city")
public class City {
    /**
     * 市
     */
    @ApiModelProperty(name = "cityId",value = "市",dataType = "java.lang.Integer")
    private Integer cityId;

    /**
     * 父级ID
     */
    @ApiModelProperty(name = "parentId",value = "父级ID",dataType = "java.lang.Integer")
    private Integer parentId;

    /**
     * 层级
     */
    @ApiModelProperty(name = "level",value = "层级",dataType = "java.lang.Byte")
    private Byte level;

    /**
     * 行政代码
     */
    @ApiModelProperty(name = "areaCode",value = "行政代码",dataType = "java.lang.Long")
    private Long areaCode;

    /**
     * 邮政编码
     */
    @ApiModelProperty(name = "zipCode",value = "邮政编码",dataType = "java.lang.Integer")
    private Integer zipCode;

    /**
     * 区号
     */
    @ApiModelProperty(name = "cityCode",value = "区号",dataType = "java.lang.String")
    private String cityCode;

    /**
     * 名称
     */
    @ApiModelProperty(name = "name",value = "名称",dataType = "java.lang.String")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(name = "shortName",value = "简称",dataType = "java.lang.String")
    private String shortName;

    /**
     * 组合名
     */
    @ApiModelProperty(name = "mergerName",value = "组合名",dataType = "java.lang.String")
    private String mergerName;

    /**
     * 拼音
     */
    @ApiModelProperty(name = "pinyin",value = "拼音",dataType = "java.lang.String")
    private String pinyin;

    /**
     * 经度
     */
    @ApiModelProperty(name = "lng",value = "经度",dataType = "java.math.BigDecimal")
    private BigDecimal lng;

    /**
     * 纬度
     */
    @ApiModelProperty(name = "lat",value = "纬度",dataType = "java.math.BigDecimal")
    private BigDecimal lat;
}