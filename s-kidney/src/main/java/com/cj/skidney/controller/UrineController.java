package com.cj.skidney.controller;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.BloodCreatinine;
import com.cj.core.entity.BloodPressure;
import com.cj.core.entity.UrineProtein;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.skidney.domain.UrineModel;
import com.cj.skidney.service.UrineService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 检查记录管理
 * @date： 2019/3/13 10:26
 **/
@RestController
@RequestMapping("/s-kidney/api/v1/inspect")
@Api(tags = "公共接口: 检查记录管理")
public class UrineController {

    @Autowired
    private UrineService urineService;

    /**
     * 插入尿检结果
     * @param json
     * @return
     */
    @ApiOperation(value = "插入尿检结果",response = UrineModel.class)
    @PostMapping("/insertUrine")
    @Log(name = "检查记录管理",value = "插入尿检结果")
    public ApiResult insertUrine(@ApiParam(name = "json",value = "[{\n" +
            "        \"urineIdentification\": {\n" +
            "          \"identificationAcr\": null,\n" +
            "          \"identificationLeu\": null,\n" +
            "          \"identificationNit\": null,\n" +
            "          \"identificationUbg\": null,\n" +
            "          \"identificationPro\": null,\n" +
            "          \"identificationPh\": null,\n" +
            "          \"identificationBld\": null,\n" +
            "          \"identificationSg\": null,\n" +
            "          \"identificationKet\": null,\n" +
            "          \"identificationBil\": null,\n" +
            "          \"identificationGlu\": null,\n" +
            "          \"identificationVc\": null,\n" +
            "          \"identificationMa\": null,\n" +
            "          \"identificationCre\": null,\n" +
            "          \"identificationCa\": null\n" +
            "        },\n" +
            "        \"urineResultVO\": {\n" +
            "          \"userId\": 477,\n" +
            "          \"bluetoothMac\": 13,\n" +
            "          \"resultAcr\": null,\n" +
            "          \"resultLeu\": \"14\",\n" +
            "          \"resultNit\": \"1\",\n" +
            "          \"resultUbg\": \"1\",\n" +
            "          \"resultPro\": \"1\",\n" +
            "          \"resultPh\": \"1.0\",\n" +
            "          \"resultBld\": \"1\",\n" +
            "          \"resultSg\": \"1.000\",\n" +
            "          \"resultKet\": \"1\",\n" +
            "          \"resultBil\": \"1\",\n" +
            "          \"resultGlu\": \"1\",\n" +
            "          \"resultVc\": \"1.0\",\n" +
            "          \"resultMa\": \"1\",\n" +
            "          \"resultCre\": \"1.0\",\n" +
            "          \"resultCa\": \"1.0\",\n" +
            "          \"inspectTime\": \"2019-04-02 11:48:05\"\n" +
            "        },\n" +
            "        \"urineAbnormal\": {\n" +
            "          \"abnormalAcr\": null,\n" +
            "          \"abnormalLeu\": null,\n" +
            "          \"abnormalNit\": null,\n" +
            "          \"abnormalUbg\": null,\n" +
            "          \"abnormalPro\": null,\n" +
            "          \"abnormalPh\": null,\n" +
            "          \"abnormalBld\": null,\n" +
            "          \"abnormalSg\": null,\n" +
            "          \"abnormalKet\": null,\n" +
            "          \"abnormalBil\": null,\n" +
            "          \"abnormalGlu\": null,\n" +
            "          \"abnormalVc\": null,\n" +
            "          \"abnormalMa\": null,\n" +
            "          \"abnormalCre\": null,\n" +
            "          \"abnormalCa\": null\n" +
            "        }\n" +
            "      }]",required = true)@RequestBody List<UrineModel> json){
//        List<UrineModel> list = JsonUtil.gson.fromJson(json,new TypeToken<List<UrineModel>>(){}.getType());
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(urineService.insertUrine(json));
        return apiResult;
    }
    /**
     * 插入24小时尿蛋白结果
     * @param json
     * @return
     */
    @ApiOperation(value = "插入24小时尿蛋白结果",response = UrineProtein.class)
    @PostMapping("/insertUrineProtein")
    @Log(name = "尿蛋白记录管理",value = "插入24小时尿蛋白结果")
    public ApiResult insertUrineProtein(@ApiParam(name = "json",value = "{\n" +
            "        \"userId\": 477,\n" +
            "        \"abnormal\": \"1\",\n" +
            "        \"protein\": 1,\n" +
            "        \"amount\": 1,\n" +
            "        \"createTime\": \"2019-04-02 16:20:03\"\n" +
            "      }",required = true)@RequestBody UrineProtein json){
//        UrineProtein urineProtein = JsonUtil.gson.fromJson(json,UrineProtein.class);
        int i = urineService.insertUrineProtein(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 插入血肌酐结果
     * @param json
     * @return
     */
    @ApiOperation(value = "插入血肌酐结果",response = BloodCreatinine.class)
    @PostMapping("/insertBloodCreatinine")
    @Log(name = "血肌酐记录管理",value = "插入血肌酐结果")
    public ApiResult insertBloodCreatinine(@ApiParam(name = "json",value = "{\n" +
            "        \"userId\": 477,\n" +
            "        \"abnormal\": \"1\",\n" +
            "        \"sc\": 1,\n" +
            "        \"createTime\": \"2019-04-02 11:46:24\"\n" +
            "      }",required = true)@RequestBody BloodCreatinine json){
//        BloodCreatinine bloodCreatinine = JsonUtil.gson.fromJson(json,BloodCreatinine.class);
        int i = urineService.insertBloodCreatinine(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 插入血压结果
     * @param json
     * @return
     */
    @ApiOperation(value = "插入血压结果",response = BloodPressure.class)
    @PostMapping("/insertBloodPressure")
    @Log(name = "血压记录管理",value = "插入血压结果")
    public ApiResult insertBloodPressure(@ApiParam(name = "json",value = "  {\n" +
            "        \"userId\": 477,\n" +
            "        \"bluetoothMac\": 13,\n" +
            "        \"abnormal\": \"1\",\n" +
            "        \"systolicPressure\": 1,\n" +
            "        \"diastolicPressure\": 1,\n" +
            "        \"pulse\": 1,\n" +
            "        \"createTime\": \"2019-03-14 16:37:54\"\n" +
            "      }",required = true)@RequestBody BloodPressure json){
//        BloodPressure bloodPressure = JsonUtil.gson.fromJson(json, BloodPressure.class);
        int i = urineService.insertBloodPressure(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 查询检测记录列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询检测记录列表")
    @GetMapping("/findIspectModel")
    @ApiImplicitParam(name = "json" ,value ="province=省 city=市 area=区 hospitalId=医院表id" +
            "sex=性别 男 女 minAge=开始年龄 maxAge=结束年龄 minTime=开始时间 maxTime=结束时间" +
            "abnormal=异常 0-有 1-无 diseaseId=疾病 type = 类型 0-尿检 1-血压 2-血肌酐 3-24小时尿蛋白" +
            "abnormalAcr = 尿微量白蛋白肌酐比值 1-异常 0-正常 abnormalUbg = 尿胆原 1-异常 0-正常" +
            "abnormalLeu = 白细胞 1-异常 0-正常 abnormalNit = 亚硝酸盐 1-异常 0-正常" +
            "abnormalPro = 蛋白质 1-异常 0-正常 abnormalPh = PH值 1-异常 0-正常 " +
            "abnormalBld = 潜血 1-异常 0-正常 abnormalSg = 比重 1-异常 0-正常 " +
            "abnormalKet = 酮体 1-异常 0-正常 abnormalBil = 胆红素 1-异常 0-正常 " +
            "abnormalGlu = 葡萄糖 1-异常 0-正常 abnormalVc = 维生素c 1-异常 0-正常" +
            "abnormalMa = 微量白蛋白 1-异常 0-正常 abnormalCre = 肌酐 1-异常 0-正常" +
            "abnormalCa = 钙 1-异常 0-正常",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"province\":\"1\",\"city\":\"1\",\"area\":\"1\"," +
                    "\"hospitalId\":\"1\",\"sex\":\"男\",\"minAge\":\"1\",\"abnormal\":\"1\"," +
                    "\"maxAge\":\"1\",\"minTime\":2000-02-02,\"maxTime\":2000-02-02" +
                    ",\"diseaseId\":1,\"type\":0" +
                    ",\"abnormalAcr\":0 ,\"abnormalLeu\":0 ,\"abnormalNit\":0 ,\"abnormalUbg\":0 " +
                    ",\"abnormalPro\":0 ,\"abnormalPh\":0 ,\"abnormalBld\":0 ,\"abnormalSg\":0 " +
                    ",\"abnormalKet\":0 ,\"abnormalBil\":0 ,\"abnormalGlu\":0 ,\"abnormalVc\":0 " +
                    ",\"abnormalMa\":0 ,\"abnormalCre\":0 ,\"abnormalCa\":0 }}")
    @Log(name = "检查记录管理",value = "查询检测记录列表")
    public ApiResult findIspectModel(String json) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = urineService.getInsM(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 查询检查结果列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询检查结果列表")
    @GetMapping("/findResult")
    @ApiImplicitParam(name = "json" ,value ="userId=用户id minTime=开始时间 maxTime=结束时间 " +
            "type = 类型 0-尿检 1-血压 2-血肌酐 3-24小时尿蛋白",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
             "\"parameters\":{\"userId\":\"1\",\"minTime\":2000-02-02,\"maxTime\":2000-02-02,\"type\":\"0\"}}")
    @Log(name = "检查记录管理",value = "查询检查结果列表")
    public ApiResult findResult(String json) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = urineService.getResult(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 根据id查询检查结果
     * @param json
     * @return
     */
    @ApiOperation(value = "根据id查询检查结果")
    @GetMapping("/findResultByid")
    @ApiImplicitParam(name = "json" ,value ="id=检查结果id type= 0-尿检 1-血压 2-血肌酐 3-24小时尿蛋白 ",
            required = true,
            defaultValue = "{\"parameters\":{\"id\":1,\"type\":\"0\"}}")
    @Log(name = "检查记录管理",value = "根据id查询检查结果")
    public ApiResult findResultByid(String json) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = urineService.getResultById(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 导出检测记录列表
     * @param json
     * @return
     */
    @ApiOperation(value = "导出检测记录列表")
    @GetMapping("/exportIspect")
    @ApiImplicitParam(name = "json" ,value ="province=省 city=市 area=区 hospitalId=医院表id" +
            "sex=性别 男 女 minAge=开始年龄 maxAge=结束年龄 minTime=开始时间 maxTime=结束时间" +
            "abnormal=异常 0-有 1-无 diseaseId=疾病 type = 类型 0-尿检 1-血压 2-血肌酐 3-24小时尿蛋白" +
            "abnormalAcr = 尿微量白蛋白肌酐比值 1-异常 0-正常 abnormalUbg = 尿胆原 1-异常 0-正常" +
            "abnormalLeu = 白细胞 1-异常 0-正常 abnormalNit = 亚硝酸盐 1-异常 0-正常" +
            "abnormalPro = 蛋白质 1-异常 0-正常 abnormalPh = PH值 1-异常 0-正常 " +
            "abnormalBld = 潜血 1-异常 0-正常 abnormalSg = 比重 1-异常 0-正常 " +
            "abnormalKet = 酮体 1-异常 0-正常 abnormalBil = 胆红素 1-异常 0-正常 " +
            "abnormalGlu = 葡萄糖 1-异常 0-正常 abnormalVc = 维生素c 1-异常 0-正常" +
            "abnormalMa = 微量白蛋白 1-异常 0-正常 abnormalCre = 肌酐 1-异常 0-正常" +
            "abnormalCa = 钙 1-异常 0-正常",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"province\":\"1\",\"city\":\"1\",\"area\":\"1\"," +
                    "\"hospitalId\":\"1\",\"sex\":\"男\",\"minAge\":\"1\",\"abnormal\":\"1\"," +
                    "\"maxAge\":\"1\",\"minTime\":2000-02-02,\"maxTime\":2000-02-02" +
                    ",\"diseaseId\":1,\"type\":0" +
                    ",\"abnormalAcr\":0 ,\"abnormalLeu\":0 ,\"abnormalNit\":0 ,\"abnormalUbg\":0 " +
                    ",\"abnormalPro\":0 ,\"abnormalPh\":0 ,\"abnormalBld\":0 ,\"abnormalSg\":0 " +
                    ",\"abnormalKet\":0 ,\"abnormalBil\":0 ,\"abnormalGlu\":0 ,\"abnormalVc\":0 " +
                    ",\"abnormalMa\":0 ,\"abnormalCre\":0 ,\"abnormalCa\":0 }}")
    @Log(name = "检查记录管理",value = "导出检测记录列表")
    public void exportIspect(String json,HttpServletResponse response) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        urineService.exportIspect(oldPager,response);
    }
    /**
     * 导出检查结果列表
     * @param json
     * @return
     */
    @ApiOperation(value = "导出检查结果列表")
    @GetMapping("/export")
    @ApiImplicitParam(name = "json" ,value ="userId=用户id minTime=开始时间 maxTime=结束时间" +
            "type = 类型 0-尿检 1-血压 2-血肌酐 3-24小时尿蛋白 path=保存路径",
            required = true,
            defaultValue = "{\"parameters\":{\"userId\":\"1\",\"minTime\":2000-02-02," +
                    "\"maxTime\":2000-02-02,\"path\":保存路径,\"type\":\"0\"}}")
    @Log(name = "检查记录管理",value = "导出检查结果列表")
    public void export(String json,HttpServletResponse response) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        urineService.export(oldPager,response);
    }
}
