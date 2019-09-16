package com.cj.sim.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.sim.service.ImCallConsultService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@Component
public class ImCallConsultServiceImpl implements ImCallConsultService {

    RestUtil restUtil = new RestUtil();
    @Override
    public ApiResult insertEvaluate(Map map) {
        return restUtil.postJson("/s-consult/api/v1/interrogation/insertEvaluate",map);
        //throw new MyException("s-im --> s-consult");
    }
}
