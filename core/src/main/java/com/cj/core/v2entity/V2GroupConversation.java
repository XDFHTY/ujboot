package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-25 11:06:25
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_group_conversation")
public class V2GroupConversation implements Serializable {
    /**
     * 群聊会话表主键
     */
    @ApiModelProperty(name = "groupConversationId",value = "群聊会话表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long groupConversationId;

    /**
     * 团队id
     */
    @ApiModelProperty(name = "teamId",value = "团队id",dataType = "java.lang.Long")
    private Long teamId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 环信群组id
     */
    @ApiModelProperty(name = "hxGroupId",value = "环信群组id",dataType = "java.lang.String")
    private String hxGroupId;

    /**
     * 结束标记 0-已结束 1-未结束
     */
    @ApiModelProperty(name = "isEnd",value = "结束标记 0-已结束 1-未结束",dataType = "java.lang.String")
    private String isEnd;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime",value = "结束时间",dataType = "java.util.Date")
    private Date endTime;

    private static final long serialVersionUID = 1L;
}