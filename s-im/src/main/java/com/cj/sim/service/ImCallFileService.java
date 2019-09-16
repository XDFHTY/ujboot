package com.cj.sim.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * Created by XD on 2019/3/14.
 */
//@FeignClient(value = "s-user",fallback = ImCallUserServiceImpl.class)
public interface ImCallFileService {

    /**
     * 根据id查询头像 昵称
     * @param
     * @return
     */
    @GetMapping(value = "/s-file/api/file/hxUploads",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult downloadFile(@RequestParam("json") Map map);
}
