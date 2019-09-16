package com.cj.suser.controller;

import com.cj.core.entity.UserInfo;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.exception.MyException;
import com.cj.core.util.reg.RegexUtils;
import com.cj.suser.domain.VoUserInfo;
import com.cj.suser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/s-user/api/v2/userInfo")
@Api(tags = "用户端: 用户详情")
public class UserInfoController {


    @Resource
    private UserService userService;


    //============================================userInfo====================================================

    @GetMapping("/findUserInfo")
    @ApiOperation(value = "查询用户详情,根据token",response = VoUserInfo.class)
    @Log(name = "用户详情", value = "查询用户详情")
    public ApiResult findUserInfo(HttpServletRequest request){

        return ResultUtil.result(1,userService.findUserInfo(request));


    }
    @PostMapping("/addUserInfo")
    @ApiOperation(value = "新增用户详情")
    @Log(name = "用户详情", value = "新增用户详情")
    public ApiResult addUserInfo(@ApiParam(name = "userInfo",value = "用户详情",required = true)
                                 @RequestBody UserInfo userInfo) {
        if (userInfo.getIdNumber()!=null){
            if (!RegexUtils.checkIdCard(userInfo.getIdNumber())){
                throw new MyException("身份证号错误");
            }
        }

        return ResultUtil.result(userService.addUserInfo(userInfo));
    }

    @PutMapping("/updateUserInfo")
    @ApiOperation(value = "修改用户详情")
    @Log(name = "用户详情", value = "修改用户详情")
    public ApiResult updateUserInfo(@ApiParam(name = "userInfo",value = "用户详情，userInfoId必传",required = true)
                                    @RequestBody UserInfo userInfo){
        if (userInfo.getIdNumber()!=null){
            if (!RegexUtils.checkIdCard(userInfo.getIdNumber())){
                throw new MyException("身份证号错误");
            }
        }

        return ResultUtil.result(userService.updateUserInfo(userInfo));
    }

    @PutMapping("/updateHeadUrl")
    @ApiOperation(value = "用户修改头像")
    @Log(name = "用户详情", value = "用户修改头像")
    @ApiImplicitParam(name = "headUrl",value = "头像地址",required = true)
    public ApiResult updateUserHeadUrl(String headUrl,HttpServletRequest request){

        return ResultUtil.result(userService.updateUserHeadUrl(headUrl,request));
    }


}
