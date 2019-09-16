package com.cj.skidney.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2BloodOxygen;
import com.cj.core.v2entity.V2HeartRate;
import com.cj.core.v2entity.V2Location;
import com.cj.skidney.service.V2RecordService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 2期检测记录管理
 * @date： 2019/6/29 16:15
 **/
@RestController
@RequestMapping("/s-kidney/api/v2/record")
@Api(tags = "公共接口:检查记录管理")
public class V2RecordController {

    @Autowired
    private V2RecordService v2RecordService;

    /**
     * 新增心率
     * @param list
     * @return
     */
    @PostMapping("/insertHeartRate")
    @ApiOperation(value = "新增心率", response = V2HeartRate.class)
    @Log(name = "检查记录管理", value = "新增心率")
    public ApiResult insertHeartRate(@ApiParam(name = "list",value = "新增心率",required = true)
                                         @RequestBody List<V2HeartRate> list){
        return ResultUtil.result(v2RecordService.insertHeartRate(list));
    }

    /**
     * 新增血氧
     * @param list
     * @return
     */
    @PostMapping("/insertBloodOxygen")
    @ApiOperation(value = "新增血氧", response = V2BloodOxygen.class)
    @Log(name = "检查记录管理", value = "新增血氧")
    public ApiResult insertBloodOxygen(@ApiParam(name = "list",value = "新增血氧",required = true)
                                     @RequestBody List<V2BloodOxygen> list){
        return ResultUtil.result(v2RecordService.insertBloodOxygen(list));
    }

    /**
     * 新增定位
     * @param list
     * @return
     */
    @PostMapping("/insertLocation")
    @ApiOperation(value = "新增定位", response = V2Location.class)
    @Log(name = "检查记录管理", value = "新增定位")
    public ApiResult insertLocation(@ApiParam(name = "list",value = "新增定位",required = true)
                                       @RequestBody List<V2Location> list){
        return ResultUtil.result(v2RecordService.insertLocation(list));
    }

    /**
     * 分页查询检查结果列表
     * @param parameters
     * @return
     */
    @ApiOperation(value = "分页查询检查结果列表")
    @GetMapping("/findRecordPage/{current}")
    @ApiImplicitParam(name = "parameters" ,value ="userId=用户id minTime=开始时间 maxTime=结束时间 " +
            "type = 类型 0-心率 1-血氧 2-定位 3-尿检 4-血压 5-血肌酐 6-24小时尿蛋白",
            required = true,
            defaultValue = "{\"userId\":\"1\",\"minTime\":\"2000-02-02 00:00:00\"," +
                    "\"maxTime\":\"2000-02-02 00:00:00\",\"type\":\"0\"}")
    @Log(name = "检查记录管理",value = "分页查询检查结果列表")
    public ApiResult findRecordPage(String parameters,@PathVariable int current) {
        Pager pager = new Pager();
        pager.setCurrent(current);
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        pager.setParameters(map);
        Pager page = v2RecordService.findRecordPage(pager);
        if(page==null){
            return ResultUtil.result("type不正确");
        }else {
            return ResultUtil.result(page);
        }
    }

    /**
     * 查询检查结果列表
     * @param parameters
     * @return
     */
    @ApiOperation(value = "查询检查结果列表")
    @GetMapping("/findRecord")
    @ApiImplicitParam(name = "parameters" ,value ="userId=用户id minTime=开始时间 maxTime=结束时间 " +
            "type = 类型 0-心率 1-血氧 2-定位",
            required = true,
            defaultValue = "{\"userId\":\"1\",\"minTime\":\"2000-02-02 00:00:00\"," +
                    "\"maxTime\":\"2000-02-02 00:00:00\",\"type\":\"0\"}")
    @Log(name = "检查记录管理",value = "查询检查结果列表")
    public ApiResult findRecord(String parameters) {
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        List list = v2RecordService.findRecord(map);
        if(list==null){
            return ResultUtil.result("type不正确");
        }else {
            return ResultUtil.result(list);
        }
    }
}
