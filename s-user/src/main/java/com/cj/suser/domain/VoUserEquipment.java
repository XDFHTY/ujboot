package com.cj.suser.domain;

import com.cj.core.entity.UserEquipment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户绑定设备记录信息返回值")
public class VoUserEquipment extends UserEquipment {

    @ApiModelProperty(name = "inspectTime",value = "检查时间",dataType = "java.lang.String")
    private Date inspectTime;
}
