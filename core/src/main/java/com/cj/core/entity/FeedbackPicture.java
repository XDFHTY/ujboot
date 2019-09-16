package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2019/03/06
*/
@Data
@ApiModel(value = "feedback_picture")
public class FeedbackPicture {
    /**
     * 反馈信息图片
     */
    @ApiModelProperty(name = "feedbackPictureId",value = "反馈信息图片",dataType = "java.lang.Long")
    private Long feedbackPictureId;

    /**
     * 反馈记录id
     */
    @ApiModelProperty(name = "feedbackId",value = "反馈记录id",dataType = "java.lang.Long")
    private Long feedbackId;

    /**
     * 图片路径
     */
    @ApiModelProperty(name = "routeUrl",value = "图片路径",dataType = "java.lang.String")
    private String routeUrl;
}