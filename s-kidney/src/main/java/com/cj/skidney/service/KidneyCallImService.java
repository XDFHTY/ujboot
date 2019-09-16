package com.cj.skidney.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 调用推送
 * @date： 2019/4/10 16:31
 **/
//@FeignClient(value = "s-im",fallback = KidneyCallImServiceImpl.class)
public interface KidneyCallImService {
    /**
     * 推送异常信息给医生
     * @param map
     * @return
     */
    @PostMapping(value = "/s-im/api/v1/jPush/buildPushObjectAllAliasAlert",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult buildPushObjectAllAliasAlert(@RequestBody Map map);
    /**
     * 推送全部
     * @param map
     * @return
     */
    @PostMapping(value = "/s-im/api/v1/jPush/buildPushObjectAllAllAlert",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult buildPushObjectAllAllAlert(@RequestBody Map map);
}
