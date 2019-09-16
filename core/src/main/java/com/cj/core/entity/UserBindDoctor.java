package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "user_bind_doctor")
public class UserBindDoctor {
    /**
     * 用户绑定医生表
     */
    @ApiModelProperty(name = "userBindDoctorId",value = "用户绑定医生表",dataType = "java.lang.Long")
    private Long userBindDoctorId = 0l;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId = 0l;

    /**
     * 绑定的医生id
     */
    @ApiModelProperty(name = "doctorId",value = "绑定的医生id",dataType = "java.lang.Long")
    private Long doctorId = 0l;

    /**
     * 医生分类 1-家庭医生 2-肾病专家
     */
    @ApiModelProperty(name = "doctorType",value = "医生分类 1-家庭医生 2-肾病专家",dataType = "java.lang.String")
    private String doctorType = "";

    /**
     * 绑定状态 0-解绑 1-绑定
     */
    @ApiModelProperty(name = "isBind",value = "绑定状态 0-解绑 1-绑定",dataType = "java.lang.String")
    private String isBind = "";

    /**
     * 操作时间
     */
    @ApiModelProperty(name = "operationTime",value = "操作时间",dataType = "java.config.Date")
    private Date operationTime;
}