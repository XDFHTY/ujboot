package com.cj.sim.controller;


import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sim.entity.Conversation;
import com.cj.sim.entity.GroupConversion;
import com.cj.sim.entity.RespConverstion;
import com.cj.sim.service.ConversationService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会话控制器
 * Created by XD on 2019/3/4.
 */
@RestController
@RequestMapping(value = "/s-im/api/v1/conversation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "公共接口：会话管理")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    /**
     * 创建会话
     *
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建会话",response = RespConverstion.class)
    @ApiImplicitParam(name = "map",value = "fromUser=会话创建者(只能是用户)  toUser=会话被创建者(只能是医生)" +
            " type=咨询对象类型 1-医生 2-咨询员" +
            "   {\"fromUser\":\"a1\",\"toUser\":\"b1\"，\"type\":\"1\"}",required = true)
    @ResponseBody
    @Log(name = "会话服务", value = "创建会话")
    public ApiResult create(@RequestBody Map map, HttpServletRequest request) {
        ApiResult apiResult;
        RespConverstion respConverstion = conversationService.createConversation(map,request);
        if(respConverstion != null){
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(respConverstion);
        }else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }


    /**
     * 结束会话
     */
//    @PutMapping("/end")
//    @ApiOperation("结束会话")
//    @Log(name = "会话服务", value = "结束会话")
//    @ApiImplicitParam(name = "conversationId",value = "conversationId=会话id", defaultValue = "1",required = true)
//    @ResponseBody
//    public ApiResult end( Long conversationId, HttpServletRequest request) {
//        ApiResult apiResult;
//        int i = conversationService.endConversation(conversationId,request);
//        if(i > 0){
//            apiResult = ApiResult.SUCCESS();
//            apiResult.setData(i);
//        }else {
//            apiResult = ApiResult.FAIL();
//        }
//        return apiResult;
//    }


    /**
     * 删除会话
     * 删除的会话必定是已结束的  未结束的不一定就删除
     * 两个用户之间可能存在多个已结束的会话，所以 删除就要删除全部结束的会话
     */
//    @DeleteMapping("/delete")
//    @ApiOperation("删除会话")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "conversationId",value = "conversationId=会话ID", defaultValue = "1",required = true),
//            @ApiImplicitParam(name = "user",value = "user=操作人", defaultValue = "a1",required = true),
//    })
//    @ResponseBody
//    @Log(name = "会话服务", value = "删除会话")
//    public ApiResult delete( Long conversationId,String user,HttpServletRequest request) {
//        ApiResult apiResult;
//        int i = conversationService.deleteConversation(conversationId,user);
//        if(i > 0){
//            apiResult = ApiResult.SUCCESS();
//            apiResult.setData(i);
//        }else {
//            apiResult = ApiResult.FAIL();
//        }
//        return apiResult;
//    }


    /**
     * 查询会话列表 未结束的  已结束的
     */
    @GetMapping("/getList")
    @ApiOperation(value = "查询会话列表",response = Conversation.class)
    @ApiImplicitParam(name = "json",value = "userName=环信id  endState=状态 0-已结束 1-正常",required = true,
            defaultValue = "{\"userName\":\"557\",\"endState\":\"1\"}")
    @ResponseBody
    @Log(name = "会话服务", value = "查询会话列表")
    public ApiResult getList(String json,HttpServletRequest request) {
        Map map = JsonUtil.gson.fromJson(json,Map.class);


        List<Conversation> list = conversationService.getList(map,request);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }

    /**
     * 查询团聊天记录的时候调用此接口
     * 根据医生id查询群会话列表
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/getTeamMsgList/{userId}")
    @ApiOperation(value = "根据医生id查询群会话列表",response = Conversation.class)
    @Log(name = "会话服务", value = "根据医生id查询群会话列表")
    public ApiResult getTeamMsgList(@PathVariable(value = "userId") Long userId,HttpServletRequest request) {
        List<Conversation> list = conversationService.getTeamMsgList(userId,request);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 根据医生id 和 患者id 查询会话列表
     */
    @GetMapping("/getMsgListByIds")
    @ApiOperation(value = "根据医生id和患者id查询会话列表",response = Conversation.class)
    @Log(name = "会话服务", value = "根据医生id和患者id查询会话列表")
    @ApiImplicitParam(name = "json",value = "userId=患者id  doctorId=医生id  type=发起方 1-患者 2-医生",required = true,
            defaultValue = "{\"userId\":557,\"doctorId\":690,\"type\":1}")
    public ApiResult getMsgListByIds(String json,HttpServletRequest request) {
        Map map = JsonUtil.gson.fromJson(json,Map.class);
        List<Conversation> list = conversationService.getMsgListByIds(map,request);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 根据群聊id 查询所有用户
     */
    @ApiOperation(value = "根据环信群聊id查询所有用户")
    @GetMapping("/findUserInfo/{hxGroupId}")
    @Log(name = "会话服务",value = "根据环信群聊id查询所有用户")
    public ApiResult findUserInfo(@PathVariable(value = "hxGroupId")Long hxGroupId,
                                  HttpServletRequest request){
        ApiResult apiResult = conversationService.findUserInfo(hxGroupId,request);
        return apiResult;
    }


    @ApiOperation(value = "根据团队id和用户id查询群会话",response = Conversation.class)
    @GetMapping("/findGroupConversation/{teamId}/{userId}")
    @Log(name = "会话服务",value = "根据团队id和用户id查询群会话")
    public ApiResult findGroupConversation(@PathVariable(value = "teamId")Long teamId,
                                           @PathVariable(value = "userId")Long userId,
                                  HttpServletRequest request){
        ApiResult apiResult = conversationService.findGroupConversation(teamId,userId,request);
        return apiResult;
    }
}
