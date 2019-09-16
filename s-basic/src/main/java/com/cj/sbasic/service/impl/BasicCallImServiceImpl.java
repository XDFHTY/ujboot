package com.cj.sbasic.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.sbasic.service.BasicCallImService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BasicCallImServiceImpl implements BasicCallImService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult buildPushObjectAllOrAndTagAlert(Map map) {
        return restUtil.postJson("/s-im/api/v1/jPush/buildPushObjectAllOrAndTagAlert",map);
        //throw new MyException("s-basic --> s-im");
    }
}
