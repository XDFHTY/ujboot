package com.cj.sim.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.sim.service.ImCallAdminService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XD on 2019/4/10.
 */
@Component
public class ImCallAdminServiceImpl implements ImCallAdminService {

    RestUtil restUtil = new RestUtil();

    @Override
    public ApiResult getInfo(Integer adminId) {
        //throw new MyException("s-im --> s-admin");

        return restUtil.getJson("/s-admin/api/v1/account/findAdminInfo/" + adminId,null);
    }
}
