package com.cj.sim.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;

import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.sim.entity.ChatMessage;
import com.cj.sim.service.MsgService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 聊天消息 控制器
 * Created by XD on 2019/2/28.
 */

@RestController
@RequestMapping(value = "/s-im/api/v1/msg",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "公共接口：消息管理")
public class MsgController {

    @Autowired
    private MsgService msgService;

    /**
     * 存放聊天记录
     * @param chatMessageList
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("存放聊天记录")
    @Log(name = "消息服务", value = "保存聊天记录")
    public ApiResult insert(@RequestBody List<ChatMessage> chatMessageList, HttpServletRequest request) {
        int i = msgService.insert(chatMessageList,request);
        ApiResult apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 获取聊天记录
     * @param
     * @return
     */
    @GetMapping("/getMsg")
    @ApiOperation(value = "获取单独聊天的记录", response = ChatMessage.class)
    @ApiResponse(code = 0, message ="",response = OldPager.class)
    @ApiImplicitParam(name = "json",value = "startDate=开始时间(非必传) endDate=结束时间(非必传)  userA=聊天对象A  userB=聊天对象B",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"startDate\":\"2019-02-28 16:55:32\",\"userA\":\"a1\"" +
                    ",\"userB\":\"b1\"}}")
    @Log(name = "消息服务", value = "查询聊天记录")
    public ApiResult getMsg(String json){

        OldPager oldPager1 = JsonUtil.gson.fromJson(json,OldPager.class);

        OldPager oldPager = new OldPager();
        oldPager.setCurrentPage(oldPager1.getCurrentPage());
        oldPager.setPageSize(oldPager1.getPageSize());
        oldPager.setParameters(oldPager1.getParameters());
        OldPager oldPager2 = msgService.getMsg(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;

    }

    @GetMapping("/getGroupMsg")
    @ApiOperation(value = "获取群聊的记录", response = ChatMessage.class)
    @ApiResponse(code = 0, message ="",response = OldPager.class)
    @ApiImplicitParam(name = "json",value = "startDate=开始时间(非必传) endDate=结束时间(非必传) hxGroupId=环信群聊Id",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":" +
                    "{\"startDate\":\"2019-02-28 16:55:32\",\"hxGroupId\":\"123456\"}}")
    @Log(name = "消息服务", value = "查询聊天记录")
    public ApiResult getGroupMsg(String json){

        OldPager oldPager1 = JsonUtil.gson.fromJson(json,OldPager.class);

        OldPager oldPager = new OldPager();
        oldPager.setCurrentPage(oldPager1.getCurrentPage());
        oldPager.setPageSize(oldPager1.getPageSize());
        oldPager.setParameters(oldPager1.getParameters());
        OldPager oldPager2 = msgService.getGroupMsg(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }


    /**
     * 根据一个环信id 查询 与他聊过天的人
     */
//    @GetMapping("/getUserListByHxId")
//    @ApiOperation(value = "根据环信id查询与他聊过天的人", response = ChatMessage.class)
//    @ApiResponse(code = 0, message ="",response = Pager.class)
//    @ApiImplicitParam(name = "json",value = "hxId=环信id ",required = true,
//            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"hxId\":\"a1\"}}")
//    @Log(name = "消息服务", value = "根据一个环信id查询与他聊过天的人")
//    public ApiResult getUserListByHxId(String json){
//
//
//        Pager pager2 = msgService.getUserListByHxId( JsonUtil.gson.fromJson(json, Pager.class));
//        ApiResult apiResult = ApiResult.SUCCESS();
//        apiResult.setData(pager2);
//        return apiResult;
//
//    }


    /**
     * 查询有没有资格退款
     * 群：
     * 用医生id和用户id 查询所有会话集合 拿到所有的hxGroupId
     * 用GroupId 和时间段查询 是否有消息记录
     * 个人：
     * 根据医生id和用户id查询这个时间段有没有聊天记录
     */
    @GetMapping("/getRefundEligibility")
    @ApiOperation(value = "查询退款资格")
    @Log(name = "消息服务", value = "查询退款资格")
    public ApiResult getRefundEligibility(String json){
        Map map = JsonUtil.gson.fromJson(json,Map.class);
        Double userId = (Double) map.get("userId");//用户id
        Double doctorId = (Double) map.get("doctorId");//医生id
        String startDate = (String) map.get("startDate");//开始时间
        String endDate = (String) map.get("endDate");//结束时间
        Boolean b = msgService.getRefundEligibility(userId.longValue(),doctorId.longValue(),startDate,endDate);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(b);
        return apiResult;
    }


}
