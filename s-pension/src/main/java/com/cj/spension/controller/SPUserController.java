package com.cj.spension.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.spension.domain.FillIllModel;
import com.cj.spension.service.SPDoctorService;
import com.cj.spension.service.SPUserService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用户信息管理
 * @date： 2019/3/11 16:14
 **/
@RestController
@RequestMapping("/s-pension/api/v1/userInfo")
@Api(tags = "后台: 用户信息管理")
public class SPUserController {

    @Autowired
    private SPUserService userService;
    @Autowired
    private SPDoctorService doctorService;

    /**
     * 查询用户信息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询用户信息列表")
    @GetMapping("/findUserPage")
    @ApiImplicitParam(name = "json" ,
            value ="province=省 city=市 area=区 sex=性别 " +
                    "minAge=开始年龄 maxAge=结束年龄 diseaseId=疾病id equipmentType=设备类型" +
                    "doctorId=绑定医生id expertId=绑定专家id like = 模糊查询条件"+
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
                    "\"parameters\":{\"province\":1,\"city\":1,\"area\":1," +
                    "\"sex\":\"男\",\"minAge\":1,\"maxAge\":1,\"diseaseId\":1," +
                    "\"equipmentType\":1,\"expertId\":1,\"like\":\"模糊查询条件%\",\"doctorId\":1," +
    "\"abnormalAcr\":1 ,\"abnormalLeu\":1 ,\"abnormalNit\":1 ,\"abnormalUbg\":1 " +
            ",\"abnormalPro\":0 ,\"abnormalPh\":0 ,\"abnormalBld\":0 ,\"abnormalSg\":1 " +
            ",\"abnormalKet\":0 ,\"abnormalBil\":0 ,\"abnormalGlu\":0 ,\"abnormalVc\":1 " +
            ",\"abnormalMa\":0 ,\"abnormalCre\":0 ,\"abnormalCa\":0 }}")
    @Log(name = "用户信息管理",value = "查询用户信息列表")
    public ApiResult findUserPage(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = userService.getUserPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 查询用户疾病信息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "查询用户疾病信息列表",response = FillIllModel.class)
    @GetMapping("/findFillPage")
    @ApiImplicitParam(name = "json" ,
            value ="userId = 用户id",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"userId\":1}}")
    @Log(name = "用户信息管理",value = "查询用户疾病信息列表")
    public ApiResult findFillPage(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = userService.getFillPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 根据ID查询用户疾病信息
     * @param fallIllId
     * @return
     */
    @ApiOperation(value = "根据ID查询用户疾病信息",response = FillIllModel.class)
    @GetMapping("/findFillByID")
    @Log(name = "用户信息管理",value = "查询用户疾病信息")
    public ApiResult findFillByID(Long fallIllId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userService.getFillByID(fallIllId));
        return apiResult;
    }
    /**
     * 根据ID查询用户绑定设备信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据ID查询用户绑定设备信息")
    @GetMapping("/findUserByID")
    @Log(name = "用户信息管理",value = "查询用户绑定设备信息")
    public ApiResult findUserByID(Long userId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userService.getUserByID(userId));
        return apiResult;
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPass 新密码
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePass")
    @Log(name = "用户信息管理",value = "修改密码")
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
    @ApiOperation(value = "删除用户账号")
    @DeleteMapping("/deleteUser")
    @Log(name = "用户信息管理",value = "删除账号")
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
            value ="province=省 city=市 area=区 sex=性别 " +
                    "minAge=开始年龄 maxAge=结束年龄 diseaseId=疾病id equipmentType=设备类型" +
                    "doctorId=绑定医生id expertId=绑定专家id like = 模糊查询条件",
            required = true,
            defaultValue = "{\"parameters\":{\"province\":1,\"city\":1,\"area\":1," +
                    "\"sex\":\"男\",\"minAge\":1,\"maxAge\":1,\"diseaseId\":1," +
                    "\"equipmentType\":1,\"expertId\":1,\"like\":\"模糊查询条件%\",\"doctorId\":1}}")
    @Log(name = "用户信息管理",value = "导出Excel")
    public void exportExcel(String json,HttpServletResponse response) {
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        userService.exportExcel(oldPager,response);
    }
}
