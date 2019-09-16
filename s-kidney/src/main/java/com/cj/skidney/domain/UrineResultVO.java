package com.cj.skidney.domain;

import com.cj.core.entity.UrineResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： 刘磊
 * @Description: 尿检结果实体类VO
 * @date： 2019/4/4 9:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "尿检结果实体类VO")
public class UrineResultVO extends UrineResult {
    /**
     * 标记
     */
    @ApiModelProperty(name = "isUpload",value = "标记",dataType = "java.lang.Boolean")
    private Boolean isUpload = true;
}
