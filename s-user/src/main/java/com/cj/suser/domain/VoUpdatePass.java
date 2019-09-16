package com.cj.suser.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Api(tags = "用户修改密码实体")
public class VoUpdatePass {

    @ApiModelProperty(name = "oldPass",value = "旧密码",dataType = "String")
    private String oldPass;

    @ApiModelProperty(name = "newPass",value = "新密码",dataType = "String")
    private String newPass;
}
