package com.cj.sshop.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "医生收益订单信息")
public class VoDoctorOrder {


    @ApiModelProperty(name = "userName",value = "用户姓名",dataType = "String")
    private String userName;

    @ApiModelProperty(name = "payTime",value = "支付时间",dataType = "Date")
    private Date payTime;

    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "String")
    private String goodType;

    @ApiModelProperty(name = "bindGet",value = "绑定对象获得",dataType = "Integer")
    private String bindGet;

}
