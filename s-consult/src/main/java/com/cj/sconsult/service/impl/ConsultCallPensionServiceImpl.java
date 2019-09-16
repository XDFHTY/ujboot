package com.cj.sconsult.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.Parameter;
import com.cj.core.util.rest.RestUtil;
import com.cj.sconsult.service.ConsultCallPensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@Slf4j
@Component
public class ConsultCallPensionServiceImpl implements ConsultCallPensionService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult getInfo(String json, HttpServletRequest request) {
        Parameter parameter = new Parameter();
        parameter.setJson(json);
        return restUtil.getJson("/s-pension/api/v1/doctor/findPageDoctor",parameter);
        //throw new MyException("s-consult --> s-pension");
    }

    @Override
    public ApiResult findDoctorByID(Long userId,HttpServletRequest request) {
        return restUtil.getJson("/s-pension/api/v1/doctor/findDoctorByID?userId=" + userId,null);
        //throw new MyException("s-consult --> s-pension");
    }
}
