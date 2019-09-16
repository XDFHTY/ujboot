package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/01
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "urine_abnormal")
public class UrineAbnormal {
    /**
     * 尿检异常信息
     */
    @ApiModelProperty(name = "urineAbnormalId",value = "尿检异常信息",dataType = "java.lang.Long")
    private Long urineAbnormalId;

    /**
     * 尿常规检查结果表id
     */
    @ApiModelProperty(name = "urineResultId",value = "尿常规检查结果表id",dataType = "java.lang.Long")
    private Long urineResultId;

    /**
     * 尿微量白蛋白肌酐比值 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalAcr",value = "尿微量白蛋白肌酐比值 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalAcr;

    /**
     * 白细胞 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalLeu",value = "白细胞 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalLeu;

    /**
     * 亚硝酸盐 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalNit",value = "亚硝酸盐 1-异常 0-正常常",dataType = "java.lang.Integer")
    private Integer abnormalNit;

    /**
     * 尿胆原 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalUbg",value = "尿胆原 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalUbg;

    /**
     * 蛋白质 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalPro",value = "蛋白质 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalPro;

    /**
     * PH值 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalPh",value = "PH值 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalPh;

    /**
     * 潜血 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalBld",value = "潜血 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalBld;

    /**
     * 比重 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalSg",value = "比重 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalSg;

    /**
     * 酮体 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalKet",value = "酮体 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalKet;

    /**
     * 胆红素 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalBil",value = "胆红素 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalBil;

    /**
     * 葡萄糖 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalGlu",value = "葡萄糖 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalGlu;

    /**
     * 维生素c 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalVc",value = "维生素c 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalVc;

    /**
     * 微量白蛋白 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalMa",value = "微量白蛋白 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalMa;

    /**
     * 肌酐 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalCre",value = "肌酐 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalCre;

    /**
     * 钙 1-异常 0-正常
     */
    @ApiModelProperty(name = "abnormalCa",value = "钙 1-异常 0-正常",dataType = "java.lang.Integer")
    private Integer abnormalCa;
}