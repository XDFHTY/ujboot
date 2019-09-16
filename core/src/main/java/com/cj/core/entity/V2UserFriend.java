package com.cj.core.entity;

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
* Created by lw 2019-08-27 10:04:03
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_user_friend")
public class V2UserFriend implements Serializable {
    /**
     * 用户-亲友表主键
     */
    @ApiModelProperty(name = "userFriendId",value = "用户-亲友表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long userFriendId;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 亲友ID
     */
    @ApiModelProperty(name = "friendId",value = "亲友ID",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long friendId;

    /**
     * 0-解绑，1-绑定，默认为1
     */
    @ApiModelProperty(name = "bindState",value = "0-解绑，1-绑定，默认为1",dataType = "java.lang.String")
    private String bindState;

    /**
     * 绑定时间
     */
    @ApiModelProperty(name = "bindTime",value = "绑定时间",dataType = "java.util.Date")
    private Date bindTime;

    private static final long serialVersionUID = 1L;
}