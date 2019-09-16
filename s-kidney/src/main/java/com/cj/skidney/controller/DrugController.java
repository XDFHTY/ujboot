package com.cj.skidney.controller;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.DrugwarnTime;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.skidney.domain.DrugModel;
import com.cj.skidney.service.DrugService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用药管理
 * @date： 2019/3/19 11:51
 **/
@RestController
@RequestMapping("/s-kidney/api/v1/drug")
@Api(tags = "用户端: 用药管理")
public class DrugController {

    @Autowired
    private DrugService drugService;
    /**
     * 新增用药计划
     * @param json
     * @return
     */
    @ApiOperation(value = "新增用药计划",response = DrugModel.class)
    @PostMapping("/insertDrug")
    @Log(name = "用药管理",value = "新增用药计划")
    public ApiResult insertDrug(@ApiParam(name = "json",value = "{\n" +
            "    \"drugwarn\": {\n" +
            "      \"userId\": 477,\n" +
            "      \"drugName\": \"三九感冒灵\",\n" +
            "      \"drugMeasure\": \"1袋\",\n" +
            "      \"takeNumber\": \"4\"\n" +
            "    },\n" +
            "    \"dtList\": [\n" +
            "      {\n" +
            "        \"drugTime\": \"1970-01-01 18:10:00\",\n" +
            "        \"isClose\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"drugTime\": \"1970-01-01 18:20:00\",\n" +
            "        \"isClose\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"drugTime\": \"1970-01-01 18:30:00\",\n" +
            "        \"isClose\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"drugTime\": \"1970-01-01 16:10:00\",\n" +
            "        \"isClose\": \"0\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }",required = true)@RequestBody DrugModel json){
//        DrugModel drugModel = JsonUtil.gson.fromJson(json,DrugModel.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(drugService.insertDrug(json));
        return apiResult;
    }


    /**
     * 删除用药计划
     * @param drugwarnId
     * @return
     */
    @ApiOperation(value = "删除用药计划")
    @DeleteMapping("/deleteDrug")
    @Log(name = "用药管理",value = "删除用药计划")
    public ApiResult deleteDrug(Long drugwarnId){
        int i = drugService.delete(drugwarnId);
        if(i>=1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 修改通知状态
     * @param json
     * @return
     */
    @ApiOperation(value = "修改通知状态",response = DrugwarnTime.class)
    @PutMapping("/updataDrug")
    @ApiImplicitParam(name = "json",value = "drugwarnTimeId=用药提醒时间id drugTime = 用药提醒时间" +
            "isClose=0-关闭提醒，1-正常提醒",
            required = true,
            defaultValue = "[{\"drugwarnTimeId\":1,\"drugTime\":\"1970-01-01 18:20:00\",\"isClose\":0}," +
                    "{\"drugwarnTimeId\":2,\"drugTime\":\"1970-01-01 18:20:00\",\"isClose\":0}]")
    @Log(name = "用药管理",value = "修改通知状态")
    public ApiResult updataDrug(String json){
        List<DrugwarnTime> list = JsonUtil.gson.fromJson(json,new TypeToken<List<DrugwarnTime>>(){}.getType());
        int i = list.stream().mapToInt(drugwarnTime->drugService.update(drugwarnTime)).sum();
        if(i==list.size()){
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setData(i);
            return apiResult;
        }
        return ApiResult.FAIL();
    }
    /**
     * 查询用药列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询用药列表",response = DrugModel.class)
    @GetMapping("/findDrug")
    @ApiImplicitParam(name = "json" ,value ="drugTime=用药时间 userId=用户id ",
            required = true,
            defaultValue = "{\"parameters\":{\"drugTime\":\"01:01:01\",\"userId\":1}}")
    @Log(name = "用药管理",value = "查询用药列表")
    public ApiResult findDrug(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(drugService.getDrug(oldPager));
        return apiResult;
    }
    /**
     * 查询用药
     * @param drugwarnId
     * @return
     */
    @ApiOperation(value = "查询用药",response = DrugModel.class)
    @GetMapping("/findDrugById")
    @Log(name = "用药管理",value = "查询用药")
    public ApiResult findDrugById(Long drugwarnId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(drugService.getDrugById(drugwarnId));
        return apiResult;
    }
}
