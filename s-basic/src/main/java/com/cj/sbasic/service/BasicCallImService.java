package com.cj.sbasic.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

//@FeignClient(value = "s-im",fallback = BasicCallImServiceImpl.class)
public interface BasicCallImService {

    @PostMapping(value = "/s-im/api/v1/jPush/buildPushObjectAllOrAndTagAlert",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult buildPushObjectAllOrAndTagAlert(@RequestBody Map map);
}
