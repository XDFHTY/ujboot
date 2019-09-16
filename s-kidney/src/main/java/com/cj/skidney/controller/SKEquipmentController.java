package com.cj.skidney.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.skidney.service.SKEquipmentService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author： 刘磊
 * @Description: 设备管理
 * @date： 2019/3/14 18:09
 **/
@RestController
@RequestMapping("/s-kidney/api/v1/equipment")
@Api(tags = "后台: 设备管理")
public class SKEquipmentController {

    @Autowired
    private SKEquipmentService equipmentService;

    /**
     * 查询设备信息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询设备信息列表")
    @GetMapping("/findEquipmentModel")
    @ApiImplicitParam(name = "json",value = "province=省 city=市 area=区 sex=性别 男 女 " +
            "minAge=开始年龄 maxAge=结束年龄 minTime=开始时间 maxTime=结束时间 model=型号 " +
            " equipmentType = 设备类型",
            required = true,
    defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"province\":1," +
            "\"city\":1,\"area\":1,\"sex\":\"男\",\"minAge\":1," +
            "\"maxAge\":1,\"minTime\":2000-02-02,\"maxTime\":2000-02-02,\"model\":\"型号\"," +
            "\"equipmentType\":\"设备类型\"}}")
    @Log(name = "设备管理",value = "查询设备信息列表")
    public ApiResult findEquipmentModel(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = equipmentService.getEquipmentModelPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 查询设备历史绑定记录
     * @param json
     * @return
     */
    @ApiOperation(value = "查询设备历史绑定记录")
    @GetMapping("/findHistorical")
    @ApiImplicitParam(name = "json",value = "userId=用户id",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"userId\":1}}")
    @Log(name = "设备管理",value = "查询设备历史绑定记录")
    public ApiResult findHistorical(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = equipmentService.getHistoricalPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 导出设备信息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "导出设备信息列表")
    @GetMapping("/exportEquipmentModel")
    @ApiImplicitParam(name = "json",value = "province=省 city=市 area=区sex=性别 男 女 " +
            "minAge=开始年龄 maxAge=结束年龄 minTime=开始时间 maxTime=结束时间 model=型号 " +
            "equipmentType = 设备类型",
            required = true,
            defaultValue = "{\"parameters\":{\"province\":1," +
                    "\"city\":1,\"area\":1,\"sex\":\"男\",\"minAge\":1," +
                    "\"maxAge\":1,\"minTime\":2000-02-02,\"maxTime\":2000-02-02,\"model\":\"string\"," +
                    "\"equipmentType\":\"设备类型\"}}")
    @Log(name = "设备管理",value = "导出设备信息列表")
    public void exportEquipmentModel(String json,HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        ApiResult.SUCCESS();
        equipmentService.exportEquipmentModel(oldPager,response);
    }
    /**
     * 导出设备历史绑定记录
     * @param json
     * @return
     */
    @ApiOperation(value = "导出设备历史绑定记录")
    @GetMapping("/exportHistorical")
    @ApiImplicitParam(name = "json",value = "userId=用户i ",required = true,
            defaultValue = "{\"parameters\":{\"userId\":1}}")
    @Log(name = "设备管理",value = "导出设备历史绑定记录")
    public void exportHistorical(String json,HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        equipmentService.exportHistorical(oldPager,response);
    }

}
