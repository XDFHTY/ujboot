package com.cj.sim.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会话返回实体类
 * Created by XD on 2019/4/8.
 */
@Data
public class RespConverstion {
    //会话id
    @ApiModelProperty(name = "conversationId",value = "会话id",dataType = "Long")
    private Long conversationId;

    @ApiModelProperty(name = "nickName",value = "昵称",dataType = "String")
    private String nickName;

    @ApiModelProperty(name = "headUrl",value = "头像",dataType = "String")
    private String headUrl;

    @ApiModelProperty(name = "name",value = "姓名",dataType = "String")
    private String name;

    @ApiModelProperty(name = "bindType",value = "是否为绑定关系 0-未绑定  1-已绑定",dataType = "String")
    private String bindType;
}
