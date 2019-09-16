package com.cj.sbasic.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserCheckVO")
public class UserCheckVO {
    /**
     * 用户id
     */
    @ApiModelProperty(name = "userInfoId",value = "用户id",dataType = "java.lang.Long")
    private Long userInfoId;

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
     * 身份 1-用户，2-家庭医生，3-肾病医生
     */
    @ApiModelProperty(name = "userType",value = "身份 1-用户，2-家庭医生，3-肾病医生",dataType = "java.lang.String")
    private String userType = "";

    /**
     * 省
     */
    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province = "";

    /**
     * 市
     */
    @ApiModelProperty(name = "city",value = "市",dataType = "java.lang.String")
    private String city = "";

    /**
     * 区
     */
    @ApiModelProperty(name = "area",value = "区",dataType = "java.lang.String")
    private String area = "";

    /**
     * 身份证号
     */
    @ApiModelProperty(name = "idNumber",value = "身份证号",dataType = "java.lang.String")
    private String idNumber = "";

    /**
     * 申请时间
     */
    @ApiModelProperty(name = "updateTime",value = "申请时间",dataType = "java.lang.String")
    private Date updateTime;

}
