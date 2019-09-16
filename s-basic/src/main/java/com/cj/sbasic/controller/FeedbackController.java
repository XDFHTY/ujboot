package com.cj.sbasic.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.FeedbackService;
import com.cj.sbasic.vo.FeedbackVO;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/s-basic/api/v1/feedback")
@Api(tags = "后台: 投诉管理")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 按条件分页查询所有的投诉信息
     * @param json
     * @return
     */
    @ApiOperation(value = "分页查询投诉信息列表",response = FeedbackVO.class)
    @GetMapping("/feedbacks")
    @ApiImplicitParam(name = "json" ,value =" timeDown = 开始时间(格式：2018-01-01 00:00:00)," +
            "timeUp = 结束时间,object = 投诉类别 0-设备 1-医生 2-平台," +
            "state = 状态 0-未处理 1-已处理",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"timeDown\":\"\",\"timeUp\":\"\",\"object\":\"\"," +
                    "\"state\":\"\"}}")
    @Log(name = "投诉管理",value = "分页查询投诉信息列表")
    public ApiResult getAllFeedbackPage(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","true");//分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(feedbackService.getFeedbackPage(oldPager));
        return apiResult;
    }

    /**
     * 通过投诉表id更改状态
     * @param id
     * @return
     */
    @ApiOperation(value = "处理投诉")
    @PutMapping("/dealFeedback/{id}")
    @Log(name = "投诉管理",value = "处理投诉")
    public ApiResult updateFeedbackStateById(@PathVariable Long id){
        int result = feedbackService.updateFeedbackStateById(id);
        if (result == 1) {
            ApiResult apiResult = ApiResult.SUCCESS();
            return apiResult;
        } else {
            ApiResult apiResult = ApiResult.FAIL();
            apiResult.setMsg("处理失败");
            return apiResult;
        }
    }

    /**
     * 导出反馈记录
     * @param json
     * @return
     */
    @ApiOperation(value = "导出反馈记录")
    @GetMapping("/exportFeedback")
    @ApiImplicitParam(name = "json" ,value =" timeDown = 开始时间(格式：2018-01-01 00:00:00),timeUp = 结束时间,object = 投诉类别 0-设备 1-医生 2-平台,state = 状态 0-未处理 1-已处理",required = true,
            defaultValue = "{\"parameters\":{\"timeDown\":\"\",\"timeUp\":\"\",\"object\":\"\",\"state\":\"\"}}")
    @Log(name = "投诉管理",value = "导出反馈记录")
    public ApiResult exportFeedback(String json, HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","false");//不分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(feedbackService.exportFeedback(feedbackService.getFeedbackPage(oldPager),response));
        return apiResult;
    }
}
