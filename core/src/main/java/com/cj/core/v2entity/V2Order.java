package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-07-15 09:44:11
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_order")
public class V2Order implements Serializable {
    /**
     * 订单表主键
     */
    @ApiModelProperty(name = "orderId",value = "订单表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "orderNo",value = "订单编号",dataType = "java.lang.String")
    private String orderNo;

    /**
     * 1-支付宝，2-微信，3-其他
     */
    @ApiModelProperty(name = "payType",value = "1-支付宝，2-微信，3-其他",dataType = "java.lang.String")
    private String payType;

    /**
     * 支付编号/退款编号
     */
    @ApiModelProperty(name = "payNo",value = "支付编号/退款编号",dataType = "java.lang.String")
    private String payNo;

    /**
     * 商品ID
     */
    @ApiModelProperty(name = "goodId",value = "商品ID",dataType = "java.lang.Long")
    private Long goodId;

    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "java.lang.String")
    private String goodType;

    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goodName",value = "商品名称",dataType = "java.lang.String")
    private String goodName;

    /**
     * 应付 分
     */
    @ApiModelProperty(name = "shouldPay",value = "应付 分",dataType = "java.lang.Integer")
    private Integer shouldPay;

    /**
     * 实付 分
     */
    @ApiModelProperty(name = "actualPay",value = "实付 分",dataType = "java.lang.Integer")
    private Integer actualPay;

    /**
     * 卖家ID
     */
    @ApiModelProperty(name = "sellerId",value = "卖家ID",dataType = "java.lang.Long")
    private Long sellerId;

    /**
     * 绑定的医生ID
     */
    @ApiModelProperty(name = "bindId",value = "绑定的医生ID",dataType = "java.lang.Long")
    private Long bindId;

    /**
     * 买家ID
     */
    @ApiModelProperty(name = "buyerId",value = "买家ID",dataType = "java.lang.Long")
    private Long buyerId;

    /**
     * 该订单平台获得金额 分
     */
    @ApiModelProperty(name = "biosGet",value = "该订单平台获得金额 分",dataType = "java.math.BigDecimal")
    private BigDecimal biosGet;

    /**
     * 下单时间
     */
    @ApiModelProperty(name = "createTime",value = "下单时间",dataType = "java.util.Date")
    private Date createTime;

    /**
     * 支付时间
     */
    @ApiModelProperty(name = "payTime",value = "支付时间",dataType = "java.util.Date")
    private Date payTime;

    /**
     * 服务订单到期时间
     */
    @ApiModelProperty(name = "expireTime",value = "服务订单到期时间",dataType = "java.util.Date")
    private Date expireTime;

    /**
     * 退款申请截止时间
     */
    @ApiModelProperty(name = "lastRefundTime",value = "退款申请截止时间",dataType = "java.util.Date")
    private Date lastRefundTime;

    /**
     * 1-未支付，2-已支付，3-已评价，4-已退款
     */
    @ApiModelProperty(name = "orderStatus",value = "1-未支付，2-已支付，3-已评价，4-已退款，5-已申请退款",dataType = "java.lang.String")
    private String orderStatus;

    /**
     * 退款时间
     */
    @ApiModelProperty(name = "refundTime",value = "退款时间",dataType = "java.util.Date")
    private Date refundTime;

    /**
     * 评分
     */
    @ApiModelProperty(name = "commentScore",value = "评分",dataType = "java.lang.Integer")
    private Integer commentScore;

    /**
     * 评分时间
     */
    @ApiModelProperty(name = "commentTime",value = "评分时间",dataType = "java.util.Date")
    private Date commentTime;

    private static final long serialVersionUID = 1L;
}