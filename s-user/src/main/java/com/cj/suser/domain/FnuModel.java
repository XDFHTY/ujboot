package com.cj.suser.domain;

import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.DoctorInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 医生信息实体类
 * @date： 2019/6/29 10:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "医生信息实体类")
//@JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer" })
public class FnuModel extends DoctorInfo {


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
     * 医院
     */
    @ApiModelProperty(name = "hospital",value = "医院",dataType = "java.lang.String")
    private String hospital;

    /**
     * 科室
     */
    @ApiModelProperty(name = "departments",value = "科室",dataType = "java.lang.String")
    private String departments;

    /**
     * 价格
     */
    @ApiModelProperty(name = "price",value = "价格",dataType = "java.lang.String")
    private String price;

    /**
     * 商品id
     */
    @ApiModelProperty(name = "goodId",value = "商品id",dataType = "java.lang.Long")
    private Long goodId;
    /**
     * 医生证书信息
     */
    @ApiModelProperty(name = "dcList",value = "医生证书信息",dataType = "List")
    private List<DoctorCertificate> dcList;
}
