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
@ApiModel(value = "urine_identification")
public class UrineIdentification {
    /**
     * 尿常规检查本机标识
     */
    @ApiModelProperty(name = "urineIdentificationId",value = "尿常规检查本机标识",dataType = "java.lang.Long")
    private Long urineIdentificationId;

    /**
     * 尿常规检查结果表
     */
    @ApiModelProperty(name = "urineResultId",value = "尿常规检查结果表",dataType = "java.lang.Long")
    private Long urineResultId;

    /**
     * 尿微量白蛋白肌酐比值
     */
    @ApiModelProperty(name = "identificationAcr",value = "尿微量白蛋白肌酐比值",dataType = "java.lang.String")
    private String identificationAcr;

    /**
     * 白细胞
     */
    @ApiModelProperty(name = "identificationLeu",value = "白细胞",dataType = "java.lang.String")
    private String identificationLeu;

    /**
     * 亚硝酸盐
     */
    @ApiModelProperty(name = "identificationNit",value = "亚硝酸盐",dataType = "java.lang.String")
    private String identificationNit;

    /**
     * 尿胆原
     */
    @ApiModelProperty(name = "identificationUbg",value = "尿胆原",dataType = "java.lang.String")
    private String identificationUbg;

    /**
     * 蛋白质
     */
    @ApiModelProperty(name = "identificationPro",value = "蛋白质",dataType = "java.lang.String")
    private String identificationPro;

    /**
     * PH值
     */
    @ApiModelProperty(name = "identificationPh",value = "PH值",dataType = "java.lang.String")
    private String identificationPh;

    /**
     * 潜血
     */
    @ApiModelProperty(name = "identificationBld",value = "潜血",dataType = "java.lang.String")
    private String identificationBld;

    /**
     * 比重
     */
    @ApiModelProperty(name = "identificationSg",value = "比重",dataType = "java.lang.String")
    private String identificationSg;

    /**
     * 酮体
     */
    @ApiModelProperty(name = "identificationKet",value = "酮体",dataType = "java.lang.String")
    private String identificationKet;

    /**
     * 胆红素
     */
    @ApiModelProperty(name = "identificationBil",value = "胆红素",dataType = "java.lang.String")
    private String identificationBil;

    /**
     * 葡萄糖
     */
    @ApiModelProperty(name = "identificationGlu",value = "葡萄糖",dataType = "java.lang.String")
    private String identificationGlu;

    /**
     * 维生素c
     */
    @ApiModelProperty(name = "identificationVc",value = "维生素c",dataType = "java.lang.String")
    private String identificationVc;

    /**
     * 微量白蛋白
     */
    @ApiModelProperty(name = "identificationMa",value = "微量白蛋白",dataType = "java.lang.String")
    private String identificationMa;

    /**
     * 肌酐
     */
    @ApiModelProperty(name = "identificationCre",value = "肌酐",dataType = "java.lang.String")
    private String identificationCre;

    /**
     * 钙
     */
    @ApiModelProperty(name = "identificationCa",value = "钙",dataType = "java.lang.String")
    private String identificationCa;
}