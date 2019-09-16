package com.cj.sconsult.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 医生信息实体类
 * Created by JuLei on 2019/6/28.
 */
@Data
public class RespDoctorInfo {
    @ApiModelProperty(name = "userId",value = "医生id",dataType = "java.lang.Long")
    private Long userId;

    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name;


    @ApiModelProperty(name = "sex",value = "性别 男 女",dataType = "java.lang.String")
    private String sex;

    @ApiModelProperty(name = "roleId",value = "角色id",dataType = "java.lang.Long")
    private Long roleId;

    @ApiModelProperty(name = "roleName",value = "角色",dataType = "java.lang.String")
    private String roleName;

    @ApiModelProperty(name = "doctorBirth",value = "生日",dataType = "java.util.Date")
    private Date doctorBirth;

    @ApiModelProperty(name = "title",value = "职称",dataType = "java.lang.String")
    private String title;

    @ApiModelProperty(name = "hospitalName",value = "医院",dataType = "java.lang.String")
    private String hospitalName;

    @ApiModelProperty(name = "phone", value = "电话号码", dataType = "java.lang.String")
    private String phone;

    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province;

    @ApiModelProperty(name = "city",value = "市",dataType = "java.lang.String")
    private String city;

    @ApiModelProperty(name = "area",value = "区",dataType = "java.lang.String")
    private String area;

    @ApiModelProperty(name = "briefIntroduction",value = "简介",dataType = "java.lang.String")
    private String briefIntroduction;

    @ApiModelProperty(name = "advantages",value = "擅长疾病",dataType = "java.lang.String")
    private String advantages;

    @ApiModelProperty(name = "headUrl",value = "头像地址",dataType = "java.lang.String")
    private String headUrl;

    @ApiModelProperty(name = "departments", value = "科室", dataType = "java.lang.String")
    private String departments;

    @ApiModelProperty(name = "company",value = "单位",dataType = "String")
    private String company;
}
