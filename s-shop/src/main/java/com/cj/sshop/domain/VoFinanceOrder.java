package com.cj.sshop.domain;


import com.cj.core.domain.ApiResult;
import com.cj.core.v2entity.V2Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "财务订单")
public class VoFinanceOrder {


    @ApiModelProperty(name = "userName",value = "用户姓名",dataType = "String")
    private String userName;

    @ApiModelProperty(name = "userPhone",value = "用户电话",dataType = "String")
    private String userPhone;

    @ApiModelProperty(name = "bindName",value = "绑定对象姓名",dataType = "String")
    private String bindName;

    @ApiModelProperty(name = "bindPhone",value = "绑定对象电话",dataType = "String")
    private String bindPhone;

    @ApiModelProperty(name = "orderNo",value = "订单号",dataType = "String")
    private String orderNo;

    @ApiModelProperty(name = "payTime",value = "支付时间",dataType = "Date")
    private Date payTime;

    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "String")
    private String goodType;

    @ApiModelProperty(name = "shouldPay",value = "应付",dataType = "Integer")
    private String shouldPay;

    @ApiModelProperty(name = "actualPay",value = "实付",dataType = "Integer")
    private String actualPay;

    @ApiModelProperty(name = "biosGet",value = "平台获得",dataType = "Integer")
    private String biosGet;

    @ApiModelProperty(name = "bindGet",value = "绑定对象获得",dataType = "Integer")
    private String bindGet;



}
