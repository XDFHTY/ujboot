package com.cj.sim.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户头像昵称实体类
 * Created by JuLei on 2019/7/8.
 */
@Data
public class RespUserInfo {
    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "Long")
    private Long userId;

    /**
     * 用户类型
     */
    @ApiModelProperty(name = "userType",value = "用户类型 1-群主 2-医生 3-用户",dataType = "String")
    private String userType;

    /**
     * 对方的昵称
     */
    @ApiModelProperty(name = "nickName",value = "对方的昵称",dataType = "String")
    private String nickName;

    /**
     * 对方的姓名
     */
    @ApiModelProperty(name = "name",value = "对方的姓名",dataType = "String")
    private String name;

    /**
     * 对方的头像地址
     */
    @ApiModelProperty(name = "headUrl",value = "对方的头像地址",dataType = "String")
    private String headUrl;
}
