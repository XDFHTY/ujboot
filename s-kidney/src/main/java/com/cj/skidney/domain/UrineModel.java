package com.cj.skidney.domain;

import com.cj.core.entity.UrineAbnormal;
import com.cj.core.entity.UrineIdentification;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author： 刘磊
 * @Description: 尿检结果实体类
 * @date： 2019/3/13 13:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "尿检结果实体类")
public class UrineModel implements Serializable {
    /**
     * 尿常规检查结果值
     */
    @ApiModelProperty(name = "urineResultId",value = "尿常规检查结果值",dataType = "java.lang.Long")
    private Long urineResultId;
    /**
     * 尿检设备标识
     */
    @ApiModelProperty(name = "urineIdentification",value = "尿检设备标识")
    private UrineIdentification urineIdentification;
    /**
     * 尿检结果
     */
    @ApiModelProperty(name = "urineResultVO",value = "尿检结果")
    private UrineResultVO urineResultVO;
    /**
     * 尿检异常
     */
    @ApiModelProperty(name = "urineAbnormal",value = "尿检异常")
    private UrineAbnormal urineAbnormal;
}
