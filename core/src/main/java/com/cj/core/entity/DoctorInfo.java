package com.cj.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-28 10:36:57
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "doctor_info")
public class DoctorInfo {
    /**
     * 医生基本信息
     */
    @ApiModelProperty(name = "doctorInfoId",value = "医生基本信息",dataType = "java.lang.Long")
    private Long doctorInfoId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 医生可以存医院名称，干部存单位名称
     */
    @ApiModelProperty(name = "company",value = "医生可以存医院名称，干部存单位名称",dataType = "java.lang.String")
    private String company;

    /**
     * 医院表id
     */
    @ApiModelProperty(name = "hospitalId",value = "医院表id",dataType = "java.lang.Long")
    private Long hospitalId;

    /**
     * 科室id
     */
    @ApiModelProperty(name = "departmentsId",value = "科室id",dataType = "java.lang.Long")
    private Long departmentsId;

    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name;

    /**
     * 性别 男 女
     */
    @ApiModelProperty(name = "sex",value = "性别 男 女",dataType = "java.lang.String")
    private String sex;

    /**
     * 生日
     */
    @ApiModelProperty(name = "doctorBirth",value = "生日",dataType = "java.util.Date")
    private Date doctorBirth;

    /**
     * 年龄
     */
    @ApiModelProperty(name = "age",value = "年龄",dataType = "java.lang.Integer")
    private Integer age;

    /**
     * 职称
     */
    @ApiModelProperty(name = "title",value = "职称",dataType = "java.lang.String")
    private String title;

    /**
     * 电话号码
     */
    @ApiModelProperty(name = "phone",value = "电话号码",dataType = "java.lang.String")
    private String phone;

    /**
     * 地址
     */
    @ApiModelProperty(name = "address",value = "地址",dataType = "java.lang.String")
    private String address;

    /**
     * 简介
     */
    @ApiModelProperty(name = "briefIntroduction",value = "简介",dataType = "java.lang.String")
    private String briefIntroduction;

    /**
     * 擅长疾病
     */
    @ApiModelProperty(name = "advantages",value = "擅长疾病",dataType = "java.lang.String")
    private String advantages;

    /**
     * 头像地址
     */
    @ApiModelProperty(name = "headUrl",value = "头像地址",dataType = "java.lang.String")
    private String headUrl;

    /**
     * 省
     */
    @ApiModelProperty(name = "provinceId",value = "省",dataType = "java.lang.Long")
    private Long provinceId;

    /**
     * 市
     */
    @ApiModelProperty(name = "cityId",value = "市",dataType = "java.lang.Long")
    private Long cityId;

    /**
     * 区
     */
    @ApiModelProperty(name = "areaId",value = "区",dataType = "java.lang.Long")
    private Long areaId;

    /**
     * 身份证号
     */
    @ApiModelProperty(name = "idNumber",value = "身份证号",dataType = "java.lang.String")
    private String idNumber;

    /**
     * 身份证有效期限
     */
    @ApiModelProperty(name = "idTerm",value = "身份证有效期限",dataType = "java.lang.String")
    private String idTerm;

    /**
     * 身份证正面
     */
    @ApiModelProperty(name = "idJustUrl",value = "身份证正面",dataType = "java.lang.String")
    private String idJustUrl;

    /**
     * 身份证反面
     */
    @ApiModelProperty(name = "idBackUrl",value = "身份证反面",dataType = "java.lang.String")
    private String idBackUrl;

    /**
     * 注册时间
     */
    @ApiModelProperty(name = "registerTime",value = "注册时间",dataType = "java.util.Date")
    private Date registerTime;

    private static final long serialVersionUID = 1L;
}