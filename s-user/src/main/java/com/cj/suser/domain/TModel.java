package com.cj.suser.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： 刘磊
 * @Description: 团队信息实体类
 * @date： 2019/6/29 10:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "团队信息实体类")
@JsonIgnoreProperties(value = {"handler"})
public class TModel {

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
     * 团长id
     */
    @ApiModelProperty(name = "teamCaptainId",value = "团长id",dataType = "java.lang.Long")
    private Long teamCaptainId;
    /**
     * 团长
     */
    @ApiModelProperty(name = "teamCaptainName",value = "团长",dataType = "java.lang.Long")
    private String teamCaptainName;

    /**
     * 团队头像
     */
    @ApiModelProperty(name = "teamHeadUrl",value = "团队头像",dataType = "java.lang.String")
    private String teamHeadUrl;

    /**
     * 团队简介
     */
    @ApiModelProperty(name = "teamDescribe",value = "团队简介",dataType = "java.lang.String")
    private String teamDescribe;

    /**
     * 团队评分
     */
    @ApiModelProperty(name = "score",value = "团队评分",dataType = "java.lang.String")
    private String score;

    /**
     * 团队人数
     */
    @ApiModelProperty(name = "num",value = "团队人数",dataType = "java.lang.String")
    private String num;

    /**
     * 擅长疾病
     */
    @ApiModelProperty(name = "advantages",value = "擅长疾病",dataType = "java.lang.String")
    private String advantages;

    /**
     * 团队类型
     */
    @ApiModelProperty(name = "type",value = "团队类型",dataType = "java.lang.String")
    private String type;
}
