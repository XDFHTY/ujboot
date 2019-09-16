package com.cj.sconsult.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.sconsult.service.ConsultCallShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by JuLei on 2019/6/28.
 */
@Slf4j
@Component
public class ConsultCallShopServiceImpl implements ConsultCallShopService {
    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult findScore(long doctorId) {
        return restUtil.getJson("/s-shop/api/v2/orders/d/order/findScore/" + doctorId,null);
    }
}
