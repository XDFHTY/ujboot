package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sshop.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/s-shop/api/v2/goods/d/good")
@Api(tags = "医生端: 商品业务")
@RestController
public class GoodDController {

    @Resource
    private GoodService goodService;

    //医生、干部等添加扩展信息自动创建商品，为user模块提供服务
    @ApiOperation(value = "医生、干部等添加扩展信息自动创建商品")
    @PostMapping("/createGoods/{id}/{roleId}")
    @Log(name = "商品业务",value = "自动创建商品")
    public ApiResult createGoods(HttpServletRequest request, @PathVariable long id, @PathVariable long roleId){

        return ResultUtil.result(goodService.createGoods(id,roleId));
    }

    @ApiOperation(value = "医生、干部等查询自己的商品价格")
    @GetMapping("/findGoodById")
    @Log(name = "商品业务",value = "医生、干部等查询自己的商品价格")
    public ApiResult findGoodById(HttpServletRequest request){

        return ResultUtil.result(goodService.findGoodById(request));
    }

}
