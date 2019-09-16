package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/15
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "user_info")
public class UserInfo {
    /**
     * 个人信息
     */
    @ApiModelProperty(name = "userInfoId",value = "个人信息",dataType = "java.lang.Long")
    private Long userInfoId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name;

    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickname",value = "昵称",dataType = "java.lang.String")
    private String nickname;

    /**
     * 年龄
     */
    @ApiModelProperty(name = "age",value = "年龄",dataType = "java.lang.Integer")
    private Integer age;

    /**
     * 性别
     */
    @ApiModelProperty(name = "sex",value = "性别",dataType = "java.lang.String")
    private String sex;

    /**
     * 出身日期
     */
    @ApiModelProperty(name = "birth",value = "出身日期",dataType = "java.config.Date")
    private Date birth;

    /**
     * 血型
     */
    @ApiModelProperty(name = "bloodType",value = "血型",dataType = "java.lang.String")
    private String bloodType;

    /**
     * 体重 kg
     */
    @ApiModelProperty(name = "weight",value = "体重 kg",dataType = "java.lang.Double")
    private Double weight;

    /**
     * 身高 cm
     */
    @ApiModelProperty(name = "height",value = "身高 cm",dataType = "java.lang.Integer")
    private Integer height;

    /**
     * 头像地址
     */
    @ApiModelProperty(name = "headUrl",value = "头像地址",dataType = "java.lang.String")
    private String headUrl;

    /**
     * 身份证号
     */
    @ApiModelProperty(name = "idNumber",value = "身份证号",dataType = "java.lang.String")
    private String idNumber;

    /**
     * 身份证有效期
     */
    @ApiModelProperty(name = "idTerm",value = "身份证有效期",dataType = "java.config.Date")
    private Date idTerm;

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
     * 区/县
     */
    @ApiModelProperty(name = "areaId",value = "区/县",dataType = "java.lang.Long")
    private Long areaId;

    /**
     * 地址
     */
    @ApiModelProperty(name = "address",value = "地址",dataType = "java.lang.String")
    private String address;
}