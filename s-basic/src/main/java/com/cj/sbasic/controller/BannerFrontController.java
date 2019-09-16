package com.cj.sbasic.controller;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Banner;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.BannerService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 刘磊
 * @Description: 轮播图用户
 * @date： 2019/3/8 10:32
 **/
@RestController
@RequestMapping("/s-basic/api/v1/banner")
@Api(tags = "用户端: 轮播图管理")
public class BannerFrontController {
    @Autowired
    private BannerService bannerService;

    /**
     * 分页查询轮播图列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询轮播图列表",response = Banner.class)
    @GetMapping("/findPageBannerFront")
    @ApiImplicitParam(name = "json" ,value = "bannerType=分类 1-个人端 2-医生端",required = true,
    defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"bannerType\":\"1\"}}")
    @Log(name = "轮播图管理",value = "用户端查询轮播图列表")
    public ApiResult findPageBannerFront(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = bannerService.getBarPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }

    /**
     * 根据id查询轮播图
     * @param bannerId
     * @return
     */
    @ApiOperation(value = "查询轮播图",response = Banner.class)
    @GetMapping("/findBannerByIDFront")
    @Log(name = "轮播图管理",value = "用户端查询轮播图")
    public ApiResult findBannerByIDFront(Long bannerId){
        Banner banner = bannerService.getBarByID(bannerId);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(banner);
        return apiResult;
    }

}
