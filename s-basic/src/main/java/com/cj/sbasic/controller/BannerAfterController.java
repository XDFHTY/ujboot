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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 轮播图 管理员
 * @date： 2019/3/8 10:32
 **/
@RestController
@RequestMapping("/s-basic/api/v1/banner")
@Api(tags = "后台: 轮播图管理")
public class BannerAfterController {
    @Autowired
    private BannerService bannerService;

    /**
     * 分页查询轮播图列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询轮播图列表",response = Banner.class)
    @GetMapping("/findPageBannerAfter")
    @ApiImplicitParam(name = "json" ,value = "bannerType=分类 1-个人端 2-医生端",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"bannerType\":\"1\"}}")
    @Log(name = "轮播图管理",value = "管理端查询轮播图列表")
    public ApiResult findPageBannerAfter(String json){
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
    @GetMapping("/findBannerByIDAfter")
    @Log(name = "轮播图管理",value = "管理端查询轮播图")
    public ApiResult findBannerByIDAfter(Long bannerId){
        Banner banner = bannerService.getBarByID(bannerId);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(banner);
        return apiResult;
    }

    /**
     * 新增
     * @param json
     * @return
     */
    @ApiOperation(value = "新增轮播图",response = Banner.class)
    @PostMapping("/insert")
    @ApiImplicitParam(name = "json" ,value = "createTime=创建时间 url=跳转地址 bannerImgUrl=图片路径 bannerType=分类 1-个人端 2-医生端",required = true,
            defaultValue = "{\"createTime\":2000-02-02,\"url\":跳转地址,\"bannerImgUrl\":图片路径,\"bannerType\":1}")
    @Log(name = "轮播图管理",value = "新增轮播图")
    public ApiResult insert(@RequestBody Banner json){
//        Banner banner = JsonUtil.gson.fromJson(json,Banner.class);
        json.setBannerState("1");
        int i = bannerService.insert(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 删除
     * @param list
     * @return
     */
    @ApiOperation(value = "删除轮播图",response = Banner.class)
    @DeleteMapping("/delete")
    @Log(name = "轮播图管理",value = "删除轮播图")
    public ApiResult delete(@RequestBody List<Long> list){
        int i = 0;
        for(Long bannerId:list){
            i += bannerService.delete(bannerId);
        }
        if(i==list.size()){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 修改
     * @param json
     * @return
     */
    @ApiOperation(value = "修改轮播图",response = Banner.class)
    @PutMapping("/uqdate")
    @ApiImplicitParam(name = "json" ,value = "bannerId=id url=跳转地址 bannerImgUrl=图片路径 bannerType=分类 1-个人端 2-医生端",required = true,
            defaultValue = "{\"bannerId\":1,\"url\":跳转地址,\"bannerImgUrl\":图片路径,\"bannerType\":1}")
    @Log(name = "轮播图管理",value = "修改轮播图")
    public ApiResult uqdate(@RequestBody Banner json){
//        Banner banner = JsonUtil.gson.fromJson(json,Banner.class);
        int i = bannerService.update(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

}
