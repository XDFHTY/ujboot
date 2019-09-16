package com.cj.suser.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(value = "s-admin",fallback = UserCallAdminServiceImpl.class)
public interface UserCallAdminService {


    @GetMapping(value = "/s-admin/api/satx",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult userToadmin();
}
