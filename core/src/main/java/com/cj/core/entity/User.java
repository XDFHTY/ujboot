package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/06
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户基本信息")
public class User {
    /**
     * 用户表
     */
    @ApiModelProperty(name = "userId",value = "用户表ID",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName",value = "用户名",dataType = "java.lang.String")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(name = "userPass",value = "密码",dataType = "java.lang.String")
    private String userPass;

    /**
     * 盐值
     */
    @ApiModelProperty(name = "saltVal",value = "盐值",dataType = "java.lang.String")
    private String saltVal;

    /**
     * 用户类型
     */
    @ApiModelProperty(name = "userType",value = "0-游客、1-用户 、2-家庭医生、3-肾病医生 、4-营养师、5-乡干部、6-卫健委干部、7-离退休干部、8-护士、9-健康管理师",dataType = "java.lang.String")
    private String userType;

    /**
     * 账号有效期
     */
    @ApiModelProperty(name = "validTime",value = "账号有效期",dataType = "java.config.Date")
    private Date validTime;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phoneNumber",value = "手机号",dataType = "java.lang.String")
    private String phoneNumber;

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     */
    @ApiModelProperty(name = "isPhone",value = "是否验证，0-未验证，1-已验证，默认为0",dataType = "java.lang.String")
    private String isPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "eMail",value = "邮箱",dataType = "java.lang.String")
    private String eMail;

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     */
    @ApiModelProperty(name = "isMail",value = "是否验证，0-未验证，1-已验证，默认为0",dataType = "java.lang.String")
    private String isMail;

    /**
     * QQ号
     */
    @ApiModelProperty(name = "qqNumber",value = "QQ号",dataType = "java.lang.String")
    private String qqNumber;

    /**
     * WX号
     */
    @ApiModelProperty(name = "wxNumber",value = "WX号",dataType = "java.lang.String")
    private String wxNumber;

    /**
     * WB号
     */
    @ApiModelProperty(name = "wbNumber",value = "WB号",dataType = "java.lang.String")
    private String wbNumber;

    /**
     * 账号状态，0-已删除，1-正常，2-未审核，3-审核中，4-审核不通过
     */
    @ApiModelProperty(name = "state",value = "账号状态，0-已删除，1-正常，2-未审核，3-审核中，4-审核不通过",dataType = "java.lang.String")
    private String state;

    /**
     * 操作员ID
     */
    @ApiModelProperty(name = "operationId",value = "操作员ID",dataType = "java.lang.Long")
    private Long operationId;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.config.Date")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(name = "updateTime",value = "修改时间",dataType = "java.config.Date")
    private Date updateTime;

}