package com.cj.spension.controller;

import com.cj.common.domain.DoctorModel;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.spension.service.SPDoctorService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 医生信息管理
 * @date： 2019/3/8 16:04
 **/
@RestController
@RequestMapping("/s-pension/api/v1/doctor")
@Api(tags = "后台: 医生信息管理")
public class SPDoctorController {
    @Autowired
    private SPDoctorService doctorService;

    /**
     * 查询医生信息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询医生信息列表",response = DoctorModel.class)
    @GetMapping("/findPageDoctor")
    @ApiImplicitParam(name = "json" ,
            value ="province=省 city=市 area=区 hospitalId=医院表id departmentsId=科室id title=职称 " +
                    "sex=性别 男 女 sort=排序 0-咨询量 1-评价 2-综合 userType = 用户类型，1-用户，2-医生，3-专家",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"province\":1,\"city\":1,\"area\":1," +
                    "\"departmentsId\":1,\"hospitalId\":1,\"sort\":1," +
                    "\"title\":职称,\"sex\":\"男\",\"userType\":\"1\"}}")
    @Log(name = "医生信息管理",value = "查询医生账号信息列表")
    public ApiResult findPageDoctor(String json, HttpServletRequest request){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = doctorService.getDocPage(oldPager,request);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }

    /**
     * 根据ID查询医生信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询医生信息",response = DoctorModel.class)
    @GetMapping("/findDoctorByID")
    @Log(name = "医生信息管理",value = "查询医生信息")
    public ApiResult findDoctorByID(Long userId ,HttpServletRequest request){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(doctorService.getDocByID(userId,request));
        return apiResult;
    }
    /**
     * 修改信息
     * @param json
     * @return
     */
    @ApiOperation(value = "修改信息")
    @PutMapping("/updateInfo")
    @Log(name = "医生信息管理",value = "修改信息")
    public ApiResult updateInfo(@ApiParam(name = "json",value = "{\n" +
            "    \"doctorInfo\": {\n" +
            "      \"doctorInfoId\": 医生信息表id,\n" +
            "      \"userId\": 用户表id,\n" +
            "      \"hospitalId\": 医院id,\n" +
            "      \"departmentsId\": 科室id,\n" +
            "      \"name\": \"宋医生\",\n" +
            "      \"sex\": \"男\",\n" +
            "      \"age\": 21,\n" +
            "      \"title\": \"职称\",\n" +
            "      \"phone\": \"电话\",\n" +
            "      \"address\": \"住址\",\n" +
            "      \"briefIntroduction\": \"简介\",\n" +
            "      \"advantages\": \"擅长生病\",\n" +
            "      \"headUrl\": 头像地址,\n" +
            "      \"provinceId\": 省,\n" +
            "      \"cityId\": 市,\n" +
            "      \"areaId\": 区,\n" +
            "      \"idNumber\": \"身份证号\",\n" +
            "      \"idTerm\": 身份证有效期限,\n" +
            "      \"idJustUrl\": \"身份证正面\",\n" +
            "      \"idBackUrl\": \"身份证反面\",\n" +
            "      \"qualifications\": \"医师资格证号\"\n" +
            "    }\n" +
            "  }")
                                    @RequestBody String json){
        DoctorModel doctorModel = JsonUtil.gson.fromJson(json,DoctorModel.class);
        int i = doctorService.updateForInfo(doctorModel);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPass 新密码
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePass")
    @Log(name = "医生信息管理",value = "修改密码")
    public ApiResult updatePass(Long userId,String newPass){
        int i = doctorService.updateForPassByID(userId,newPass);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 删除账号 逻辑删除
     * @param list
     * @return
     */
    @ApiOperation(value = "删除医生账号")
    @DeleteMapping("/deleteUser")
    @Log(name = "医生信息管理",value = "删除账号")
    public ApiResult deleteUser(@RequestBody List<Long> list){
        int i = 0;
        for(Long userId : list){
            i += doctorService.deleteByID(userId);
        }
        if(i==list.size()){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 导出Excel
     * @param json
     * @return
     */
    @ApiOperation(value = "导出Excel")
    @GetMapping("/exportExcel")
    @ApiImplicitParam(name = "json" ,
            value ="province=省 city=市 area=区 hospitalId=医院表id departmentsId=科室id title=职称 " +
                    "sex=性别 男 女 sort=排序 0-咨询量 1-评价 2-综合 userType = 用户类型，1-用户，2-医生，3-专家",
            required = true,
            defaultValue = "{\"parameters\":{\"province\":1,\"city\":1,\"area\":1," +
                    "\"departmentsId\":1,\"hospitalId\":1,\"sort\":1," +
                    "\"title\":职称,\"sex\":\"男\",\"userType\":\"1\"}}")
    @Log(name = "医生信息管理",value = "导出Excel")
    public void exportExcel(String json
            , HttpServletRequest request
            ,HttpServletResponse response) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        doctorService.exportExcel(oldPager,response,request);
    }
}
