package com.cj.suser.controller;

import com.cj.common.domain.BindDoctorVo;
import com.cj.common.utils.ResultUtil;
import com.cj.common.utils.sms.SmsCodeUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.entity.UserInfo;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.*;
import com.cj.suser.service.PerUserService;
import com.cj.suser.service.UserCallConsultService;
import com.cj.suser.service.UserFriendService;
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
 * @Description: 个人端用户管理
 * @date： 2019/6/28 10:30
 **/
@RestController
@RequestMapping("/s-user/api/v2/peruser")
@Api(tags = "后台: 用户管理-个人端用户")
public class PerUserController {

    @Autowired
    private PerUserService perUserService;
    @Autowired
    private UserCallConsultService userCallConsultService;
    @Resource
    private UserService userService;
    @Value("${jasypt.code}")
    private String code;

    @Resource
    private UserFriendService userFriendService;

    /**
     * 新增用户
     * @param ipuModel
     * @return
     */
    @PostMapping("/insertUser")
    @ApiOperation(value = "新增用户", response = IPUModel.class)
    @Log(name = "个人端用户", value = "新增用户")
    public ApiResult insertUser(@ApiParam @RequestBody IPUModel ipuModel) throws InvocationTargetException, IllegalAccessException {
        VoUser voUser = new VoUser();
        voUser.setCode(code);
        voUser.setUserName("A"+ipuModel.getUserName());
        voUser.setUserPass(ipuModel.getUserPass());
        Long register = userService.register(voUser);
        int i = 0;
        if(register != 0){
            //发送短信
            SmsCodeUtil.sendRegister(ipuModel.getUserName(),ipuModel.getUserPass());
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(register);
            userInfo.setSex(ipuModel.getSex());
            userInfo.setName(ipuModel.getName());
            i = userService.addUserInfo(userInfo);
//            i = userService.addUserInfo(register, ipuModel.getName(), ipuModel.getSex());
        }
        if(i > 0){
            BindDoctorVo bindDoctorVo = new BindDoctorVo();
            bindDoctorVo.setDoctorIds(ipuModel.getList());
            bindDoctorVo.setUserId(register);
            return userCallConsultService.userBindDoctor(bindDoctorVo);
        }
        return ResultUtil.result(0);
    }

    /**
     * 删除 用户 医生 护士 干部 营养师 助理
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteUser/{userId}")
    @ApiOperation(value = "删除用户")
    @Log(name = "个人端用户", value = "删除用户")
    public ApiResult deleteUser(@PathVariable Long userId){
        return ResultUtil.result(perUserService.deleteUser(userId));
    }

    /**
     * 修改密码
     * @param userId
     * @param userPass
     * @return
     */
    @PutMapping("/updatePass/{userId}/{userPass}")
    @ApiOperation(value = "修改密码")
    @Log(name = "个人端用户", value = "修改密码")
    public ApiResult updatePass(@PathVariable Long userId,@PathVariable String userPass){
        return ResultUtil.result(userService.putPass(userId,"12345678"));
    }

    /**
     * 查询用户列表
     * @param parameters
     * @param current
     * @return
     */
    @GetMapping("/findUser/{current}")
    @ApiOperation(value = "查询用户列表", response = FpuModel.class)
    @ApiImplicitParam(name = "parameters",value = "province=省 city=市 area=区 doctorType=医生类型" +
            "1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部" +
            "maxAge=年龄大 minAge=年龄小 sex=性别 男 女",
            required = true,
            defaultValue = "{\"province\":1,\"city\":1,\"area\":1," +
                    "\"doctorType\":2,\"maxAge\":2,\"minAge\":0,\"sex\":\"男\"}")
    @Log(name = "个人端用户", value = "查询用户列表")
    public ApiResult findUser(String parameters,@PathVariable int current){
        Pager pager = new Pager();
        pager.setCurrent(current);
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        pager.setParameters(map);
        return ResultUtil.result(perUserService.findUser(pager));
    }

    /**
     * 查询用户订单
     * @param current
     * @param parameter
     * @return
     */
    @GetMapping("/findOrder/{current}")
    @ApiOperation(value = "查询用户订单", response = OrderModel.class)
    @Log(name = "个人端用户", value = "查询用户订单")
    public ApiResult findOrder(@PathVariable int current,String parameter){
        Pager pager = new Pager();
        pager.setCurrent(current);
        pager.setParameter(parameter);
        return ResultUtil.result(perUserService.findOrder(pager));
    }

    /**
     * 模糊查询医生
     * @param current 权限id
     * @param parameter 条件
     * @return
     */
    @GetMapping("/findPeople/{current}")
    @ApiOperation(value = "模糊查询医生")
    @Log(name = "个人端用户", value = "模糊查询医生")
    public ApiResult findPeople(@PathVariable int current,String parameter){
        return ResultUtil.result(perUserService.findPeople(current,parameter));
    }

    /**
     * 导出用户列表
     * @param parameters
     * @return
     */
    @GetMapping("/export")
    @ApiOperation(value = "导出用户列表", response = FpuModel.class)
    @ApiImplicitParam(name = "parameters",value = "province=省 city=市 area=区 doctorType=医生类型" +
            "1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部" +
            "maxAge=年龄大 minAge=年龄小 sex=性别 男 女",
            required = true,
            defaultValue = "{\"province\":1,\"city\":1,\"area\":1," +
                    "\"doctorType\":2,\"maxAge\":2,\"minAge\":0,\"sex\":\"男\"}")
    @Log(name = "个人端用户", value = "导出用户列表")
    public ApiResult export(String parameters, HttpServletResponse response){
        Map<String,String> map = JsonUtil.gson.fromJson(parameters, Map.class);
        return ResultUtil.result(perUserService.export(map,response));
    }



    @GetMapping("/findFriendByUserId")
    @ApiOperation(value = "查询用户亲友列表", response = VoFriend.class)
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true)
    public ApiResult findFriendByUserId(long userId){
        return ResultUtil.result(userFriendService.findFriend(userId));
    }



}
