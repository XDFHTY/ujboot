package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询咨询量评分")
public class VoScore {

    @ApiModelProperty(name = "consultNum",value = "咨询量",dataType = "Integer")
    private int consultNum;

    @ApiModelProperty(name = "averageScore",value = "平均分",dataType = "String")
    private String averageScore;
}
