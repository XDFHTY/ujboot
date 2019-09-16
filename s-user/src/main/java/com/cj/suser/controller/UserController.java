package com.cj.suser.controller;

import com.cj.common.domain.LoginResp;
import com.cj.common.domain.RespIDCard;
import com.cj.common.domain.RespInfo;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.entity.AppVersion;
import com.cj.core.entity.User;
import com.cj.core.exception.MyException;
import com.cj.core.util.reg.RegexUtils;
import com.cj.suser.domain.VoUpdatePass;
import com.cj.suser.domain.VoUser;
import com.cj.suser.service.UserService;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/s-user/api/v2/user")
@Api(tags = "用户端&医生端:   注册、登录、注销、身份识别等")
public class UserController {


    @Resource
    private UserService userService;


    @GetMapping("/getCode")
    @ApiOperation(value = "用户获取短信验证码，userName=前缀+手机号,个人端A,医生端B")
    @Log(name = "用户基础信息", value = "用户获取短信验证码")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true)
    public ApiResult getCode(String userName) {
        String phone = userName.substring(1);
        if (phone!=null){
            if (!RegexUtils.checkMobile(phone)){

                throw new MyException("手机号错误");
            }
        }

        String code = userService.getCode(userName,phone);

        ApiResult apiResult = ApiResult.SUCCESS();
//        apiResult.setData(code);

        return apiResult;
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册,只需要传用户名、密码、验证码，userName=前缀+手机号,个人端A,医生端B", response = LoginResp.class)
    @Log(name = "用户基础信息", value = "用户注册")
    public ApiResult register(@ApiParam(name = "voRegister", value = "{\n" +
            "  \"code\": \"999999\",\n" +
            "  \"userName\": \"13512345678\",\n" +
            "  \"userPass\": \"123456\",\n" +
            "}", required = true)
                              @RequestBody VoUser voUser) throws InvocationTargetException, IllegalAccessException {

        long userId = userService.register(voUser);

        if (userId>0){
            return ResultUtil.result(1,"注册成功",userId);

        }else {

            return ResultUtil.result(0);
        }

    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录,userName=前缀+手机号,个人端A,医生端B，返回：state（审核状态）=1-正常，2-未审核，3-审核中，4-审核不通过", response = LoginResp.class)
    @Log(name = "用户基础信息", value = "用户登录")
    public ApiResult login(@ApiParam(name = "user", value = "{\n" +
            "  \"userName\": \"18251957230\",\n" +
            "  \"userPass\": \"12345699\"\n" +
            "}", required = true)
                           @RequestBody VoUser voUser, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        User user = new User();
        BeanUtils.copyProperties(user, voUser);

        return ResultUtil.result(userService.login(user, request));
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "用户注销")
    @Log(name = "用户基础信息", value = "用户注销")
    public ApiResult logout(HttpServletRequest request) {

        userService.logout(request);
        return ApiResult.SUCCESS();
    }

    @PutMapping("/retrieveUserPass")
    @ApiOperation(value = "用户找回密码")
    @Log(name = "用户模块", value = "用户找回密码")
    public ApiResult retrieveUserPass(@RequestBody VoUser voUser) {
        int i = userService.retrieveUserPass(voUser);
        if (i == 1) {
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    @GetMapping("/findVersion")
    @ApiOperation(value = "检查更新", response = AppVersion.class)
    @Log(name = "用户模块", value = "检查更新")
    @ApiImplicitParam(name = "type", value = "1-安卓用户端，2-安卓医生端，3-IOS用户端，4-IOS医生端", required = true, defaultValue = "1")
    public ApiResult findVersion(String type) {
        return ResultUtil.result(userService.findVersion(type));
    }

    @PutMapping("/updatePass")
    @ApiOperation(value = "用户修改密码")
    @Log(name = "用户模块", value = "用户修改密码")
    public ApiResult updatePass(HttpServletRequest request,
                                @RequestBody VoUpdatePass voUpdatePass) {
        return ResultUtil.result(userService.updatePass(request, voUpdatePass));
    }


    //============================================info====================================================
    @GetMapping("/getCardInfo")
    @ApiOperation(value = "识别身份证信息", response = RespIDCard.class)
    @Log(name = "用户基础信息", value = "识别身份证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", value = "idCard=身份证地址", required = true),
            @ApiImplicitParam(name = "idCardSide", value = "1为身份证正面，2为反面,可以不传，默认为1", required = false, defaultValue = "1")
    })
    public ApiResult getCardInfo(String idCard, String idCardSide) {


        return userService.getCardInfo(idCard, idCardSide);

    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "根据用户id集合获取用户昵称、头像", response = RespInfo.class)
    @Log(name = "用户基础信息", value = "根据用户id集合获取用户昵称、头像")
    @ApiImplicitParam(name = "json", value = "RespInfo对象集合", required = true, defaultValue = "[{\"userId\":593},{\"userId\":594}]")
    public ApiResult getInfo(String json) {

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userService.getInfo(json));

        return apiResult;

    }


}
