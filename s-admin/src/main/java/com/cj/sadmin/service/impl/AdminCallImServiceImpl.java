package com.cj.sadmin.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Customer;
import com.cj.core.exception.MyException;
import com.cj.core.util.rest.RestUtil;
import com.cj.sadmin.service.AdminCallImService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AdminCallImServiceImpl implements AdminCallImService {



    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult imRegister(Customer customer) {
        return restUtil.postJson("/s-im/api/v1/hxUser/register",customer);
//        throw new MyException("s-admin --> s-im");

    }

    @Override
    public ApiResult getCardInfo(Map map) {
        return restUtil.postJson("/s-im/api/v1/idCard/IDCardRecogize",map);
//        throw new MyException("s-admin --> s-im");
    }
}
