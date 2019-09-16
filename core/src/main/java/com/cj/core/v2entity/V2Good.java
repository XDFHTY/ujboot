package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-21 16:16:06
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_good")
public class V2Good implements Serializable {
    /**
     * 商品表主键
     */
    @ApiModelProperty(name = "goodId",value = "商品表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long goodId;

    /**
     * 卖家id
     */
    @ApiModelProperty(name = "sellerId",value = "卖家id",dataType = "java.lang.Long")
    private Long sellerId;

    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goodName",value = "商品名称",dataType = "java.lang.String")
    private String goodName;

    /**
     * 商品说明
     */
    @ApiModelProperty(name = "goodMsg",value = "商品说明",dataType = "java.lang.String")
    private String goodMsg;

    /**
     * 商品价格
     */
    @ApiModelProperty(name = "goodPrice",value = "商品价格",dataType = "java.math.BigDecimal")
    private BigDecimal goodPrice;

    /**
     * 1-健康管理，2-肾病管理，3-健康咨询，4-肾病咨询，5-营养咨询，6-其他咨询
     */
    @ApiModelProperty(name = "goodType",value = "1-健康管理，2-肾病管理，3-健康咨询，4-肾病咨询，5-营养咨询，6-其他咨询",dataType = "java.lang.String")
    private String goodType;

    /**
     * 有效期（天）
     */
    @ApiModelProperty(name = "validDate",value = "有效期（天）",dataType = "java.lang.Integer")
    private Integer validDate;

    private static final long serialVersionUID = 1L;
}