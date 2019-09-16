package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "banner")
public class Banner {
    /**
     * 轮播图管理
     */
    @ApiModelProperty(name = "bannerId",value = "轮播图管理",dataType = "java.lang.Long")
    private Long bannerId;

    /**
     * 图片路径
     */
    @ApiModelProperty(name = "bannerImgUrl",value = "图片路径",dataType = "java.lang.String")
    private String bannerImgUrl;

    /**
     * 跳转地址
     */
    @ApiModelProperty(name = "url",value = "跳转地址",dataType = "java.lang.String")
    private String url;

    /**
     * 分类 1-个人端 2-医生端
     */
    @ApiModelProperty(name = "bannerType",value = "分类 1-个人端 2-医生端",dataType = "java.lang.String")
    private String bannerType;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.config.Date")
    private Date createTime;

    /**
     * 删除状态 0-已删除 1-正常
     */
    @ApiModelProperty(name = "bannerState",value = "删除状态 0-已删除 1-正常",dataType = "java.lang.String")
    private String bannerState;
}