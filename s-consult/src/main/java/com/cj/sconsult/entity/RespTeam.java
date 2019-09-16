package com.cj.sconsult.entity;

import com.cj.core.v2entity.V2Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队列表 实体类
 * Created by JuLei on 2019/7/2.
 */
@Data
public class RespTeam extends V2Team {

    @ApiModelProperty(name = "teamCaptainName",value = "团长姓名",dataType = "String")
    private String teamCaptainName;

    @ApiModelProperty(name = "teamType",value = "所属类别",dataType = "String")
    private String teamType;

    @ApiModelProperty(name = "number",value = "人数",dataType = "Integer")
    private Integer number;

    @ApiModelProperty(name = "inquiry", value = "咨询量", dataType = "Integer")
    private Integer inquiry;

    @ApiModelProperty(name = "score",value = "评分",dataType = "String")
    private String score;

}
