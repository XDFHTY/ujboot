package com.cj.sbasic.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.UserInfoService;
import com.cj.sbasic.vo.UserCheckVO;
import com.cj.sbasic.vo.UserDetailVO;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/s-basic/api/v1/check/user")
@Api(tags = "后台: 审核用户")
public class UserCheckController {
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 按条件分页查询所有的待审核的用户列表
     * @param json
     * @return
     */
    @ApiOperation(value = "分页查询用户审核信息列表",response = UserCheckVO.class)
    @GetMapping("/userChecks")
    @ApiImplicitParam(name = "json" ,value =" provinceId = 省Id,cityId = 市Id,areaId = 区Id,state = 审核状态 审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"provinceId\":\"\",\"cityId\":\"\",\"areaId\":\"\",\"state\":\"1\"}}")
    @Log(name = "审核管理",value = "分页查询用户审核信息列表")
    public ApiResult getAllUserCheck(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","true");//分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userInfoService.getUserCheckPage(oldPager));
        return apiResult;
    }

    /**
     * 根据用户id获取用户的详情信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据用户id获取用户的详情信息",response = UserDetailVO.class)
    @GetMapping("/getUserDetail")
    @Log(name = "审核管理",value = "根据用户id获取用户的详情信息")
    public ApiResult getUserDetailById(Long id){
        UserDetailVO userDetailVO = userInfoService.getUserDetailById(id);
        if (userDetailVO != null){
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setData(userDetailVO);
            return apiResult;
        } else {
            ApiResult apiResult = ApiResult.FAIL();
            apiResult.setMsg("未能找到对应的用户详情信息");
            return apiResult;
        }
    }

    /**
     * 导出用户审核信息
     * @param json
     * @return
     */
    @ApiOperation(value = "导出用户审核信息")
    @GetMapping("/exportUserCheck")
    @ApiImplicitParam(name = "json" ,value =" provinceId = 省Id,cityId = 市Id,areaId = 区Id,state = 审核状态 审核状态 0-已删除 1-正常 2-未审核 3-审核中 4-审核不通过",required = true,
            defaultValue = "{\"parameters\":{\"provinceId\":\"\",\"cityId\":\"\",\"areaId\":\"\",\"state\":\"1\"}}")
    @Log(name = "审核管理",value = "导出用户审核信息")
    public ApiResult exportUserCheck(String json, HttpServletResponse response){
        OldPager oldPager = JsonUtil.gson.fromJson(json, OldPager.class);
        oldPager.getParameters().put("isPage","false");//不分页
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(userInfoService.exportUserCheck(userInfoService.getUserCheckPage(oldPager),response));
        return apiResult;
    }
}
