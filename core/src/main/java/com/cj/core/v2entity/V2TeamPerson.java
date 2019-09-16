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
@ApiModel(value = "v2_team_person")
public class V2TeamPerson implements Serializable {
    /**
     * 团队人员表主键
     */
    @ApiModelProperty(name = "teamPersonId",value = "团队人员表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long teamPersonId;

    /**
     * 团队id
     */
    @ApiModelProperty(name = "teamId",value = "团队id",dataType = "java.lang.Long")
    private Long teamId;

    /**
     * 成员id
     */
    @ApiModelProperty(name = "customerId",value = "成员id",dataType = "java.lang.Long")
    private Long customerId;

    /**
     * 加入时间
     */
    @ApiModelProperty(name = "joinTime",value = "加入时间",dataType = "java.util.Date")
    private Date joinTime;

    /**
     * 删除状态 0-已删除 1-正常
     */
    @ApiModelProperty(name = "deleteState",value = "删除状态 0-已删除 1-正常",dataType = "java.lang.String")
    private String deleteState;


    private static final long serialVersionUID = 1L;
}