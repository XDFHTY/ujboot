package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sshop.domain.VoFindGood;
import com.cj.sshop.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/s-shop/api/v2/goods/u/good")
@Api(tags = "用户端: 商品查询")
@RestController
public class GoodUController {

    @Resource
    private GoodService goodService;


    //用户查询可以购买的商品信息
    @ApiOperation(value = "用户查询可以购买的商品信息",response = VoFindGood.class)
    @GetMapping("/findGood/{doctorId}/{goodType}")
    @Log(name = "商品业务",value = "用户查询商品信息")
    @ApiImplicitParam(name = "goodType",value = "1-健康管理包，2-肾病管理包，3-健康咨询，4-肾病咨询，5-营养咨询，6-其他咨询")
    public ApiResult findGood(HttpServletRequest request,@PathVariable long doctorId,
                                      @PathVariable String goodType){

        long id = Long.parseLong(request.getHeader("id"));
        return ResultUtil.result(1,goodService.findGood(id,doctorId,goodType));
    }

}
