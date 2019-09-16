package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "drugwarn_time")
public class DrugwarnTime {
    /**
     * 用药提醒时间表
     */
    @ApiModelProperty(name = "drugwarnTimeId",value = "用药提醒时间表",dataType = "java.lang.Long")
    private Long drugwarnTimeId;

    /**
     * 用药表ID
     */
    @ApiModelProperty(name = "drugwarnId",value = "用药表ID",dataType = "java.lang.Long")
    private Long drugwarnId;

    /**
     * 用药提醒时间
     */
    @ApiModelProperty(name = "drugTime",value = "用药提醒时间",dataType = "java.config.Date")
    private Date drugTime;

    /**
     * 0-关闭提醒，1-正常提醒
     */
    @ApiModelProperty(name = "isClose",value = "0-关闭提醒，1-正常提醒",dataType = "java.lang.String")
    private String isClose;
}