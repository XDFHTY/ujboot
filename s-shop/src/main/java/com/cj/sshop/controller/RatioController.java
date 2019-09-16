package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.v2entity.V2Ratio;
import com.cj.sshop.service.RatioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/s-shop/api/v2/goods/ratio")
@Api(tags = "后台: 服务设置")
@RestController
public class RatioController {

    @Resource
    private RatioService ratioService;

    //分页查询所有商品
    @ApiOperation(value = "查询所有服务,後台使用",response = V2Ratio.class)
    @GetMapping("/findAllRatios")
    @Log(name = "服务设置",value = "查询所有服务")
    public ApiResult findAllRatios(){


        return ResultUtil.result(ratioService.findAllRatios());
    }



    //修改商品价格
    @ApiOperation(value = "修改商品价格,後台使用")
    @PutMapping("/updateRatio")
    @Log(name = "商品业务",value = "修改商品价格")
    public ApiResult updateRatio(HttpServletRequest request,
                                 @ApiParam(name = "ratio",value = "id和比例和goodtype必传,其它字段不传")
                                     @RequestBody V2Ratio ratio){

        return ResultUtil.result(ratioService.updateRatio(ratio));
    }

}
