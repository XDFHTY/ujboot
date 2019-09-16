package com.cj.suser.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 新增用户实体类
 * @date： 2019/6/28 11:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新增用户实体类")
@JsonIgnoreProperties(value = {"handler"})
public class IPUModel {

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
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "姓名",dataType = "java.lang.String")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(name = "sex",value = "性别",dataType = "java.lang.String")
    private String sex;

    /**
     * 绑定医生id
     */
    @ApiModelProperty(name = "list",value = "绑定医生",dataType = "java.util.List")
    private List<Long> list;
}
