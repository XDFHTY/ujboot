package com.cj.sshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoPayNo {

    //支付方式
    private String payType;

    //商户订单号
    private String outTradeNo;

    //交易状态
    private String tradeStatus;

    //交易单号
    private String tradeNo;

    //支付时间
    private String gmtPayment;


}
