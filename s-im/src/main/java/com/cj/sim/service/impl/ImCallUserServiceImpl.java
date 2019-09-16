package com.cj.sim.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.Parameter;
import com.cj.core.util.rest.RestUtil;
import com.cj.sim.service.ImCallUserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@Component
public class ImCallUserServiceImpl implements ImCallUserService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult getInfo(String json) {
       // throw new MyException("s-im --> s-user");
        Parameter parameter = new Parameter();
        parameter.setJson(json);
        return restUtil.getJson("/s-user/api/v2/user/getInfo",parameter);
    }
}
