package com.cj.sadmin.domain;

import com.cj.core.entity.OperateLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "操作日志模块返回值")
public class VoLog extends OperateLog {

    @ApiModelProperty(name = "customerName",value = "用户名",dataType = "java.lang.String")
    private String customerName;

    @ApiModelProperty(name = "fullName",value = "姓名",dataType = "java.lang.String")
    private String fullName;


}
