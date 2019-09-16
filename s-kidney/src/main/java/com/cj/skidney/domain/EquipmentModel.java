package com.cj.skidney.domain;

import com.cj.core.entity.UserEquipment;
import com.cj.core.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 设备信息实体类
 * @date： 2019/3/14 18:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "设备信息实体类")
public class EquipmentModel implements Serializable {
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
     * 省
     */
    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(name = "city",value = "市",dataType = "java.lang.String")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(name = "area",value = "区",dataType = "java.lang.String")
    private String area;
    /**
     * 绑定设备
     */
    @ApiModelProperty(name = "ubeList",value = "绑定设备")
    private List<UserEquipment> ueList;
}
