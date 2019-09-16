package com.cj.suser.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.exception.MyException;
import com.cj.core.util.rest.RestUtil;
import com.cj.suser.service.UserCallShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCallShopServiceImpl implements UserCallShopService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult addGood(Long id, Long roleId) {
        return restUtil.postJson("/s-shop/api/v2/goods/d/good/createGoods/"+id+"/"+roleId,null);
//        throw new MyException("s-user--->s-shop 失败");
    }

    @Override
    public ApiResult findScore(Long doctorId) {
        return restUtil.getJson("/s-shop/api/v2/orders/d/order/findScore/"+doctorId,null);
    }
}
