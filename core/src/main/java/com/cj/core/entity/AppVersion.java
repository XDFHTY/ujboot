package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/19
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "app_version")
public class AppVersion {
    /**
     * 编译版本
     */
    @ApiModelProperty(name = "versioncode",value = "编译版本",dataType = "java.lang.Integer")
    private Integer versioncode;

    /**
     * 版本号
     */
    @ApiModelProperty(name = "version",value = "版本号",dataType = "java.lang.String")
    private String version;

    /**
     * 下载地址
     */
    @ApiModelProperty(name = "url",value = "下载地址",dataType = "java.lang.String")
    private String url;

    /**
     * 版本说明
     */
    @ApiModelProperty(name = "msg",value = "版本说明",dataType = "java.lang.String")
    private String msg;

    /**
     * 1-安卓用户，2-安卓医生，3-IOS用户，4-IOS医生
     */
    @ApiModelProperty(name = "type",value = "1-安卓用户，2-安卓医生，3-IOS用户，4-IOS医生",dataType = "java.lang.String")
    private String type;

    /**
     * 发行日期
     */
    @ApiModelProperty(name = "issuetime",value = "发行日期",dataType = "java.config.Date")
    private Date issuetime;
}