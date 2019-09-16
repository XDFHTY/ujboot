package com.cj.sim.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


/**
 * Created by XD on 2019/3/14.
 */
//@FeignClient(value = "s-consult",fallback = ImCallConsultServiceImpl.class)
public interface ImCallConsultService {

    /**
     * 根据id查询头像 昵称
     * @param
     * @return
     */
    @PostMapping(value = "/s-consult/api/v1/interrogation/insertEvaluate",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult insertEvaluate(@RequestBody Map map);
}
