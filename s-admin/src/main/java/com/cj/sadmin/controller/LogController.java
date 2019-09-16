package com.cj.sadmin.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sadmin.domain.VoLog;
import com.cj.sadmin.service.LogService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/s-admin/api/v1/log/log")
@Api(tags = "后台: 系统管理-日志模块")
public class LogController {

    Gson gson = JsonUtil.gson;

    @Resource
    private LogService logService;

    @GetMapping("/{currentPage}/{pageSize}")
    @ApiOperation(value = "分页查询操作日志",response = VoLog.class)
    @Log(name = "日志模块",value = "分页查询操作日志")
    @ApiImplicitParam(name = "adminId",value = "管理员ID",required = false)
    public ApiResult findLog(@PathVariable int currentPage, @PathVariable int pageSize,Long adminId){
        OldPager oldPager = new OldPager();
        oldPager.setCurrentPage(currentPage);
        oldPager.setPageSize(pageSize);
        oldPager.setParameter(adminId);
        return ResultUtil.result(logService.findLogs(oldPager));
    }
}
