package com.cj.sbasic.controller;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Help;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.HelpService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 刘磊
 * @Description: 帮助管理前台
 * @date： 2019/3/7 17:34
 **/
@RestController
@RequestMapping("/s-basic/api/v1/help")
@Api(tags = "用户端: 帮助管理")
public class HelpFrontController {
    @Autowired
    private HelpService helpService;

    /**
     * 分页查询帮助文章
     * @param json
     * @return
     */
    @ApiOperation(value = "用户端查询帮助文章列表",response = Help.class)
    @GetMapping("/findPageHelpFront")
    @ApiImplicitParam(name = "json" ,value =" type = 端口类型 1-个人 2-医生 ",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"type\":\"1\"}}")
    @Log(name = "帮助管理",value = "用户端查询帮助文章列表")
    public ApiResult findPageHelpFront(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = helpService.getHelpPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }

    /**
     * 根据id查询内容
     * @param helpId
     * @return
     */
    @ApiOperation(value = "根据id查询内容",response = Help.class)
    @GetMapping("/findHelpByIDFront")
    @Log(name = "帮助管理",value = "根据id查询内容")
    public ApiResult findHelpByID(Long helpId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(helpService.getHelpByID(helpId));
        return apiResult;
    }
}
