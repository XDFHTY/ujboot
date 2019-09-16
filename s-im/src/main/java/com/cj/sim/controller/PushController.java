package com.cj.sim.controller;

import cn.jpush.api.push.model.PushPayload;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sim.service.PushService;
import com.cj.sim.utils.jiguang.MsgPushUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 推送控制器
 * Created by XD on 2019/3/8.
 */
@RestController
@RequestMapping(value = "/s-im/api/v1/jPush", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "后台：系统管理-推送管理")
public class PushController {

    @Autowired
    private PushService pushService;


    /**
     * 给全部设备 全部用户 发送 通知
     * @param map alert 通知类容
     * @return
     */
    @PostMapping("/buildPushObjectAllAllAlert")
    @ApiOperation("给全部设备全部用户发送通知")
    @Log(name = "极光推送管理", value = "给全部设备全部用户发送通知")
    @ApiImplicitParam(name = "map",value = "alert=通知内容", required = true)
    public ApiResult buildPushObjectAllAllAlert(@RequestBody Map<String,String> map) {
        ApiResult apiResult;
        boolean b = pushService.buildPushObjectAllAllAlert(map.get("alert"),map.get("appType"));
        if (b){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 给全部设备 全部用户 发送通知和消息
     * @param map days  推送消息保留的天数
     * @param map content 消息内容
     * @param map alert 通知内容 内容可以为空字符串，则表示不展示到通知栏。
     * @return
     */
    @PostMapping("/sendAndroidAndIosMessageAndNotification")
    @ApiOperation("给全部设备全部用户发送通知和消息")
    @Log(name = "极光推送管理", value = "给全部设备全部用户发送通知和消息")
    @ApiImplicitParam(name = "map",value = "days=当用户无法接收推送时，这条推送保留的天数（最大10）  content=消息内容  alert=通知内容", required = true)
    public ApiResult sendAndroidAndIosMessageAndNotification(@RequestBody Map<String,String> map) {
        ApiResult apiResult;
        boolean b = pushService.sendAndroidAndIosMessageAndNotification(map.get("days"),map.get("content"),map.get("alert"),map.get("appType"));
        if (b){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }


    /**
     * 给全部设备 按别名 发送通知 和 参数
     * @param map days   String
     * @param map params map
     * @param map alert  String
     * @param map alias  List
     * @return
     */
    @PostMapping("/buildPushObjectAllAliasAlert")
    @ApiOperation("给全部设备按别名发送通知和参数")
    @Log(name = "极光推送管理", value = "给全部设备按别名发送通知和参数")
    @ApiImplicitParam(name = "map",value = "days=当用户无法接收推送时，这条推送保留的天数(最大10)  params=自定义kv  alert=通知内容" +
                    "  alias=用户的别名集合(当前用户别名和用户id一致)", required = true)
    public ApiResult buildPushObjectAllAliasAlert(@RequestBody Map map) {
        ApiResult apiResult;
        boolean b = pushService.buildPushObjectAllAliasAlert((String) map.get("days"),
                (Map) map.get("params"),(String) map.get("alert"),(List<String>)map.get("alias"), (String) map.get("appType"));
        if (b){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }


    /**
     * 给全部设备按标签发送通知和参数
     * @param map days String
     * @param map params Map
     * @param map alert String
     * @param map tag List
     * @param map type int
     * @return
     */
    @PostMapping("/buildPushObjectAllOrAndTagAlert")
    @ApiOperation("给全部设备按标签发送通知和参数")
    @Log(name = "极光推送管理", value = "给全部设备按标签发送通知和参数")
    @ApiImplicitParam(name = "map",value = "days=当用户无法接收推送时，这条推送保留的天数（最大10）  params=自定义kv  alert=通知内容" +
                    " tag=用户的标签集合 最大支持20个标签  type= 1-标签之间取并集  2-标签之间取交集",required = true)
    public ApiResult buildPushObjectAllOrAndTagAlert(@RequestBody Map map) {
        ApiResult apiResult;
        List<String> tag = (List<String>) map.get("tag");
        if (tag == null || tag.size()==0 || "".equals(tag.get(0))){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("请确认标签");
            return apiResult;
        }
        String type = (String) map.get("type");
        String days = (String) map.get("days");
        if (tag.size() <= 20){//标签不能超过20个
            pushService.buildPushObjectAllOrAndTagAlert(days, (Map) map.get("params"),(String) map.get("alert"),tag,
                    Integer.valueOf(type), (String) map.get("appType"));
        }else {//拆分
            List<List<String>> partition = ListUtils.partition(tag, 20);
            for (List<String> list:partition){
                pushService.buildPushObjectAllOrAndTagAlert(days, (Map) map.get("params"),(String) map.get("alert"),list,
                        Integer.valueOf(type), (String) map.get("appType"));
            }

        }


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


}

