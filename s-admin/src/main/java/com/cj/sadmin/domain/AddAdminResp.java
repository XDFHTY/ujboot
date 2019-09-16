package com.cj.sadmin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "注册/添加账号 接口返回参数")
public class AddAdminResp {

    @ApiModelProperty(name = "adminId",value = "管理员ID",dataType = "long")
    private long adminId;

    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "long")
    private long userId;


}
