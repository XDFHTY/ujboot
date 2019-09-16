package com.cj.skidney.domain;

import com.cj.core.entity.Drugwarn;
import com.cj.core.entity.DrugwarnTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用药实体类
 * @date： 2019/3/19 11:55
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用药实体类")
public class DrugModel implements Serializable {
    /**
     * 用药提醒id
     */
    @ApiModelProperty(name = "drugwarnId",value = "用药提醒id",dataType = "java.lang.Long")
    private Long drugwarnId;
    /**
     * 用药提醒
     */
    @ApiModelProperty(name = "drugwarn",value = "用药提醒",dataType = "com.cj.core.entity.Drugwarn")
    private Drugwarn drugwarn;
    /**
     * 用药提醒时间
     */
    @ApiModelProperty(name = "dtList",value = "用药提醒时间",dataType = "java.config.List")
    private List<DrugwarnTime> dtList;
}
