package com.cj.spension.domain;

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
 * @Description: 用户实体类
 * @date： 2019/3/11 15:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类")
public class UserModel implements Serializable {
    /**
     * 用户信息实体
     */
    @ApiModelProperty(name = "userInfo",value = "用户信息实体")
    private UserInfo userInfo;
    /**
     * 用户实体
     */
    @ApiModelProperty(name = "user",value = "用户实体")
    private User user;
    /**
     * 用户表id
     */
    @ApiModelProperty(name = "userId",value = "用户表ID",dataType = "java.lang.Long")
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
     * 病名
     */
    @ApiModelProperty(name = "disease",value = "病名",dataType = "java.lang.String")
    private String disease;
    /**
     * 医院
     */
    @ApiModelProperty(name = "hospital",value = "医院",dataType = "java.lang.String")
    private String hospital;
    /**
     * 绑定设备记录
     */
    @ApiModelProperty(name = "ubeList",value = "绑定设备记录")
    private List<UserEquipment> ubeList;
    /**
     * 疾病记录
     */
    @ApiModelProperty(name = "fimList",value = "绑定设备记录")
    private List<FillIllModel> fimList;
}
