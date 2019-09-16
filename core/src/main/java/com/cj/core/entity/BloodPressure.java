package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "blood_pressure")
public class BloodPressure {
    /**
     * 血压信息表
     */
    @ApiModelProperty(name = "bloodPressureId",value = "血压信息表",dataType = "java.lang.Long")
    private Long bloodPressureId;

    /**
     * 用户
     */
    @ApiModelProperty(name = "userId",value = "用户",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 设备蓝牙地址
     */
    @ApiModelProperty(name = "bluetoothMac",value = "设备蓝牙地址",dataType = "java.lang.String")
    private String bluetoothMac;

    /**
     * 0-异常 1-正常
     */
    @ApiModelProperty(name = "abnormal",value = "0-异常 1-正常",dataType = "java.lang.String")
    private String abnormal;

    /**
     * 收缩压
     */
    @ApiModelProperty(name = "systolicPressure",value = "收缩压",dataType = "java.lang.Integer")
    private Integer systolicPressure;

    /**
     * 舒张压
     */
    @ApiModelProperty(name = "diastolicPressure",value = "舒张压",dataType = "java.lang.Integer")
    private Integer diastolicPressure;

    /**
     * 次/每分
     */
    @ApiModelProperty(name = "pulse",value = "次/每分",dataType = "java.lang.Integer")
    private Integer pulse;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.config.Date")
    private Date createTime;
}