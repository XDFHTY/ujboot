package com.cj.suser.domain;

import com.cj.core.entity.UserBindDoctor;
import com.cj.core.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VoUserInfo")
public class VoUserInfo extends UserInfo{


    @ApiModelProperty(name = "userBindDoctors",value = "用户绑定的医生集合")
    private List<UserBindDoctor> userBindDoctors;

    @ApiModelProperty(name = "region",value = "地区")
    private String region;
}
