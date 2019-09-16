package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 身份证识别后返回参数
 * Created by XD on 2019/3/9.
 */
@Data
@ApiModel(value = "RespIDCard 身份证识别后返回参数")
public class RespIDCard {


    @ApiModelProperty(name = "name",value = "姓名",dataType = "String")
    private String name;
    @ApiModelProperty(name = "nation",value = "民族",dataType = "String")
    private String nation;
    @ApiModelProperty(name = "address",value = "住址",dataType = "String")
    private String address;
    @ApiModelProperty(name = "idNumber",value = "公民身份号码",dataType = "String")
    private String idNumber;
    @ApiModelProperty(name = "birth",value = "出生",dataType = "String")
    private String birth;
    @ApiModelProperty(name = "sex",value = "性别",dataType = "String")
    private String sex;
}
