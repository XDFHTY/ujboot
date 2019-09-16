package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.common.domain.VoScore;
import com.cj.sshop.domain.VoDoctorOrder;
import com.cj.sshop.service.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RequestMapping("/s-shop/api/v2/orders/d/order")
@Api(tags = "医生端: 醫生订单")
@RestController
public class OrderDController {

    @Resource
    private UserOrderService userOrderService;

    @ApiOperation(value = "查询医生咨询量和评分,SC模块调用",response = VoScore.class)
    @GetMapping("/findScore/{doctorId}")
    @Log(name = "商品业务",value = "查询医生咨询量和评分")
    public ApiResult findScore(@PathVariable long doctorId){

        return ResultUtil.result(userOrderService.findScore(doctorId));
    }


    @ApiOperation(value = "查询医生订单收益",response = VoDoctorOrder.class)
    @GetMapping("/findDoctorOrder/{doctorId}")
    @ApiImplicitParam(name = "date",value = "时间",required = true)
    @Log(name = "商品业务",value = "查询医生订单收益")
    public ApiResult findDoctorOrder(@PathVariable long doctorId, Date date){

        return ResultUtil.result(1,userOrderService.findDoctorOrder(doctorId,date));
    }



}
