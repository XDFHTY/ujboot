package com.cj.suser.domain;

import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.DoctorInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 新增医生实体类
 * @date： 2019/6/29 9:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新增医生实体类")
@JsonIgnoreProperties(value = {"handler"})
public class IDUModel {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName",value = "用户名",dataType = "java.lang.String")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(name = "userPass",value = "密码",dataType = "java.lang.String")
    private String userPass;

    /**
     * 扩展信息
     */
    @ApiModelProperty(name = "voDoctorInfo",value = "扩展信息")
    private VoDoctorInfo voDoctorInfo;

}
