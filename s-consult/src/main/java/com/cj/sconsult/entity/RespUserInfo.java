package com.cj.sconsult.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户头像昵称实体类
 * Created by JuLei on 2019/7/8.
 */
@Data
public class RespUserInfo {

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
