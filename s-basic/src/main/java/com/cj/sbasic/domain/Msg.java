package com.cj.sbasic.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lw 2019/04/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "msg")
public class Msg {
    /**
     * 推送消息id
     */
    @ApiModelProperty(name = "msgId",value = "推送消息id",dataType = "java.lang.Long")
    private Long msgId;

    /**
     * 消息类型，0-广告消息，1-活动消息
     */
    @ApiModelProperty(name = "msgType",value = "消息类型，0-广告消息，1-活动消息",dataType = "java.lang.String")
    private String msgType;

    /**
     * 发布时间
     */
    @ApiModelProperty(name = "publishTime",value = "发布时间",dataType = "java.config.Date")
    private Date publishTime;

    /**
     * 标题
     */
    @ApiModelProperty(name = "title",value = "标题",dataType = "java.lang.String")
    private String title;

    /**
     * 推送对象描述
     */
    @ApiModelProperty(name = "objectstr",value = "推送对象描述",dataType = "java.lang.String")
    private String objectstr;

    /**
     * 内容富文本
     */
    @ApiModelProperty(name = "content",value = "内容富文本",dataType = "java.lang.String")
    private String content;
}