package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-21 16:16:06
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_give")
public class V2Give implements Serializable {
    /**
     * 赠送用户的0元支付记录表主键
     */
    @ApiModelProperty(name = "giveId",value = "赠送用户的0元支付记录表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long giveId;

    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户id",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 3-健康咨询，4-肾病咨询，5-营养咨询
     */
    @ApiModelProperty(name = "goodType",value = "3-健康咨询，4-肾病咨询，5-营养咨询",dataType = "java.lang.String")
    private String goodType;

    /**
     * 赠送次数，正数，消耗次数，负数
     */
    @ApiModelProperty(name = "giveNum",value = "赠送次数，正数，消耗次数，负数",dataType = "java.lang.Integer")
    private Integer giveNum;

    /**
     * 对应的订单ID
     */
    @ApiModelProperty(name = "orderId",value = "对应的订单ID",dataType = "java.lang.Long")
    private Long orderId;
    /**
     * 对应的订单编号
     */
    @ApiModelProperty(name = "orderNo",value = "对应的订单编号",dataType = "java.lang.String")
    private String orderNo;

    private static final long serialVersionUID = 1L;
}