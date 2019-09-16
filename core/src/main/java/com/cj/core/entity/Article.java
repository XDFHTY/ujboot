package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/03/18
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "article")
public class Article {
    /**
     * 文章表
     */
    @ApiModelProperty(name = "articleId",value = "文章表",dataType = "java.lang.Long")
    private Long articleId;

    /**
     * 文章标题
     */
    @ApiModelProperty(name = "articleTitle",value = "文章标题",dataType = "java.lang.String")
    private String articleTitle;

    /**
     * 1肾病文章，2健康小知识
     */
    @ApiModelProperty(name = "articleSign",value = "1肾病文章，2健康小知识",dataType = "java.lang.String")
    private String articleSign;

    /**
     * 浏览量
     */
    @ApiModelProperty(name = "articleViews",value = "浏览量",dataType = "java.lang.Integer")
    private Integer articleViews;

    /**
     * 文章发布时间
     */
    @ApiModelProperty(name = "articleDate",value = "文章发布时间",dataType = "java.config.Date")
    private Date articleDate;

    /**
     * 是否显示 0-隐藏 1-显示
     */
    @ApiModelProperty(name = "isDisplay",value = "是否显示 0-隐藏 1-显示",dataType = "java.lang.String")
    private String isDisplay;

    /**
     * 是否删除 0-已删除 1-正常
     */
    @ApiModelProperty(name = "articleState",value = "是否删除 0-已删除 1-正常",dataType = "java.lang.String")
    private String articleState;

    /**
     * 封面
     */
    @ApiModelProperty(name = "coverUrl",value = "封面",dataType = "java.lang.String")
    private String coverUrl;

    /**
     * 文章内容
     */
    @ApiModelProperty(name = "articleContent",value = "文章内容",dataType = "java.lang.String")
    private String articleContent;
}