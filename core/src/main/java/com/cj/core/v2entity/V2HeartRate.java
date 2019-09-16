package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-29 16:09:33
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_heart_rate")
public class V2HeartRate implements Serializable {
    /**
     * 心率表主键
     */
    @ApiModelProperty(name = "heartRateId",value = "心率表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long heartRateId;

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
     * 次/每分
     */
    @ApiModelProperty(name = "pulse",value = "次/每分",dataType = "java.lang.Integer")
    private Integer pulse;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.util.Date")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}