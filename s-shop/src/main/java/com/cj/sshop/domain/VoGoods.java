package com.cj.sshop.domain;

import com.cj.core.v2entity.V2Good;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VoGoods")
public class VoGoods extends V2Good {

    @ApiModelProperty(name = "sellerName",value = "卖家姓名",dataType = "java.lang.String")
    private String sellerName;


    @ApiModelProperty(name = "phone",value = "电话",dataType = "java.lang.String")
    private String phone;

}
