package com.cj.sbasic.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DoctorCheckVO")
public class DoctorCheckVO {
    /**
     * 医生id
     */
    @ApiModelProperty(name = "doctorInfoId",value = "医生id",dataType = "java.lang.Long")
    private Long doctorInfoId;
    /**
     * 姓名
     */
    @ApiModelProperty(name = "userName",value = "姓名",dataType = "java.lang.String")
    private String userName = "";

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phoneNumber",value = "手机号",dataType = "java.lang.String")
    private String phoneNumber = "";

    /**
     * 身份 1-用户，2-家庭医生，3-肾病医生
     */
    @ApiModelProperty(name = "userType",value = "身份 1-用户，2-家庭医生，3-肾病医生",dataType = "java.lang.String")
    private String userType = "";

    /**
     * 省
     */
    @ApiModelProperty(name = "provinceId",value = "省",dataType = "java.lang.String")
    private String province = "";

    /**
     * 市
     */
    @ApiModelProperty(name = "cityId",value = "市",dataType = "java.lang.String")
    private String city = "";

    /**
     * 县
     */
    @ApiModelProperty(name = "areaId",value = "县",dataType = "java.lang.String")
    private String area = "";

    /**
     * 所属医院
     */
    @ApiModelProperty(name = "hospitalName",value = "所属医院",dataType = "java.lang.String")
    private String hospitalName = "";

    /**
     * 科室
     */
    @ApiModelProperty(name = "departmentName",value = "科室",dataType = "java.lang.String")
    private String departmentName = "";

    /**
     * 职称
     */
    @ApiModelProperty(name = "title",value = "职称",dataType = "java.lang.String")
    private String title = "";

    /**
     * 申请时间
     */
    @ApiModelProperty(name = "updateTime",value = "申请时间",dataType = "java.config.Date")
    private Date updateTime;

    /**
     * 审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过
     */
    @ApiModelProperty(name = "state",value = "审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过",dataType = "java.lang.String")
    private String state = "";
}
