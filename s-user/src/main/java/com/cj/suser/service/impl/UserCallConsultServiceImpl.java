package com.cj.suser.service.impl;

import com.cj.common.domain.BindDoctorVo;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.suser.service.UserCallConsultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/6/28 15:19
 **/
@Slf4j
@Component
public class UserCallConsultServiceImpl implements UserCallConsultService {
    RestUtil restUtil = new RestUtil();
    @Override
    public ApiResult userBindDoctor(BindDoctorVo bindDoctorVo) {
        return restUtil.postJson("/s-consult/api/v1/doctor/userBindDoctor",bindDoctorVo);
    }
}
