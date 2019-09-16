package com.cj.sbasic.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.CommService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author： 刘磊
 * @Description: 医院、科室、疾病
 * @date： 2019/3/20 15:08
 **/
@RestController
@RequestMapping("/s-basic/api/v1/comcon")
@Api(tags = "后台: 医院、科室、疾病")
public class CommController {
    @Autowired
    private CommService commService;

    /**
     * 添加科室
     * @param json
     * @return
     */
    @ApiOperation(value = "添加科室",response = Department.class)
    @PostMapping("/insertDepartment")
    @Log(name = "医院、科室、疾病",value = "添加科室")
    public ApiResult insertDepartment(@ApiParam(name = "json",value = "{\n" +
            "      \"departmentId\": 12,\n" +
            "      \"departmentName\": \"消化科\",\n" +
            "      \"isDisplay\": \"1\"\n" +
            "    }")@RequestBody Department json){
//        Department department = JsonUtil.gson.fromJson(json,Department.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.insertDepar(json));
        return apiResult;
    }

    /**
     * 修改科室
     * @param json
     * @return
     */
    @ApiOperation(value = "修改科室",response = Department.class)
    @PutMapping("/updateDepartment")
    @Log(name = "医院、科室、疾病",value = "修改科室")
    public ApiResult updateDepartment(@ApiParam(name = "json",value = "{\n" +
            "      \"departmentId\": 12,\n" +
            "      \"departmentName\": \"消化科\",\n" +
            "      \"isDisplay\": \"1\"\n" +
            "    }")@RequestBody Department json){
//        Department department = JsonUtil.gson.fromJson(json,Department.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.updateDepar(json));
        return apiResult;
    }

    /**
     * 删除科室
     * @param departmentId
     * @return
     */
    @ApiOperation(value = "删除科室")
    @DeleteMapping("/deleteDepartment")
    @Log(name = "医院、科室、疾病",value = "删除科室")
    public ApiResult deleteDepartment(Long departmentId){
        ApiResult apiResult = ApiResult.SUCCESS();

            return ResultUtil.result(commService.deleteDepar(departmentId));
    }

    /**
     * 添加疾病
     * @param json
     * @return
     */
    @ApiOperation(value = "添加疾病",response = Disease.class)
    @PostMapping("/insertDisease")
    @Log(name = "医院、科室、疾病",value = "添加疾病")
    public ApiResult insertDisease(@ApiParam(name = "json",value = "{\n" +
            "      \"diseaseId\": 3,\n" +
            "      \"departmentId\": 12,\n" +
            "      \"diseaseName\": \"肠胃病\",\n" +
            "      \"isDisplay\": \"1\"\n" +
            "    }")@RequestBody Disease json){
//        Disease disease = JsonUtil.gson.fromJson(json,Disease.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.insretDisease(json));
        return apiResult;
    }

    /**
     * 修改疾病
     * @param json
     * @return
     */
    @ApiOperation(value = "修改疾病",response = Disease.class)
    @PutMapping("/updateDisease")
    @Log(name = "医院、科室、疾病",value = "修改疾病")
    public ApiResult updateDisease(@ApiParam(name = "json",value = "{\n" +
            "      \"diseaseId\": 3,\n" +
            "      \"departmentId\": 12,\n" +
            "      \"diseaseName\": \"肠胃病\",\n" +
            "      \"isDisplay\": \"1\"\n" +
            "    }")@RequestBody Disease json){
//        Disease disease = JsonUtil.gson.fromJson(json,Disease.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.updateDisease(json));
        return apiResult;
    }

    /**
     * 删除疾病
     * @param diseaseId
     * @return
     */
    @ApiOperation(value = "删除疾病")
    @DeleteMapping("/deleteDisease")
    @Log(name = "医院、科室、疾病",value = "删除疾病")
    public ApiResult deleteDisease(Long diseaseId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.deleteDisease(diseaseId));
        return apiResult;
    }
    /**
     * 查询医院列表
     * @return
     */
    @ApiOperation(value = "查询医院列表",response = Hospital.class)
    @GetMapping("/findHospital")
    @ApiImplicitParam(name = "json" ,value ="provinceId=省id cityId=市id areaId=区id",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"provinceId\":1,\"cityId\":10,\"areaId\":1}}")
    @Log(name = "医院、科室、疾病",value = "查询医院列表")
    public ApiResult findHospital(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.getHospitalByRegion(oldPager));
        return apiResult;
    }
    /**
     * 添加医院
     * @param json
     * @return
     */
    @ApiOperation(value = "添加医院",response = Hospital.class)
    @PostMapping("/insertHospital")
    @Log(name = "医院、科室、疾病",value = "添加医院")
    public ApiResult insertHospital(@ApiParam(name = "json",value = "{\n" +
            "      \"hospitalId\": 1,\n" +
            "      \"hospitalName\": \"医院2名字\",\n" +
            "      \"provinceId\": 1,\n" +
            "      \"cityId\": 1,\n" +
            "      \"areaId\": 1,\n" +
            "      \"address\": \"医院详细地址\"\n" +
            "    }")@RequestBody Hospital json){
//        Hospital hospital = JsonUtil.gson.fromJson(json,Hospital.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.insertHospital(json));
        return apiResult;
    }

    /**
     * 修改医院
     * @param json
     * @return
     */
    @ApiOperation(value = "修改医院",response = Hospital.class)
    @PutMapping("/updateHospital")
    @Log(name = "医院、科室、疾病",value = "修改医院")
    public ApiResult updateHospital(@ApiParam(name = "json",value = "{\"hospitalId\": 1,\n" +
            "      \"hospitalName\": \"医院2名字\",\n" +
            "      \"provinceId\": 1,\n" +
            "      \"cityId\": 1,\n" +
            "      \"areaId\": 1,\n" +
            "      \"address\": \"医院详细地址\"}")@RequestBody Hospital json){
//        Hospital hospital = JsonUtil.gson.fromJson(json,Hospital.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commService.updateHospital(json));
        return apiResult;
    }

    /**
     * 删除医院
     * @param list
     * @return
     */
    @ApiOperation(value = "删除医院")
    @DeleteMapping("/deleteHospital")
    @Log(name = "医院、科室、疾病",value = "删除医院")
    public ApiResult deleteHospital(@RequestBody List<Long> list){
        int i = 0;
        for (Long hospitalId : list) {
            if(commService.deleteHospital(hospitalId) == 1){
                i++;
            }
        }
        Map<String,Integer> map = new HashMap<>();
        ApiResult apiResult = ApiResult.SUCCESS();
        map.put("suc",i);
        map.put("size",list.size());
        apiResult.setData(map);
        return apiResult;
    }
}
