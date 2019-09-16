package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.entity.DoctorInfo;
import com.cj.sshop.domain.VoFinanceResp;
import com.cj.sshop.service.FinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RequestMapping("/s-shop/api/v2/orders/finance")
@Api(tags = "后台: 财务管理")
@RestController
public class FinanceController {

    @Resource
    private FinanceService financeService;

    @ApiOperation(value = "模糊查询人员",response = DoctorInfo.class)
    @GetMapping("/findAllByName/{pameter}")
    @Log(name = "财务管理",value = "模糊查询人员")
    public ApiResult findAllByName(@PathVariable String pameter){

        return ResultUtil.result(financeService.findAllByName(pameter));

    }

    @ApiOperation(value = "财务查询,date=yyyy-MM,incomeType=0(总收益)/1（管理服务）/2（咨询服务）",response = VoFinanceResp.class)
    @GetMapping("/findAllOrder/{date}/{incomeType}/{bindId}")
    @Log(name = "财务管理",value = "财务查询")
    public ApiResult findAllOrder(@PathVariable String date,@PathVariable String incomeType,@PathVariable long bindId){
        Date dateTime = DateUtil.strToDate(date,DateUtil.YYYY_MM_DDHHMMSS);

        return ResultUtil.result(1,financeService.findAllOrder(dateTime,incomeType,bindId));

    }



}
