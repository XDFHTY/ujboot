package com.cj.sconsult.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.Parameter;
import com.cj.core.util.rest.RestUtil;
import com.cj.sconsult.service.ConsultCallUserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@Component
public class ConsultCallUserServiceImpl implements ConsultCallUserService {


    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult getInfo(String json) {
        Parameter parameter = new Parameter();
        parameter.setJson(json);
        return restUtil.getJson("/s-user/api/v2/user/getInfo",parameter);
        //throw new MyException("s-consult --> s-user");
    }
}
