package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-21 16:16:06
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_ratio")
public class V2Ratio implements Serializable {
    /**
     * 商品抽成记录表主键
     */
    @ApiModelProperty(name = "ratioId",value = "商品抽成记录表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long ratioId;

    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "java.lang.String")
    private String goodType;

    /**
     * 抽成比例，千分比
     */
    @ApiModelProperty(name = "ratioNum",value = "抽成比例，千分比",dataType = "java.lang.Integer")
    private Integer ratioNum;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createtime",value = "创建时间",dataType = "java.util.Date")
    private Date createtime;

    /**
     * 0-已删除，1-使用中
     */
    @ApiModelProperty(name = "state",value = "0-已删除，1-使用中",dataType = "java.lang.String")
    private String state;

    private static final long serialVersionUID = 1L;
}