package com.cj.sbasic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserDetailVO")
public class UserDetailVO {
    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name = "";

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phoneNumber",value = "手机号",dataType = "java.lang.String")
    private String phoneNumber = "";

    /**
     * 地址
     */
    @ApiModelProperty(name = "address",value = "地址",dataType = "java.lang.String")
    private String address = "";

    /**
     * 身份证号
     */
    @ApiModelProperty(name = "idNumber",value = "身份证号",dataType = "java.lang.String")
    private String idNumber = "";

    /**
     * 身份证正面
     */
    @ApiModelProperty(name = "idJustUrl",value = "身份证正面",dataType = "java.lang.String")
    private String idJustUrl = "";

    /**
     * 身份证反面
     */
    @ApiModelProperty(name = "idBackUrl",value = "身份证反面",dataType = "java.lang.String")
    private String idBackUrl = "";
}
