package com.cj.suser.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author： 刘磊
 * @Description: 绑定记录实体
 * @date： 2019/6/28 14:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "绑定记录实体")
@JsonIgnoreProperties(value = {"handler"})
public class BRModel {
    /**
     * 绑定的医生id
     */
    @ApiModelProperty(name = "doctorId",value = "绑定的医生id",dataType = "java.lang.Long")
    private Long doctorId;

    /**
     * 医生分类 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部
     */
    @ApiModelProperty(name = "doctorType",value = "医生分类 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部",dataType = "java.lang.String")
    private String doctorType;

    /**
     * 姓名
     */
    @ApiModelProperty(name = "doctorName",value = "姓名",dataType = "java.lang.String")
    private String doctorName;

    /**
     * 绑定状态 0-解绑 1-绑定
     */
    @ApiModelProperty(name = "isBind",value = "绑定状态 0-解绑 1-绑定",dataType = "java.lang.String")
    private String isBind;

    /**
     * 操作时间
     */
    @ApiModelProperty(name = "operationTime",value = "操作时间",dataType = "java.config.Date")
    private Date operationTime;
}
