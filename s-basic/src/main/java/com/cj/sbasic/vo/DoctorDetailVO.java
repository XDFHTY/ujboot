package com.cj.sbasic.vo;

import com.cj.core.entity.DoctorCertificate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DoctorDetailVO")
public class DoctorDetailVO {
    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name = "";

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号",dataType = "java.lang.String")
    private String phone = "";

    /**
     * 地址
     */
    @ApiModelProperty(name = "address",value = "地址",dataType = "java.lang.String")
    private String address = "";

    /**
     * 所属医院
     */
    @ApiModelProperty(name = "hospital",value = "所属医院",dataType = "java.lang.String")
    private String hospital = "";

    /**
     * 科室
     */
    @ApiModelProperty(name = "department",value = "科室",dataType = "java.lang.String")
    private String department = "";

    /**
     * 职称
     */
    @ApiModelProperty(name = "title",value = "职称",dataType = "java.lang.String")
    private String title = "";

    /**
     * 身份证正面
     */
    @ApiModelProperty(name = "idJustUrl",value = "身份证正面",dataType = "java.lang.String")
    private String idJustUrl = "";

    /**
     * 身份证背面
     */
    @ApiModelProperty(name = "idBackUrl",value = "身份证背面",dataType = "java.lang.String")
    private String idBackUrl = "";

    /**
     * 医师资格证、执业证
     */
    @ApiModelProperty(name = "certificate",value = "医师资格证、执业证",dataType = "com.cj.sbasic.config.DoctorCertificate")
    private List<DoctorCertificate> certificate = new ArrayList<>();
}
