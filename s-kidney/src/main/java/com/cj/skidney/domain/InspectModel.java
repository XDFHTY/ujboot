package com.cj.skidney.domain;

import com.cj.core.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 检查记录实体类
 * @date： 2019/3/13 11:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "检查记录实体类")
public class InspectModel implements Serializable {
    /**
     * 用户信息实体
     */
    @ApiModelProperty(name = "userInfo",value = "用户信息实体")
    private UserInfo userInfo;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;
    /**
     * 检查次数
     */
    @ApiModelProperty(name = "inspectSum",value = "检查报告")
    private Integer inspectSum;
    /**
     * 异常次数
     */
    @ApiModelProperty(name = "abnormalSum",value = "检查报告")
    private Integer abnormalSum;
    /**
     * 疾病名字
     */
    @ApiModelProperty(name = "disease",value = "疾病名字",dataType = "java.lang.String")
    private String disease;
}
