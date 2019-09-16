package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/10
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "doctor_certificate")
public class DoctorCertificate {
    /**
     * 医生证书信息表
     */
    @ApiModelProperty(name = "doctorCertificateId",value = "医生证书信息表",dataType = "java.lang.Long")
    private Long doctorCertificateId;

    /**
     * 医生表id
     */
    @ApiModelProperty(name = "doctorId",value = "医生表id",dataType = "java.lang.Long")
    private Long doctorId;

    /**
     * 证书类型 1-医师资格证 2-从业证
     */
    @ApiModelProperty(name = "certificateType",value = "证书类型 1-医师资格证 2-从业证",dataType = "java.lang.String")
    private String certificateType;

    /**
     * 证书编号
     */
    @ApiModelProperty(name = "certificateNum",value = "证书编号",dataType = "java.lang.String")
    private String certificateNum;

    /**
     * 证书路径
     */
    @ApiModelProperty(name = "certificateUrl",value = "证书路径",dataType = "java.lang.String")
    private String certificateUrl;
}