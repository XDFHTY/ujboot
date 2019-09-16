package com.cj.sim.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by XD on 2019/4/10.
 */
//@FeignClient(value = "s-admin",fallback = ImCallAdminServiceImpl.class)
public interface ImCallAdminService {

    /**
     * 根据id查询头像 昵称
     *
     * @param
     * @return
     */
    @GetMapping(value = "/s-admin/api/v1/account/findAdminInfo/{adminId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult getInfo(@PathVariable("adminId") Integer adminId);
}
