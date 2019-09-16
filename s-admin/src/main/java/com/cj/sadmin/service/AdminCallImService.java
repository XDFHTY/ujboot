package com.cj.sadmin.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Customer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

//@FeignClient(value = "s-im",fallback = AdminCallImServiceImpl.class)
public interface AdminCallImService {


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
}
