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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 帮助管理后台
 * @date： 2019/3/7 17:34
 **/
@RestController
@RequestMapping("/s-basic/api/v1/help")
@Api(tags = "后台: 帮助管理")
public class HelpAfterController {
    @Autowired
    private HelpService helpService;
    /**
     * 分页查询帮助文章
     * @param json
     * @return
     */
    @ApiOperation(value = "管理端查询帮助文章列表",response = Help.class)
    @GetMapping("/findPageHelpAfter")
    @ApiImplicitParam(name = "json" ,value =" type = 端口类型 1-个人 2-医生 ",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"type\":\"1\"}}")
    @Log(name = "帮助管理",value = "管理端查询帮助文章列表")
    public ApiResult findPageHelpAfter(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = helpService.getHelpPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 根据id查询
     */
    @ApiOperation(value = "管理端根据id查询帮助文章",response = Help.class)
    @GetMapping("/findHelpByIDAfter")
    @Log(name = "帮助管理",value = "根据id查询帮助文章")
    public ApiResult findHelpByIDAfter(Long helpId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(helpService.getHelpByID(helpId));
        return apiResult;
    }
    /**
     * 添加帮助文章
     */
    @ApiOperation(value = "添加帮助文章",response = Help.class)
    @PostMapping("/insertHelp")
    @ApiImplicitParam(name = "json" ,value ="type=端口类型 1-个人 2-医生 problem=问题 answer=答案 ",required = true,
            defaultValue = "{\"problem\":问题,\"answer\":答案,\"type\":1}")
    @Log(name ="帮助管理",value = "添加帮助文章")
    public ApiResult insertHelp(@RequestBody Help json){
//        Help help = JsonUtil.gson.fromJson(json,Help.class);
        json.setIsDisplay("1");
        int i = helpService.insert(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 修改帮助文章
     */
    @ApiOperation(value = "修改帮助文章",response = Help.class)
    @PutMapping("/updateHelp")
    @ApiImplicitParam(name="json",value = "type=端口类型 1-个人 2-医生 helpId=帮助文章ID problem=问题 answer=答案",required = true,
            defaultValue ="{\"helpId\":1,\"problem\":问题,\"answer\":答案,\"type\":1}" )
    @Log(name = "帮助管理",value = "修改帮助文章")
    public ApiResult updateHelp(@RequestBody Help json){
//        Help help = JsonUtil.gson.fromJson(json,Help.class);
        int i = helpService.update(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 删除帮助文章
     */
    @ApiOperation(value = "管理端根据id删除帮助文章",response = Help.class)
    @DeleteMapping("/deleteHelpByID")
    @Log(name = "帮助管理",value = "删除帮助文章")
    public ApiResult deleteHelpByID(@RequestBody List<Long> list){
        int i = 0;
        for(Long helpId : list){
           i += helpService.delete(helpId);
        }
        if(i==list.size()){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
}
