package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
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
@ApiModel(value = "operate_log")
public class OperateLog implements Serializable {
    /**
     * 操作日志表
     */
    @ApiModelProperty(name = "operateLogId",value = "操作日志表",dataType = "java.lang.Long")
    private Long operateLogId;

    /**
     * 操作模块
     */
    @ApiModelProperty(name = "logName",value = "操作模块",dataType = "java.lang.String")
    private String logName;

    /**
     * 内容
     */
    @ApiModelProperty(name = "logValue",value = "内容",dataType = "java.lang.String")
    private String logValue;

    /**
     * 操作结果
     */
    @ApiModelProperty(name = "logState",value = "操作结果",dataType = "java.lang.String")
    private String logState;

    /**
     * 操作人员ID
     */
    @ApiModelProperty(name = "operateId",value = "操作人员ID",dataType = "java.lang.Long")
    private Long operateId;

    /**
     * 操作时间
     */
    @ApiModelProperty(name = "operateTime",value = "操作时间",dataType = "java.config.Date")
    private Date operateTime;
}