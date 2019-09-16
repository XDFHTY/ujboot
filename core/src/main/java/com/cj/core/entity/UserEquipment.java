package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/21
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户绑定设备记录表")
public class UserEquipment {
    /**
     * 用户绑定设备记录表
     */
    @ApiModelProperty(name = "userBindEquipmentId",value = "用户绑定设备记录表ID",dataType = "java.lang.Long")
    private Long userBindEquipmentId;

    /**
     * 用户表id
     */
    @ApiModelProperty(name = "userId",value = "用户表id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 设备蓝牙类型
     */
    @ApiModelProperty(name = "bluetooth",value = "设备蓝牙类型,蓝牙4.0",dataType = "java.lang.String")
    private String bluetooth;

    /**
     * 设备蓝牙地址
     */
    @ApiModelProperty(name = "bluetoothMac",value = "设备蓝牙地址",dataType = "java.lang.String")
    private String bluetoothMac;

    /**
     * 设备SN
     */
    @ApiModelProperty(name = "equipmentSn",value = "设备SN",dataType = "java.lang.String")
    private String equipmentSn;

    /**
     * 设备类别
     */
    @ApiModelProperty(name = "equipmentType",value = "设备类别，半自动尿液分析仪，智能手表",dataType = "java.lang.String")
    private String equipmentType;

    /**
     * 设备型号
     */
    @ApiModelProperty(name = "equipmentModel",value = "设备型号",dataType = "java.lang.String")
    private String equipmentModel;

    /**
     * 绑定时间
     */
    @ApiModelProperty(name = "startTime",value = "绑定时间",dataType = "java.config.Date")
    private Date startTime;

    /**
     * 解绑时间
     */
    @ApiModelProperty(name = "endTime",value = "解绑时间",dataType = "java.config.Date")
    private Date endTime;

    @ApiModelProperty(name = "endTime",value = "解绑时间",dataType = "java.config.Date")
    private Date useTime;



}