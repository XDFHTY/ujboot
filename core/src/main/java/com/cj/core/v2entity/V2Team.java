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
@ApiModel(value = "v2_team")
public class V2Team implements Serializable {
    /**
     * 团队表主键
     */
    @ApiModelProperty(name = "teamId",value = "团队表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long teamId;

    /**
     * 团队名称
     */
    @ApiModelProperty(name = "teamName",value = "团队名称",dataType = "java.lang.String")
    private String teamName;

    /**
     * 团队简介
     */
    @ApiModelProperty(name = "teamDescribe",value = "团队简介",dataType = "java.lang.String")
    private String teamDescribe;

    /**
     * 团队头像
     */
    @ApiModelProperty(name = "teamHeadUrl",value = "团队头像",dataType = "java.lang.String")
    private String teamHeadUrl;

    /**
     * 团长id
     */
    @ApiModelProperty(name = "teamCaptainId",value = "团长id",dataType = "java.lang.Long")
    private Long teamCaptainId;

    /**
     * 是否解散，0-已解散 1-正常
     */
    @ApiModelProperty(name = "isDismiss",value = "是否解散，0-已解散 1-正常",dataType = "java.lang.String")
    private String isDismiss;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.util.Date")
    private Date createTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(name = "founderId",value = "创建人id",dataType = "java.lang.Long")
    private Long founderId;

    private static final long serialVersionUID = 1L;
}