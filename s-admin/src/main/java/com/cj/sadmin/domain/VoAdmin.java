package com.cj.sadmin.domain;

import com.cj.core.entity.Admin;
import com.cj.core.entity.AdminInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "查询所有管理员账号返回")
public class VoAdmin {

    private Admin admin;


    private AdminInfo adminInfo;

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Long")
    private Long roleId;

    @ApiModelProperty(name = "roleName",value = "角色名称",dataType = "String")
    private String roleName;
}
