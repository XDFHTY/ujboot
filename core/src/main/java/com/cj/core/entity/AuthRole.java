package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "角色对象")
public class AuthRole implements Serializable {
    /**
     * 权限角色表
     */
    @ApiModelProperty(name = "roleId",value = "权限角色表",dataType = "Long")
    private Long roleId;

    /**
     * 系统使用的角色名ROLE_XXX
     */
    @ApiModelProperty(name = "roleName",value = "系统使用的角色名ROLE_XXX",dataType = "String")
    private String roleName;

    /**
     * 角色等级
     */
    @ApiModelProperty(name = "roleGrade",value = "角色等级",dataType = "String")
    private String roleGrade;

    /**
     * 状态 0-禁用 1-使用 默认1
     */
    @ApiModelProperty(name = "roleState",value = "状态 0-禁用 1-使用 默认1",dataType = "String")
    private String roleState;

}