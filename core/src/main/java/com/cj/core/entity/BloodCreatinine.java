package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/14
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "blood_creatinine")
public class BloodCreatinine {
    /**
     * 血肌酐
     */
    @ApiModelProperty(name = "bloodCreatinineId",value = "血肌酐",dataType = "java.lang.Long")
    private Long bloodCreatinineId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 0-异常 1-正常
     */
    @ApiModelProperty(name = "abnormal",value = "0-异常 1-正常",dataType = "java.lang.String")
    private String abnormal;

    /**
     * 血肌酐
     */
    @ApiModelProperty(name = "sc",value = "血肌酐",dataType = "java.lang.Double")
    private Double sc;

    /**
     * 检测时间
     */
    @ApiModelProperty(name = "createTime",value = "检测时间",dataType = "java.config.Date")
    private Date createTime;
}