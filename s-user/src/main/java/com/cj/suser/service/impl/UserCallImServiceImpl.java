package com.cj.suser.service.impl;

import com.cj.core.domain.Customer;
import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.suser.service.UserCallImService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class UserCallImServiceImpl implements UserCallImService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult imRegister(Customer customer) {
       // throw new MyException("s-user --> s-im");
        return restUtil.postJson("/s-im/api/v1/hxUser/register",customer);
    }

    @Override
    public ApiResult getCardInfo(Map map) {

        //throw new MyException("s-user --> s-im");
        return restUtil.postJson("/s-im/api/v1/idCard/IDCardRecogize",map);
    }

    @Override
    public ApiResult buildPushObjectAllAliasAlert(Map map) {
        //throw new MyException("s-user --> s-im");
        return restUtil.postJson("/s-im/api/v1/jPush/buildPushObjectAllAliasAlert",map);
    }
}
