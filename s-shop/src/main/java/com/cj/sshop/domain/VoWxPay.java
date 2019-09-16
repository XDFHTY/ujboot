package com.cj.sshop.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "微信下单返回")
public class VoWxPay {

    private String appid;
    private String nonce_str;
    private String sign;
    private String mch_id;
    private String trade_type;
    private String return_code;
    private String result_code;
    //下单参数
    private String return_msg;
    private String prepay_id;
    private String timestamp;


    //回调参数
    private String transaction_id;
    private String bank_type;
    private String openid;
    private String fee_type;
    private String cash_fee;
    private String out_trade_no;
    private String total_fee;
    private String time_end;
    private String is_subscribe;
    private String err_code;
    private String err_code_des;

    //退款参数
    private String out_refund_no;
    private String refund_fee;
}
