package com.cj.suser.controller;

import com.cj.core.entity.UserEquipment;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.suser.domain.VoUserEquipment;
import com.cj.suser.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/s-user/api/v1/equipment")
@Api(tags = "用户端: 用户设备")
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;

    //=========================================设备=========================================================

    //查询用户所有绑定的设备
    @GetMapping("/equipmentByUserId")
    @ApiOperation(value = "查询用户所有绑定的设备",response = VoUserEquipment.class)
    @Log(name = "用户设备",value = "查询用户所有绑定的设备")
    public ApiResult findAllEquipmentByUserId(HttpServletRequest request){

        return ResultUtil.result(equipmentService.findAllEquipmentByUserId(request));

    }


    //绑定设备
    @PostMapping("/equipmentByUserId")
    @ApiOperation("绑定设备")
    @Log(name = "用户设备",value = "绑定设备")
    public ApiResult addEquipmentByUserId(@ApiParam(name = "userEquipment",value = "设备信息,不传userID则从缓存取，类别-尿检机/手表",required = true)
                                              @RequestBody UserEquipment userEquipment,HttpServletRequest request){

        return ResultUtil.result(equipmentService.addEquipmentByUserId(userEquipment,request));
    }

    //解绑设备
    @PutMapping("/equipmentByUserId")
    @ApiOperation("解绑设备")
    @Log(name = "用户设备",value = "解绑设备")
    public ApiResult updateEquipmentByUserId(HttpServletRequest request,
                                             @ApiParam(name = "userEquipment",value = "只需要传设备绑定的主键ID=userBindEquipmentId",required = true)
                                             @RequestBody UserEquipment userEquipment
                                             ){
        return ResultUtil.result(equipmentService.updateEquipmentByUserId(request,userEquipment));
    }
}
