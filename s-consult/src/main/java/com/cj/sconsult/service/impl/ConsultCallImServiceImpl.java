package com.cj.sconsult.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.sconsult.service.ConsultCallImService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XD on 2019/4/10.
 */
@Component
public class ConsultCallImServiceImpl implements ConsultCallImService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult buildPushObjectAllAliasAlert(Map map) {
        //throw new MyException("s-im --> s-admin");

        return restUtil.postJson("/s-im/api/v1/jPush/buildPushObjectAllAliasAlert" ,map);
    }
}
