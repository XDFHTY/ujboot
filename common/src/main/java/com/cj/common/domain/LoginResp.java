package com.cj.common.domain;

import com.cj.core.entity.AdminInfo;
import com.cj.core.entity.AuthRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * IfLogin 接口返回参数实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录 接口返回参数")
public class LoginResp {

    @ApiModelProperty(name = "imId",value = "聊天id",dataType = "Long")
    private long imId;

    @ApiModelProperty(name = "token",value = "token",dataType = "String")
    private String token;

    @ApiModelProperty(name = "userType",value = "用户类型",dataType = "String")
    private String userType;

    @ApiModelProperty(name = "issuedAt",value = "token签发时间",dataType = "Date")
    private Date issuedAt;

    @ApiModelProperty(name = "roles",value = "用户角色集合",dataType = "List")
    private List<AuthRole> roles;

    @ApiModelProperty(name = "isInfo",value = "用户信息是否已完善(true=已完善)，个人、医生用",dataType = "Boolean")
    private Boolean isInfo;

    @ApiModelProperty(name = "state",value = "账号状态",dataType = "String")
    private String state;


    @ApiModelProperty(name = "adminInfo",value = "管理员详情",dataType = "AdminInfo")
    private AdminInfo adminInfo;


}
