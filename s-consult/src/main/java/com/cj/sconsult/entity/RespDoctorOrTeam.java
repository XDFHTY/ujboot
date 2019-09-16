package com.cj.sconsult.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生或团队信息 返回实体类
 * Created by JuLei on 2019/6/27.
 */
@Data
public class RespDoctorOrTeam {

    /**
     * 公共属性
     */
    @ApiModelProperty(name = "infoType", value = "1-个人信息 2-团队信息", dataType = "String")
    private String infoType;
    @ApiModelProperty(name = "name",value = "名称",dataType = "String")
    private String name;
    @ApiModelProperty(name = "inquiry",value = "咨询量",dataType = "Integer")
    private Integer inquiry;
    @ApiModelProperty(name = "score",value = "评分",dataType = "String")
    private String score;
    @ApiModelProperty(name = "advantages",value = "擅长疾病",dataType = "String")
    private String advantages;
    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "String")
    private String goodType;
    @ApiModelProperty(name = "company",value = "单位",dataType = "String")
    private String company;
    @ApiModelProperty(name = "province",value = "省",dataType = "java.lang.String")
    private String province;
    @ApiModelProperty(name = "city",value = "市",dataType = "java.lang.String")
    private String city;
    @ApiModelProperty(name = "area",value = "区",dataType = "java.lang.String")
    private String area;
    @ApiModelProperty(name = "doctorId",value = "医生id",dataType = "Long")
    private Long doctorId;
    @ApiModelProperty(name = "headUrl",value = "头像地址",dataType = "java.lang.String")
    private String headUrl;

    /**
     * 个人属性
     */
    @ApiModelProperty(name = "roleName", value = "角色", dataType = "String")
    private String roleName;
    @ApiModelProperty(name = "departmentName",value = "科室名字",dataType = "String")
    private String departmentName;
    @ApiModelProperty(name = "hospitalName",value = "医院名字",dataType = "String")
    private String hospitalName;
    @ApiModelProperty(name = "title",value = "职称",dataType = "String")
    private String title;


    /**
     * 团队属性
     */
    @ApiModelProperty(name = "teamId",value = "团队id",dataType = "Long")
    private Long teamId;
    @ApiModelProperty(name = "hxGroupId",value = "环信群组id",dataType = "String")
    private String hxGroupId;
    @ApiModelProperty(name = "commander",value = "团长名字",dataType = "String")
    private String commander;
    @ApiModelProperty(name = "number",value = "人数",dataType = "Integer")
    private Integer number;
    @ApiModelProperty(name = "describe",value = "团队描述",dataType = "String")
    private String describe;




}
