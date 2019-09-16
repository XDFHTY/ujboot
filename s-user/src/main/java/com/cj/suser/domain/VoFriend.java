package com.cj.suser.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户绑定亲友实体")
public class VoFriend {

    @ApiModelProperty(name = "userId",value = "ID",dataType = "java.lang.Long")
    private Long userId;


    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name = "";

    @ApiModelProperty(name = "phone",value = "电话",dataType = "java.lang.String")
    private String phone = "";

    @ApiModelProperty(name = "headurl",value = "头像",dataType = "java.lang.String")
    private String headurl = "";

    @ApiModelProperty(name = "bindTime",value = "绑定时间",dataType = "java.util.Date")
    private Date bindTime;


    @ApiModelProperty(name = "userType",value = "用户类型",dataType = "java.lang.String")
    private String userType;



}
