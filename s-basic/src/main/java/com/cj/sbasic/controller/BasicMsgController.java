package com.cj.sbasic.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.domain.Msg;
import com.cj.sbasic.domain.MsgSendReq;
import com.cj.sbasic.service.BasicMsgService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/s-basic/api/v1/msg")
@Api(tags = "后台: 消息管理")
public class BasicMsgController {
    @Autowired
    private BasicMsgService msgService;
    /**
     * 条件分页查询出消息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "分页查询推送消息列表",response = Msg.class)
    @GetMapping("/msgs")
    @ApiImplicitParam(name = "json" ,value ="msgType=消息类型(0-广告消息 1-活动消息) " +
            "userType=推送对象(1-用户 2-医生) minAge=推送对象类型为用户的时候的最低年龄 maxAge=\n" +
            "\t推送对象类型为用户的时候的最高年龄 startTime=筛选发布时间的开始时间(格式：2019-01-01 00:00:00) " +
            "endTime=筛选发布时间的结束时间(格式：2019-01-01 00:00:00) department=推送对象类型为医生的时候的科室 " +
            "title=推送对象类型为医生的时候的职称",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"msgType\":\"\",\"userType\":\"1\",\"minAge\":\"\",\"maxAge\":\"\",\"startTime\":\"\",\"endTime\":\"\",\"department\":\"\",\"title\":\"\"}}")
    @Log(name = "消息管理",value = "分页查询推送消息列表")
    public ApiResult getMsgsPage(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        oldPager.getParameters().put("objectStr",generateObjectStrPager(oldPager));//推送对象描述
        oldPager.getParameters().put("isPage","true");//分页
        ApiResult apiResult = ApiResult.SUCCESS();
        //按推送对象类型分别查询
        apiResult.setData(msgService.getMsgPage(oldPager));
        return apiResult;
    }

    /**
     * 根据消息id删除消息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除消息")
    @DeleteMapping("/deleteMsg")
    @Log(name = "消息管理",value = "删除消息")
    public ApiResult deleteMsgById(Long id){
        int result = msgService.deleteMsgById(id);
        if (result == 1){//删除成功
            ApiResult apiResult = ApiResult.SUCCESS();
            return apiResult;
        } else {
            ApiResult apiResult = ApiResult.FAIL();
            apiResult.setMsg("删除失败");
            return apiResult;
        }
    }

    /**
     * 导出消息列表
     * @param json
     * @return
     */
    @ApiOperation(value = "导出消息列表")
    @GetMapping("/exportMsgs")
    @ApiImplicitParam(name = "json" ,value ="msgType=消息类型(0-广告消息 1-活动消息) " +
            "userType=推送对象(1-用户 2-医生) minAge=推送对象类型为用户的时候的最低年龄 maxAge=\n" +
            "\t推送对象类型为用户的时候的最高年龄 startTime=筛选发布时间的开始时间(格式：2019-01-01 00:00:00) endTime=筛选发布时间的结束时间(格式：2019-01-01 00:00:00) department=推送对象类型为医生的时候的科室 title=推送对象类型为医生的时候的职称",required = true,
            defaultValue = "{\"parameters\":{\"msgType\":\"\",\"userType\":\"1\",\"minAge\":\"\",\"maxAge\":\"\",\"startTime\":\"\",\"endTime\":\"\",\"department\":\"\",\"title\":\"\"}}")
    @Log(name = "消息管理",value = "导出消息列表")
    public ApiResult exportMsgs(String json, HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        oldPager.getParameters().put("objectStr",generateObjectStrPager(oldPager));//推送对象描述
        oldPager.getParameters().put("isPage","false");//不分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(msgService.exportMsgs(msgService.getMsgPage(oldPager),response));
        return apiResult;
    }

