package com.cj.sbasic.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.DoctorInfoService;
import com.cj.sbasic.vo.DoctorCheckVO;
import com.cj.sbasic.vo.DoctorDetailVO;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@RestController
@RequestMapping("/s-basic/api/v1/check/doctor")
@Api(tags = "后台: 审核医生")
public class DoctorCheckController {
    @Autowired
    private DoctorInfoService doctorInfoService;

    /**
     * 按条件分页查询所有的待审核的医生列表
     * @param json
     * @return
     */
    @ApiOperation(value = "按条件分页查询医生审核信息列表",response = DoctorCheckVO.class)
    @GetMapping("/doctorChecks")
    @ApiImplicitParam(name = "json" ,value =" provinceId = 省Id,cityId = 市Id,areaId = 区Id,hospital = 所属医院,state = 审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"provinceId\":\"\",\"cityId\":\"\",\"areaId\":\"\",\"hospital\":\"\",\"state\":\"3\"}}")
    @Log(name = "审核管理",value = "按条件分页查询医生审核信息列表")
    public ApiResult getAllDoctorCheck(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","true");//分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(doctorInfoService.getDoctorCheckPage(oldPager));
        return apiResult;
    }

    /**
     * 根据医生id获取医生的详情信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据医生id获取医生的详情信息",response = DoctorDetailVO.class)
    @GetMapping("/getDoctorDetail")
    @Log(name = "审核管理",value = "根据医生id获取医生的详情信息")
    public ApiResult getDoctorDetailById(Long id){
        DoctorDetailVO doctorDetailVO = doctorInfoService.getDoctorDetailById(id);
        if (doctorDetailVO != null) {
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setData(doctorDetailVO);
            return apiResult;
        } else {
            ApiResult apiResult = ApiResult.FAIL();
            apiResult.setMsg("未能找到对应的医生详情信息");
            return apiResult;
        }
    }

    /**
     * 通过医生的审核
     * @param id
     * @return
     */
    @ApiOperation(value = "通过医生的审核,传入医生id和咨询服务新价格")
    @PutMapping("/passDoctorCheck")
    @Log(name = "审核管理",value = "通过医生的审核")
    public ApiResult updateStatePassById(Long id, BigDecimal goodPrice){

        return ResultUtil.result(doctorInfoService.updateStatePassById(id,goodPrice));
    }

    /**
     * 驳回医生的审核申请
     * @param id
     * @return
     */
    @ApiOperation(value = "驳回医生的审核申请")
    @PutMapping("/failDoctorCheck")
    @Log(name = "审核管理",value = "驳回医生的审核申请")
    public ApiResult updateStateFailById(Long id){
        int result = doctorInfoService.updateStateFailById(id);
        if (result == 1) {
            ApiResult apiResult = ApiResult.SUCCESS();
            return apiResult;
        }else {
            ApiResult apiResult = ApiResult.FAIL();
            apiResult.setMsg("处理失败");
            return apiResult;
        }
    }

    /**
     * 导出医生审核信息
     * @param json
     * @return
     */
    @ApiOperation(value = "导出医生审核信息")
    @GetMapping("/exportDoctorCheck")
    @ApiImplicitParam(name = "json" ,value =" provinceId = 省Id,cityId = 市Id,areaId = 区Id,hospital = 所属医院,state = 审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过",required = true,
            defaultValue = "{\"parameters\":{\"provinceId\":\"\",\"cityId\":\"\",\"areaId\":\"\",\"hospital\":\"\",\"state\":\"3\"}}")
    @Log(name = "审核管理",value = "导出医生审核信息")
    public ApiResult exportDoctorCheck(String json, HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","false");//不分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(doctorInfoService.exportDoctorCheck(doctorInfoService.getDoctorCheckPage(oldPager),response));
        return apiResult;
    }
}
