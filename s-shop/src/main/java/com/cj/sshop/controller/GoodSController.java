package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2Good;
import com.cj.sshop.domain.VoGoods;
import com.cj.sshop.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/s-shop/api/v2/goods/f/good")
@Api(tags = "后台: 商品列表")
@RestController
public class GoodSController {

    @Resource
    private GoodService goodService;


    //分页查询所有商品
    @ApiOperation(value = "分页查询所有商品,後台使用",response = VoGoods.class)
    @GetMapping("/findAllGoods/{index}")
    @ApiImplicitParam(name = "name",value = "姓名",required = false)
    @Log(name = "商品业务",value = "分页查询所有商品")
    public ApiResult findAllGoods(@PathVariable long index,String name){

        Pager pager = new Pager();
        pager.setCurrent(index);
        pager.setParameter(name);

        return ResultUtil.result(goodService.findAllGoods(pager));
    }



    //修改商品价格
    @ApiOperation(value = "修改商品价格,後台使用")
    @PutMapping("/updateGoodPrice")
    @Log(name = "商品业务",value = "修改商品价格")
    public ApiResult updateGoodPrice(HttpServletRequest request,
                                     @ApiParam(name = "good",value = "商品id和价格必传,其它字段不传")
                                     @RequestBody V2Good good){

        return ResultUtil.result(goodService.updateGoodPrice(good));
    }

}
