package com.cj.suser.controller;

import com.cj.core.entity.Feedback;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.suser.domain.VoFallIllById;
import com.cj.suser.domain.VoFeed;
import com.cj.suser.service.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/s-user/api/v1/feed")
@Api(tags = "用户端&医生端: 用户投诉")
public class FeedController {


    @Resource
    private FeedService feedService;

    @PostMapping("/addFeed")
    @ApiOperation(value = "添加投诉信息")
    @Log(name = "用户投诉", value = "添加投诉信息")
    public ApiResult addFeed(@ApiParam(name = "feedback",value = "投诉内容,图片只需要传url，如下：{\n" +
            "  \"content\": \"string\",\n" +
            "  \"email\": \"string\",\n" +
            "  \"feedbackPictures\": [\n" +
            "    {\n" +
            "      \"routeUrl\": \"string\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"object\": \"0\",\n" +
            "  \"phone\": \"string\",\n" +
            "  \"time\": \"2019-04-04 16:00:00\"\n" +
            "}",required = true)
                             @RequestBody VoFeed voFeed,
                             HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        return ResultUtil.result(feedService.addfeed(voFeed,request));
    }


}
