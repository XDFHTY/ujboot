package com.cj.suser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "疾病列表对象")
public class VoFallIllList {

    @ApiModelProperty(name = "fallIllId",value = "疾病列表ID",dataType = "Long")
    private long fallIllId;

    @ApiModelProperty(name = "checkTime",value = "检查时间",dataType = "Date")
    private Date checkTime;

    @ApiModelProperty(name = "diseaseId",value = "疾病ID",dataType = "Long")
    private long diseaseId;

    @ApiModelProperty(name = "diseaseName",value = "疾病名称",dataType = "String")
    private String diseaseName;


}
