package com.cj.suser.service;

import com.cj.core.domain.Customer;
import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

//@FeignClient(value = "s-im",fallback = UserCallImServiceImpl.class)
public interface UserCallImService {

    /**
     * 向环信注册
     * @param customer
     * @return
     */
    @PostMapping(value = "/s-im/api/v1/hxUser/register",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult imRegister(@RequestBody Customer customer);


    /**
     * 身份证识别
     * @param map
     * @return
     */
    @PostMapping(value = "/s-im/api/v1/idCard/IDCardRecogize",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult getCardInfo(@RequestBody Map map);


    /**
     * 按别名 发送通知 和 参数
     * @param map days   String
     * @param map params map
     * @param map alert  String
     * @param map alias  List
     * @return
     */
    @PostMapping(value = "/s-im/api/v1/jPush/buildPushObjectAllAliasAlert",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult buildPushObjectAllAliasAlert(@RequestBody Map map);


}
