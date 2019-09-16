package com.cj.sadmin.controller;

import com.cj.common.service.AppService;
import com.cj.common.utils.ResultUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.entity.AppVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/s-admin/api/v1/version")
@Api(tags = "后台: APP版本管理")
public class AppVersionController {

    @Resource
    private AppService appService;

    @GetMapping("/find")
    @ApiOperation("查询app列表")
    public ApiResult findApp(){

        return ResultUtil.result(appService.findApp());
    }

    @PostMapping("/add")
    @ApiOperation("发布新版本app,versioncode+type联合唯一,时间字段可以不传，默认取当前时间")
    public ApiResult addApp(@RequestBody AppVersion appVersion){

        return ResultUtil.result(appService.addApp(appVersion));
    }

    @DeleteMapping("/del")
    @ApiOperation("删除APP,根据URL")
    public ApiResult delApp(String url){

        return ResultUtil.result(appService.delApp(url));
    }


}
