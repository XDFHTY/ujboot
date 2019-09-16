package com.cj.suser.domain;

import com.cj.core.entity.FallIll;
import com.cj.core.entity.FallIllImg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "疾病详情返回对象")
public class VoFallIllById extends FallIll {

    @ApiModelProperty(name = "hospitalName",value = "医院名字",dataType = "String")
    private String hospitalName;

    @ApiModelProperty(name = "diseaseName",value = "疾病名字",dataType = "String")
    private String diseaseName;

    @ApiModelProperty(name = "fallIllImgs",value = "报告单地址集合",dataType = "String")
    private List<FallIllImg> fallIllImgs;
}
