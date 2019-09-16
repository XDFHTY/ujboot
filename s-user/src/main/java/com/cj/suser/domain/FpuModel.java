package com.cj.suser.domain;

import com.cj.core.entity.UserEquipment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 个人用户实体类
 * @date： 2019/6/28 13:48
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "个人用户实体类")
//@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" })
public class FpuModel {


    /**
     * 用户表
     */
    @ApiModelProperty(name = "userId",value = "用户表ID",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName",value = "用户名",dataType = "java.lang.String")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(name = "userPass",value = "密码",dataType = "java.lang.String")
    private String userPass;

    /**
     * QQ号
     */
    @ApiModelProperty(name = "qqNumber",value = "QQ号",dataType = "java.lang.String")
    private String qqNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "eMail",value = "邮箱",dataType = "java.lang.String")
    private String eMail;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phoneNumber",value = "手机号",dataType = "java.lang.String")
    private String phoneNumber;

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
    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(name = "cityId",value = "市",dataType = "java.lang.String")
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty(name = "area",value = "区/县",dataType = "java.lang.String")
    private String area;

    /**
     * 地址
     */
    @ApiModelProperty(name = "address",value = "地址",dataType = "java.lang.String")
    private String address;

    /**
     * 绑定设备记录
     */
    @ApiModelProperty(name = "ubeList",value = "绑定设备记录")
    private List<UserEquipment> ubeList;

    /**
     * 疾病记录
     */
    @ApiModelProperty(name = "fimList",value = "疾病记录")
    private List<FIModel> fimList;

    /**
     * 绑定医生记录
     */
    @ApiModelProperty(name = "brmList",value = "绑定医生记录")
    private List<BRModel> brmList;

    /**
     * 服务包记录
     */
    @ApiModelProperty(name = "order",value = "服务包记录")
    private List<OrderModel> order;
}
