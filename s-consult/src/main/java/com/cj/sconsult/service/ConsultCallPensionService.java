package com.cj.sconsult.service;

import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by XD on 2019/3/14.
 */
//@FeignClient(value = "s-pension",fallback = ConsultCallPensionServiceImpl.class)
public interface ConsultCallPensionService {

    /**
     * 查询医生列表
     * @param
     * @return
     */
    @GetMapping(value = "/s-pension/api/v1/doctor/findPageDoctor",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult getInfo(@RequestParam(value = "json") String json, @RequestParam(value = "header") HttpServletRequest request);

    /**
     * 根据id查询医生信息
     * @param
     * @return
     */
    @GetMapping(value = "/s-pension/api/v1/doctor/findDoctorByID",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ApiResult findDoctorByID(@RequestParam(value = "userId") Long userId, @RequestParam(value = "request") HttpServletRequest request);
}
