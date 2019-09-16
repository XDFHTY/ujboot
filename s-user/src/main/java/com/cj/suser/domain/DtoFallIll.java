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
@ApiModel(value = "疾病详情请求对象")
public class DtoFallIll extends FallIll {

    @ApiModelProperty(name = "fallIllImgs",value = "报告单地址集合",dataType = "String")
    private List<FallIllImg> fallIllImgs;
}
