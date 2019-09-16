package com.cj.suser.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.VoFallIllList;
import com.cj.suser.domain.VoFriend;
import com.cj.suser.domain.VoUserInfo;
import com.cj.suser.service.UserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/s-user/api/v2/userFriend")
@Api(tags = "用户端: 用户亲友")
public class V2UserFriendController {

    @Resource
    private UserFriendService userFriendService;

    @GetMapping("/findUserByPhone")
    @ApiOperation(value = "搜索亲友,（userType=0，对方是游客，不可以加好友，userType=1,可以加好友）",response = VoFriend.class)
    @Log(name = "用户亲友",value = "搜索亲友")
    @ApiImplicitParam(name = "phone",value = "手机号",required = true)
    public ApiResult findUserByPhone(String phone){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userFriendService.findUserByPhone(phone));
        return apiResult;
    }



    @PostMapping("/reqBind/{friendId}")
    @ApiOperation(value = "请求绑定亲友")
    @Log(name = "用户亲友",value = "请求绑定亲友")
    public ApiResult reqBind(HttpServletRequest request,
                             @ApiParam(name = "friendId",value = "friendId=查询出来的亲友userId",required = true)
                                 @PathVariable long friendId){
        long userId = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(userFriendService.reqBind(userId,friendId));
    }


    @PostMapping("/confirmBind/{friendId}")
    @ApiOperation("确认绑定亲友")
    @Log(name = "用户亲友",value = "确认绑定亲友")
    public ApiResult confirmBind(HttpServletRequest request,
                              @ApiParam(name = "friendId",value = "friendId=极光推送过来的friendId",required = true)
                              @PathVariable long friendId){
        long userId = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(userFriendService.confirmBind(userId,friendId));
    }


    @GetMapping("/findFriends")
    @ApiOperation(value = "查询亲友列表",response = VoFriend.class)
    @Log(name = "用户亲友",value = "查询亲友列表")
    public ApiResult findFriend(HttpServletRequest request){
        long userId = Long.parseLong(request.getHeader("id"));


        return ResultUtil.result(userFriendService.findFriend(userId));
    }


    @DeleteMapping("/delBind/{friendId}")
    @ApiOperation("删除亲友")
    @Log(name = "用户亲友",value = "删除亲友")
    public ApiResult delFriend(HttpServletRequest request, @PathVariable long friendId){
        long userId = Long.parseLong(request.getHeader("id"));
        return ResultUtil.result(userFriendService.delFriend(userId,friendId));
    }



    @GetMapping("/findFriendInfo/{friendId}")
    @ApiOperation(value = "查询亲友基本信息",response = VoUserInfo.class)
    @Log(name = "用户亲友",value = "查询亲友基本信息")
    public ApiResult findFriendInfo(@ApiParam(name = "friendId",value = "亲友ID")
                                        @PathVariable long friendId){


        return ResultUtil.result(userFriendService.findFriendInfo(friendId));
    }


    //根据USERID查询亲友疾病列表
    @GetMapping("/fallIllList/{friendId}")
    @ApiOperation(value = "分页查询亲友疾病信息列表", response = VoFallIllList.class)
    @Log(name = "用户亲友", value = "分页查询亲友疾病信息列表")
    @ApiImplicitParam(name = "json", value = "分页查询", required = true, defaultValue = "{\"currentPage\":1,\"pageSize\":10}")
    public ApiResult findFallIll(String json ,@PathVariable long friendId) {

        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        oldPager.setParameter(friendId);

        return ResultUtil.result(userFriendService.findFallIll(oldPager));
    }

    //根据USERID查询亲友检测记录


}
