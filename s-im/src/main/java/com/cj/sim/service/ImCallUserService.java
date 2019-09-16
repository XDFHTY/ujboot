package com.cj.sim.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by XD on 2019/3/14.
 */
//@FeignClient(value = "s-user",fallback = ImCallUserServiceImpl.class)
public interface ImCallUserService {

    /**
     * 根据id查询头像 昵称
     * @param
     * @return
     */
    @GetMapping(value = "/s-user/api/v2/user/getInfo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult getInfo(@RequestParam("json") String json);
}
