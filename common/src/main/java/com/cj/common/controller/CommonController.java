package com.cj.common.controller;

import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;
import com.cj.common.service.CommonService;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 公共接口
 * @date： 2019/3/12 15:53
 **/
@RestController
@RequestMapping("/s-*/api/v1/comment")
@Api(tags = "公共接口: 医院、科室、疾病")
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 查询科室列表
     * @return
     */
    @ApiOperation(value = "查询科室列表",response = Department.class)
    @GetMapping("/findDepartment")
    @Log(name = "医院、科室、疾病",value = "查询科室列表")
    public ApiResult findDepartment(){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commonService.getDepar());
        return apiResult;
    }
    /**
     * 查询疾病列表
     * @return
     */
    @ApiOperation(value = "查询疾病列表",response = Disease.class )
    @GetMapping("/findDiseaseByDepartmentId")
    @Log(name = "医院、科室、疾病",value = "查询疾病列表")
    public ApiResult findDiseaseByDepartmentId(Long departmentId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commonService.getDiseaseByDepartmentId(departmentId));
        return apiResult;
    }
    /**
     * 查询疾病
     * @return
     */
    @ApiOperation(value = "查询疾病",response = Disease.class )
    @GetMapping("/findDisease")
    @Log(name = "医院、科室、疾病",value = "查询疾病")
    public ApiResult findDiseaseById(Long diseaseId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commonService.getDiseaseById(diseaseId));
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
            defaultValue = "{\"provinceId\":1,\"cityId\":10,\"areaId\":1}")
    @Log(name = "医院、科室、疾病",value = "查询医院列表")
    public ApiResult findHospital(String json){
        Map<String,Long> map = JsonUtil.gson.fromJson(json,Map.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(commonService.getHospitalByRegion(map));
        return apiResult;
    }
}
