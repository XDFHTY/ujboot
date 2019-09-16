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
@ApiModel(value = "用户—角色关系表")
public class AuthCustomerRole  implements Serializable {
    /**
     * 用户—角色关系表
     */
    @ApiModelProperty(name = "id",value = "ID",dataType = "Long")
    private Long id;

    /**
     * 用户id-包括admin和user ID
     */
    @ApiModelProperty(name = "customerId",value = "用户id-包括admin和user ID",dataType = "Long")
    private Long customerId;

    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId",value = "角色id",dataType = "Long")
    private Long roleId;
}