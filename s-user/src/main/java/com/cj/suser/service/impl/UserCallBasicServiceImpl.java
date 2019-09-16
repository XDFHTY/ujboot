package com.cj.suser.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.suser.service.UserCallBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCallBasicServiceImpl implements UserCallBasicService {

    RestUtil restUtil = new RestUtil();
    @Override
    public ApiResult userToBasic() {
        return restUtil.getJson("/s-basic/api/satx",null);
        //throw new MyException("s-user --> s-basic");
    }
}
