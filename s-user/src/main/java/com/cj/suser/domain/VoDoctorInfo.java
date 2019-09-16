package com.cj.suser.domain;

import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.DoctorInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "医生详情对象")
public class VoDoctorInfo extends DoctorInfo {




    @ApiModelProperty(name = "userType",value = "用户类型，0-游客、1-用户 、2-家庭医生、3-专科医生 、4-营养师、5-乡干部、6-卫健委干部、7-离退休干部、8-护士、9-健康管理师",dataType = "String")
    private String userType;

    @ApiModelProperty(name = "hospitalName",value = "医院名称",dataType = "String")
    private String hospitalName;

    @ApiModelProperty(name = "departmentsName",value = "科室名称",dataType = "String")
    private String departmentsName;

    @ApiModelProperty(name = "state",value = "账号状态",dataType = "String")
    private String state;

    @ApiModelProperty(name = "doctorCertificates",value = "医生证书信息",dataType = "List")
    private List<DoctorCertificate> doctorCertificates;

    @ApiModelProperty(name = "region",value = "地区")
    private String region;
}
