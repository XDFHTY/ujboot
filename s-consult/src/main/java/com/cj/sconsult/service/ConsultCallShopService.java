package com.cj.sconsult.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by XD on 2019/3/14.
 */
//@FeignClient(value = "s-user",fallback = ConsultCallUserServiceImpl.class)
public interface ConsultCallShopService {

    /**
     * 根据id评分和咨询量
     * @param
     * @return
     */
    @GetMapping(value = "/s-shop/api/v2/orders/d/order/findScore/{doctorId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult findScore( long doctorId);
}
