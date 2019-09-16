package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/02
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "urine_result")
public class UrineResult {
    /**
     * 尿常规检查结果值
     */
    @ApiModelProperty(name = "urineResultId",value = "尿常规检查结果值",dataType = "java.lang.Long")
    private Long urineResultId;

    /**
     * 用户表id
     */
    @ApiModelProperty(name = "userId",value = "用户表id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 设备蓝牙地址
     */
    @ApiModelProperty(name = "bluetoothMac",value = "设备蓝牙地址",dataType = "java.lang.String")
    private String bluetoothMac;

    /**
     * 尿微量白蛋白肌酐比值
     */
    @ApiModelProperty(name = "resultAcr",value = "尿微量白蛋白肌酐比值",dataType = "java.lang.String")
    private String resultAcr;

    /**
     * 白细胞
     */
    @ApiModelProperty(name = "resultLeu",value = "白细胞",dataType = "java.lang.String")
    private String resultLeu;

    /**
     * 亚硝酸盐 阴性 阳性
     */
    @ApiModelProperty(name = "resultNit",value = "亚硝酸盐 阴性 阳性",dataType = "java.lang.String")
    private String resultNit;

    /**
     * 尿胆原
     */
    @ApiModelProperty(name = "resultUbg",value = "尿胆原",dataType = "java.lang.String")
    private String resultUbg;

    /**
     * 蛋白质
     */
    @ApiModelProperty(name = "resultPro",value = "蛋白质",dataType = "java.lang.String")
    private String resultPro;

    /**
     * PH值
     */
    @ApiModelProperty(name = "resultPh",value = "PH值",dataType = "java.lang.String")
    private String resultPh;

    /**
     * 潜血 0-阴性 1-阳性
     */
    @ApiModelProperty(name = "resultBld",value = "潜血  阴性  阳性",dataType = "java.lang.String")
    private String resultBld;

    /**
     * 比重
     */
    @ApiModelProperty(name = "resultSg",value = "比重",dataType = "java.lang.String")
    private String resultSg;

    /**
     * 酮体
     */
    @ApiModelProperty(name = "resultKet",value = "酮体",dataType = "java.lang.String")
    private String resultKet;

    /**
     * 胆红素
     */
    @ApiModelProperty(name = "resultBil",value = "胆红素",dataType = "java.lang.String")
    private String resultBil;

    /**
     * 葡萄糖  阴性  阳性
     */
    @ApiModelProperty(name = "resultGlu",value = "葡萄糖  阴性  阳性",dataType = "java.lang.String")
    private String resultGlu;

    /**
     * 维生素c
     */
    @ApiModelProperty(name = "resultVc",value = "维生素c",dataType = "java.lang.String")
    private String resultVc;

    /**
     * 微量白蛋白
     */
    @ApiModelProperty(name = "resultMa",value = "微量白蛋白",dataType = "java.lang.String")
    private String resultMa;

    /**
     * 肌酐
     */
    @ApiModelProperty(name = "resultCre",value = "肌酐",dataType = "java.lang.String")
    private String resultCre;

    /**
     * 钙
     */
    @ApiModelProperty(name = "resultCa",value = "钙",dataType = "java.lang.String")
    private String resultCa;

    /**
     * 检查时间
     */
    @ApiModelProperty(name = "inspectTime",value = "检查时间",dataType = "java.config.Date")
    private Date inspectTime;
}