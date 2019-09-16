package com.cj.suser.controller;

import com.cj.core.entity.DoctorInfo;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.exception.MyException;
import com.cj.core.util.reg.RegexUtils;
import com.cj.suser.domain.VoDoctorInfo;
import com.cj.suser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/s-user/api/v2/doctorInfo")
@Api(tags = "医生端: 医生详情")
public class DoctorController {


    @Resource
    private UserService userService;


    //==============================================doctorInfo=========================================


    @GetMapping("/findDoctorInfo")
    @ApiOperation(value = "查询医生详情,根据token或doctorId,医生查询自己的不用传参,用户查询医生信息传ID",response = VoDoctorInfo.class)
    @Log(name = "医生详情", value = "查询医生详情")
    @ApiImplicitParam(name = "doctorId",value = "医生ID",required = false)
    public ApiResult findDoctorInfo(HttpServletRequest request,Long doctorId){
        return ResultUtil.result(userService.findDoctorInfo(request,doctorId));

    }



    @PostMapping("/addDoctorInfo")
    @ApiOperation(value = "医生、专家新增详情")
    @Log(name = "医生详情", value = "医生、专家新增详情")
    public ApiResult addDoctorInfo(@ApiParam(name = "voDoctorInfo",value = "医生、专家详情，新增字段userType必传，state必传",required = true)
                                   @RequestBody VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException {
        if (voDoctorInfo.getIdNumber()!=null){
            if (!RegexUtils.checkIdCard(voDoctorInfo.getIdNumber())){
                throw new MyException("身份证号错误");
            }
        }

        return ResultUtil.result(userService.addDoctorInfo(voDoctorInfo));
    }

    @PutMapping("/updateDoctorInfo")
    @ApiOperation(value = "医生、专家修改详情")
    @Log(name = "医生详情", value = "医生、专家修改详情")
    public ApiResult updateDockerInfo(@ApiParam(name = "voDoctorInfo",value = "医生、专家详情，state必传",required = true)
                                      @RequestBody VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException {
        if (voDoctorInfo.getIdNumber()!=null){
            if (!RegexUtils.checkIdCard(voDoctorInfo.getIdNumber())){
                throw new MyException("身份证号错误");
            }
        }

        return ResultUtil.result(userService.updateDoctorInfo(voDoctorInfo));
    }

//    @PutMapping("/submitCheck/{doctorId}")
//    @ApiOperation(value = "医生、专家提交审核")
//    @Log(name = "医生详情", value = "医生、专家提交审核")
//    public ApiResult submitCheck(@PathVariable Long doctorId){
//        return ResultUtil.result(userService.submitCheck(doctorId));
//    }

    @PutMapping("/updateHeadUrl")
    @ApiOperation(value = "医生修改头像")
    @Log(name = "医生详情", value = "医生修改头像")
    @ApiImplicitParam(name = "headUrl",value = "头像地址",required = true)
    public ApiResult updateUserHeadUrl(String headUrl,HttpServletRequest request){

        return ResultUtil.result(userService.updateDoctorHeadUrl(headUrl,request));
    }


}
