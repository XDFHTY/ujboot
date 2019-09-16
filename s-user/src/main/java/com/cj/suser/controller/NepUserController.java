package com.cj.suser.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.common.utils.sms.SmsCodeUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.*;
import com.cj.suser.service.NepUserService;
import com.cj.suser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 肾病医生管理
 * @date： 2019/6/28 18:49
 **/
@RestController
@RequestMapping("/s-user/api/v2/nepuser")
@Api(tags = "后台&医生端: 用户管理-关注端用户")
public class NepUserController {

    @Value("${jasypt.code}")
    private String code;
    @Resource
    private UserService userService;
    @Autowired
    private NepUserService nepUserService;

    /**
     * 新增 医生 护士 干部 营养师 助理
     * @param iduModel
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PostMapping("/insertDoctor")
    @ApiOperation(value = "新增医生", response = IDUModel.class)
    @Log(name = "医生管理", value = "新增医生")
    public ApiResult insertDoctor(@ApiParam(name = "iduModel",value = "新增医生",required = true)
                                      @RequestBody IDUModel iduModel) throws InvocationTargetException, IllegalAccessException {
        VoUser voUser = new VoUser();
        voUser.setCode(code);
        voUser.setUserName("B"+iduModel.getUserName());
        voUser.setUserPass(iduModel.getUserPass());
        Long register = userService.register(voUser);
        if(register != 0){
            //发送短信
            SmsCodeUtil.sendRegister(iduModel.getUserName(),iduModel.getUserPass());
            VoDoctorInfo voDoctorInfo = iduModel.getVoDoctorInfo();
            voDoctorInfo.setUserId(register);
            ResultUtil.result(userService.addDoctorInfo(voDoctorInfo));
        }
        return ResultUtil.result(1);
    }

    /**
     * 修改 医生 护士 干部 营养师 助理 详情
     * @param voDoctorInfo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PutMapping("/updateDoctor")
    @ApiOperation(value = "修改医生详情", response = VoDoctorInfo.class)
    @Log(name = "医生管理", value = "修改医生详情")
    public ApiResult updateDoctor(@ApiParam(name = "voDoctorInfo",value = "修改医生详情",required = true)
                                          @RequestBody VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException {
        return ResultUtil.result(userService.updateDoctorInfo(voDoctorInfo));
    }

    /**
     * 查询 医生 护士 干部 营养师 助理 列表
     * @param parameters
     * @param current
     * @return
     */
    @GetMapping("/findDoctor/{current}")
    @ApiOperation(value = "查询医生列表", response = FnuModel.class)
    @ApiImplicitParam(name = "parameters",value = "province=省 city=市 area=区 " +
            "doctorType=医生类型 29-专科医生 30-家庭医生 32-营养师 33-乡干部 34-卫健委干部 " +
            "35-离退休干部 36-护士 37-健康管理师 （必传）" +
            "hospital=医院 departments=科室 title=职称 sex=性别 男 女",
            required = true,
            defaultValue = "{\"province\":1,\"city\":1,\"area\":1,\"doctorType\":29," +
                    "\"hospital\":2,\"departments\":0,\"title\":\"医师\",\"sex\":\"男\"}")
    @Log(name = "医生管理", value = "查询医生列表")
    public ApiResult findDoctor(String parameters,@PathVariable int current){
        Pager<Map> pager = new Pager<Map>();
        pager.setCurrent(current);
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        pager.setParameters(map);
        return ResultUtil.result(nepUserService.findDoctor(pager));
    }

    /**
     * 根据医生id查询患者列表
     * @param parameters
     * @param current
     * @return
     */
    @GetMapping("/findUserByDoctorId/{current}")
    @ApiOperation(value = "查询患者列表", response = FpuModel.class)
    @ApiImplicitParam(name = "parameters",value = "doctorId=医生ID（必传） " +
            "maxAge=年龄大 minAge=年龄小 input=输入框 sex=性别 男 女",
            required = true,
            defaultValue = "{\"doctorId\":1,\"maxAge\":2,\"minAge\":0," +
                    "\"input\":\"输入框\",\"sex\":\"男\"}")
    @Log(name = "医生管理", value = "查询患者列表")
    public ApiResult findUserByDoctorId(String parameters,@PathVariable int current){
        Pager pager = new Pager();
        pager.setCurrent(current);
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        pager.setParameters(map);
        return ResultUtil.result(nepUserService.findUserByDoctorId(pager));
    }

    /**
     * 根据医生id查询医生团队
     * @param doctorId
     * @return
     */
    @GetMapping("/findTeamByDoctorId/{doctorId}")
    @ApiOperation(value = "查询医生团队", response = TModel.class)
    @Log(name = "医生管理", value = "查询医生团队")
    public ApiResult findTeamByDoctorId(@PathVariable Long doctorId){
        TModel tModel = nepUserService.findTeamByDoctorId(doctorId);
        if(tModel==null){
            return ResultUtil.result(0,"没有团队");
        }else {
            return ResultUtil.result(nepUserService.findTeamByDoctorId(doctorId));
        }
    }

    /**
     * 导出 医生 护士 干部 营养师 助理
     * @param parameters
     * @param response
     * @return
     */
    @GetMapping("/export")
    @ApiOperation(value = "导出")
    @ApiImplicitParam(name = "parameters",value = "province=省 city=市 area=区 " +
            "doctorType=医生类型 29-专科医生 30-家庭医生 32-营养师 33-乡干部 34-卫健委干部 " +
            "35-离退休干部 36-护士 37-健康管理师 （必传）" +
            "hospital=医院 departments=科室 title=职称 sex=性别 男 女",
            required = true,
            defaultValue = "{\"province\":1,\"city\":1,\"area\":1,\"doctorType\":29," +
                    "\"hospital\":2,\"departments\":0,\"title\":\"医师\",\"sex\":\"男\"}")
    @Log(name = "医生管理", value = "导出")
    public ApiResult export(String parameters, HttpServletResponse response){
        Map map = JsonUtil.gson.fromJson(parameters, Map.class);
        return ResultUtil.result(nepUserService.export(map,response));
    }
}
