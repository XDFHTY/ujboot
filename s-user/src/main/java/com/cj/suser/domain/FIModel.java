package com.cj.suser.domain;

import com.cj.core.entity.FallIllImg;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 疾病实体类
 * @date： 2019/3/28 18:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "疾病实体类")
@JsonIgnoreProperties(value = {"handler"})
public class FIModel implements Serializable {
    /**
     * 生病信息id
     */
    @ApiModelProperty(name = "fallIllId",value = "生病信息id",dataType = "java.lang.Long")
    private Long fallIllId;
    /**
     * 病名
     */
    @ApiModelProperty(name = "disease",value = "病名",dataType = "java.lang.String")
    private String diseaseF;
    /**
     * 医院
     */
    @ApiModelProperty(name = "hospital",value = "医院",dataType = "java.lang.String")
    private String hospitalF;
    /**
     * 高血压0-否 1-是
     */
    @ApiModelProperty(name = "hypertension",value = "高血压0-否 1-是",dataType = "java.lang.String")
    private String hypertension;

    /**
     * 过敏史0-无 1-有
     */
    @ApiModelProperty(name = "allergy",value = "过敏史0-无 1-有",dataType = "java.lang.String")
    private String allergy;

    /**
     * 慢性病类型
     */
    @ApiModelProperty(name = "chronic",value = "慢性病类型",dataType = "java.lang.String")
    private String chronic;

    /**
     * 肾病类型
     */
    @ApiModelProperty(name = "nephropathyType",value = "肾病类型",dataType = "java.lang.String")
    private String nephropathyType;

    /**
     * 病情描述
     */
    @ApiModelProperty(name = "fallMsg",value = "病情描述",dataType = "java.lang.String")
    private String fallMsg;

    /**
     * 检查时间
     */
    @ApiModelProperty(name = "checkTime",value = "检查时间",dataType = "java.config.Date")
    private Date checkTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "addTime",value = "创建时间",dataType = "java.config.Date")
    private Date addTime;
    /**
     * 检测图片实体类
     */
    @ApiModelProperty(name = "fiiList",value = "检测图片实体类")
    private List<FallIllImg> fiiList;
}
