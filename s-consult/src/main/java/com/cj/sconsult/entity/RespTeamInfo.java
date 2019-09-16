package com.cj.sconsult.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 团队详情 实体类
 * Created by JuLei on 2019/6/28.
 */
@Data
public class RespTeamInfo {
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
     * 团长
     */
    @ApiModelProperty(name = "teamCaptainId",value = "团长id",dataType = "java.lang.Long")
    private Long teamCaptainId;

    @ApiModelProperty(name = "teamCaptainName",value = "团长姓名",dataType = "java.lang.String")
    private String teamCaptainName;

    @ApiModelProperty(name = "roleId",value = "团长角色id",dataType = "java.lang.Long")
    private Long roleId;

    @ApiModelProperty(name = "number",value = "团队人数",dataType = "java.lang.Integer")
    private Integer number;

    @ApiModelProperty(name = "inquiry",value = "咨询量",dataType = "Integer")
    private Integer inquiry;
    @ApiModelProperty(name = "score",value = "评分",dataType = "String")
    private String score;
    @ApiModelProperty(name = "advantages",value = "擅长疾病",dataType = "java.lang.String")
    private String advantages;


    @ApiModelProperty(name = "number",value = "团员列表",dataType = "com.cj.sconsult.entity.RespDoctorInfo")
    private List<RespDoctorInfo> list;

}
