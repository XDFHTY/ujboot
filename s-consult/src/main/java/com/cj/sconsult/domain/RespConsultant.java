package com.cj.sconsult.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 咨询员 返回实体类
 * Created by XD on 2019/3/14.
 */
@Data
public class RespConsultant {

    @ApiModelProperty(name = "adminId",value = "咨询员ID",dataType = "Long")
    private Long adminId;

    @ApiModelProperty(name = "nickName",value = "咨询员昵称",dataType = "String")
    private String nickName;

    @ApiModelProperty(name = "heardUrl",value = "咨询员头像",dataType = "String")
    private String heardUrl;

    @ApiModelProperty(name = "num",value = "咨询量",dataType = "Integer")
    private Integer num;
}
