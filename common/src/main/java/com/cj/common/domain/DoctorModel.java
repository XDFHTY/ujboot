package com.cj.common.domain;

import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.DoctorInfo;
import com.cj.core.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 医生实体类
 * @date： 2019/3/8 14:57
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "医生实体类")
public class DoctorModel implements Serializable{
    /**
     * 账号信息
     */
    @ApiModelProperty(name = "user",value = "账号信息")
    private User user;
    /**
     * 用户表id
     */
    @ApiModelProperty(name = "userId",value = "用户表ID",dataType = "java.lang.Long")
    private Long userId;
    /**
     * 绑定状态
     */
    @ApiModelProperty(name = "isBind",value = "绑定状态",dataType = "java.lang.Boolean")
    private Boolean isBind;
    /**
     * 省
     */
    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(name = "city",value = "市",dataType = "java.lang.String")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(name = "area",value = "区",dataType = "java.lang.String")
    private String area;
    /**
     * 医生信息
     */
    @ApiModelProperty(name = "doctorInfo",value = "医生信息")
    private DoctorInfo doctorInfo;
    /**
     * 咨询量
     */
    @ApiModelProperty(name = "inquiry",value = "质询量",dataType = "Integer")
    private Integer inquiry;
    /**
     * 评分
     */
    @ApiModelProperty(name = "score",value = "评分",dataType = "Double")
    private Double score;
    /**
     * 科室名字
     */
    @ApiModelProperty(name = "departmentName",value = "科室名字",dataType = "String")
    private String departmentName;
    /**
     * 医院名字
     */
    @ApiModelProperty(name = "hospitalName",value = "医院名字",dataType = "String")
    private String hospitalName;
    /**
     * 医生证书
     */
    @ApiModelProperty(name = "certificateInfoList",value = "医生证书")
    private List<DoctorCertificate> doctorCertificateList;
    /**
     * 在线状态
     */
    @ApiModelProperty(name = "boostate",value = "在线状态 true-在线 false-离线")
    private boolean boostate;
}
