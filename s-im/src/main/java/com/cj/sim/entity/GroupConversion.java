package com.cj.sim.entity;

import com.cj.core.v2entity.V2GroupConversation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群聊会话
 * Created by JuLei on 2019/6/25.
 */
@Data
public class GroupConversion extends V2GroupConversation {
    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "String")
    private String goodType;

    /**
     * 对方的昵称
     */
    @ApiModelProperty(name = "nickName",value = "对方的昵称",dataType = "String")
    private String nickName;

    /**
     * 对方的姓名
     */
    @ApiModelProperty(name = "name",value = "对方的姓名",dataType = "String")
    private String name;

    /**
     * 对方的头像地址
     */
    @ApiModelProperty(name = "headUrl",value = "对方的头像地址",dataType = "String")
    private String headUrl;
}
