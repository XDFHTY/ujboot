package com.cj.suser.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "s-im",fallback = UserCallImServiceImpl.class)
public interface UserCallShopService {


    /**
     * 添加商品
     * @return
     */
    @PostMapping(value = "/s-shop/api/v2/d/good/createGoods/{id}/{roleId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult addGood(@PathVariable("id") Long id,@PathVariable("roleId") Long roleId);
    /**
     * 查询评分
     * @return
     */
    @GetMapping(value = "/s-shop/api/v2/orders/f/order/findScore/{doctorId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult findScore(@PathVariable("doctorId") Long doctorId);
}
