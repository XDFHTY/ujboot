package com.cj.sshop.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.Parameter;
import com.cj.core.util.rest.RestUtil;
import com.cj.sshop.service.ShopCallImService;
import org.springframework.stereotype.Component;

@Component
public class ShopCallImServiceImpl implements ShopCallImService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult userToadmin(String json) {
        Parameter parameter = new Parameter();
        parameter.setJson(json);
        return restUtil.getJson("/s-im/api/v1/msg/getRefundEligibility",parameter);
    }

}
