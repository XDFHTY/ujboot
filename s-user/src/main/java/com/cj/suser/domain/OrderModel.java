package com.cj.suser.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author： 刘磊
 * @Description: 订单实体类
 * @date： 2019/6/28 17:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新增用户实体类")
@JsonIgnoreProperties(value = {"handler"})
public class OrderModel {
    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goodName",value = "商品名称",dataType = "java.lang.String")
    private String goodName;

    /**
     * 下单时间
     */
    @ApiModelProperty(name = "createTime",value = "下单时间",dataType = "java.util.Date")
    private Date createTime;

    /**
     * 服务订单到期时间
     */
    @ApiModelProperty(name = "expireTime",value = "服务订单到期时间",dataType = "java.util.Date")
    private Date expireTime;

    /**
     * 1-支付宝，2-微信，3-其他
     */
    @ApiModelProperty(name = "payType",value = "1-支付宝，2-微信，3-其他",dataType = "java.lang.String")
    private String payType;

    /**
     * 商品类型 1-健康管理，2-肾病管理，3-健康咨询，4-肾病咨询，5-营养咨询，6-其他咨询
     */
    @ApiModelProperty(name = "goodType",value = "商品类型 1-健康管理，2-肾病管理，3-健康咨询，4-肾病咨询，5-营养咨询，6-其他咨询",dataType = "java.lang.String")
    private String goodType;

    /**
     * 商品说明
     */
    @ApiModelProperty(name = "goodMsg",value = "商品说明",dataType = "java.lang.String")
    private String goodMsg;

}