    /**
     * 根据消息id获取消息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据消息id获取消息",response = Msg.class)
    @GetMapping("/msg")
    @Log(name = "消息管理",value = "根据消息id获取消息")
    public ApiResult getMsgById(Long id){
        Msg msg = msgService.getMsgById(id);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(msg);
        return apiResult;
    }

    /**
     * 推送消息
     * @param msgSendReq
     * @return
     */
    @ApiOperation(value = "推送消息")
    @PostMapping("/sendMsg")
    @Log(name = "消息管理",value = "推送消息")
    public ApiResult sendMsg(@ApiParam(name = "admin",value = "msgType=消息类型(0-广告消息 1-活动消息) " +
            "userType=推送对象(1-用户 2-家庭医生 3-肾病医生 4-营养师 " +
            "5-乡干部 6-卫健委干部 7-离退休干部 8-护士 9-健康管理师) " +
            "minAge=推送对象类型为用户的时候的最低年龄 " +
            "maxAge=推送对象类型为用户的时候的最高年龄 " +
            "department=推送对象类型为医生的时候的科室 " +
            "title=推送对象类型为医生的时候的职称 " +
            "msgTitle=消息标题 msgContent=消息内容 " +
            "days=当用户无法接收推送时，这条推送保留的天数(最大10)" +
            "{\n" +
            "  \"msgType\": \"\",\n" +
            "  \"userType\": \"\",\n" +
            "  \"minAge\": \"\"\n" +
            "  \"maxAge\": \"\"\n" +
            "  \"department\": \"\"\n" +
            "  \"title\": \"\"\n" +
            "  \"days\": \"\"\n" +
            "  \"msgTitle\": \"\"\n" +
            "  \"msgContent\": \"\"\n" +
            "}",required = true)@RequestBody MsgSendReq msgSendReq){
        ApiResult apiResult = ApiResult.FAIL();//默认就是失败
        try{
            long a1 = System.currentTimeMillis();
            boolean isSuccessSend = msgService.sendMsg(msgSendReq);
            long a2 = System.currentTimeMillis();
            System.out.println("+++++++++++++++++++++++");
            System.out.println("总耗时："+(a2-a1));
            System.out.println("+++++++++++++++++++++++");
            if (isSuccessSend) {
                apiResult = ApiResult.SUCCESS();
                apiResult.setMsg("推送成功");
            }
            return apiResult;
        } catch (RuntimeException e){
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }
    }


    /**
     * 生成推送对象描述字符串
     * @param oldPager
     * @return
     */
    private String generateObjectStrPager(OldPager oldPager){
        String objectStr = "";
        if ("1".equals(oldPager.getParameters().get("userType"))){
            if(oldPager.getParameters().get("minAge").equals("")&& oldPager.getParameters().get("maxAge").equals("")){
                objectStr = "所有用户";
            }else{
                objectStr += "年龄在" + oldPager.getParameters().get("minAge") + "-" + oldPager.getParameters().get("maxAge") + "周岁的用户";
            }
        } else if ("2".equals(oldPager.getParameters().get("userType"))){
            if (!"".equals(oldPager.getParameters().get("department"))){
                objectStr += "科室是"+oldPager.getParameters().get("department");
            }
            if (!"".equals(oldPager.getParameters().get("title"))){
                objectStr += "职称是" + oldPager.getParameters().get("title");
            }
            if ("".equals(oldPager.getParameters().get("title")) && "".equals(oldPager.getParameters().get("department"))){
                objectStr += "所有";
            }
            objectStr += "的家庭医生";
        }else if ("3".equals(oldPager.getParameters().get("userType"))){
            objectStr = "专科医生";
        }else if ("4".equals(oldPager.getParameters().get("userType"))){
            objectStr = "营养师" ;
        }else if ("5".equals(oldPager.getParameters().get("userType"))){
            objectStr = "乡干部";
        }else if ("6".equals(oldPager.getParameters().get("userType"))){
            objectStr = "卫健委干部";
        }else if ("7".equals(oldPager.getParameters().get("userType"))){
            objectStr = "离退休干部";
        }else if ("8".equals(oldPager.getParameters().get("userType"))){
            objectStr = "护士";
        }else if ("9".equals(oldPager.getParameters().get("userType"))){
            objectStr = "健康管理师";
        }
        return objectStr;
    }
}
