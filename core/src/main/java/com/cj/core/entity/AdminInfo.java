package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/10
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "admin_info")
public class AdminInfo {
    /**
     * 管理员扩展信息表
     */
    @ApiModelProperty(name = "adminInfoId",value = "管理员扩展信息表",dataType = "java.lang.Long")
    private Long adminInfoId;

    /**
     * 管理员id
     */
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "java.lang.Long")
    private Long adminId;

    /**
     * 管理员姓名
     */
    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "java.lang.String")
    private String fullName;

    /**
     * 管理员电话
     */
    @ApiModelProperty(name = "phone",value = "管理员电话",dataType = "java.lang.String")
    private String phone;

    /**
     * 管理员昵称
     */
    @ApiModelProperty(name = "nickName",value = "管理员昵称",dataType = "java.lang.String")
    private String nickName;

    /**
     * 头像地址
     */
    @ApiModelProperty(name = "heardUrl",value = "头像地址",dataType = "java.lang.String")
    private String heardUrl;
}