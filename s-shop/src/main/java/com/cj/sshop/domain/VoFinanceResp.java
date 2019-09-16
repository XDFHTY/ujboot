package com.cj.sshop.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "财务管理返回实体类")
public class VoFinanceResp {


    @ApiModelProperty(name = "totalProfit",value = "总收益",dataType = "String")
    private String totalProfit;


    @ApiModelProperty(name = "boisProfit",value = "平台收益",dataType = "String")
    private String biosProfit;

    @ApiModelProperty(name = "bindProfit",value = "绑定者收益",dataType = "String")
    private String bindProfit;


    @ApiModelProperty(name = "voFinanceOrders",value = "财务订单类",dataType = "VoFinanceOrder")
    List<VoFinanceOrder> voFinanceOrders;

}
