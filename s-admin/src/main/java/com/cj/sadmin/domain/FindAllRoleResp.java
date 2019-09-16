package com.cj.sadmin.domain;

import com.cj.core.domain.AuthModulars;
import com.cj.core.entity.AuthRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FindAllRole 接口返回参数")
public class FindAllRoleResp {


    @ApiModelProperty(name = "authRoles",value = "角色集合",dataType = "List")
    private List<AuthRole> authRoles;

    @ApiModelProperty(name = "authModulars",value = "系统内权限列表",dataType = "AuthModulars")
    private AuthModulars authModulars;
}
