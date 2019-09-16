package com.cj.suser.controller;

import com.cj.core.entity.FallIll;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.suser.domain.DtoFallIll;
import com.cj.suser.domain.VoFallIllById;
import com.cj.suser.domain.VoFallIllList;
import com.cj.suser.service.FallIllService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/s-user/api/v1/healthy")
@Api(tags = "用户端: 用户健康档案")
public class FallIllController {

    @Resource
    private FallIllService fallIllService;

    //分页查询用户疾病信息列表

    @GetMapping("/fallIllList")
    @ApiOperation(value = "分页查询用户疾病信息列表", response = VoFallIllList.class)
    @Log(name = "用户健康档案", value = "分页查询用户疾病信息列表")
    @ApiImplicitParam(name = "json", value = "分页查询", required = true, defaultValue = "{\"currentPage\":1,\"pageSize\":10}")
    public ApiResult findFallIll(String json, HttpServletRequest request) {

        return ResultUtil.result(fallIllService.findFallIll(json, request));
    }


    //根据疾病ID查询疾病详情
    @GetMapping("/fallIllById")
    @ApiOperation(value = "根据疾病ID查询疾病详情", response = VoFallIllById.class)
    @Log(name = "用户健康档案", value = "根据疾病ID查询疾病详情")
    @ApiImplicitParam(name = "fallIllId", value = "疾病列表ID", required = true)
    public ApiResult findFallIllById(long fallIllId) {
        return ResultUtil.result(fallIllService.findFallIllById(fallIllId));
    }

    //新增疾病
    @PostMapping("/fallIll")
    @ApiOperation("新增疾病")
    @Log(name = "用户健康档案", value = "新增疾病")
    public ApiResult addFallIll(HttpServletRequest request,
                                @ApiParam(name = "fallIll", value = "{\n" +
                                        "  \"addTime\": \"2019-03-16 07:56:20\",\n" +
                                        "  \"allergy\": \"0\",\n" +
                                        "  \"checkTime\": \"2019-03-16 07:40:20\",\n" +
                                        "  \"chronic\": \"慢行器官炎\",\n" +
                                        "  \"fallMsg\": \"测试病情\",\n" +
                                        "  \"fallIllImgs\": [\n" +
                                        "    {\n" +
                                        "      \"routeUrl\": \"http://pr.appshowings.com:19010/s-file/img/2d45378a-d1e5-4741-a3e1-19051c144d39.png\"\n" +
                                        "    }\n" +
                                        "  ],\n" +
                                        "  \"hypertension\": \"0\",\n" +
                                        "  \"nephropathyType\": \"慢性\"\n" +
                                        "}", required = true)
                                @RequestBody DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException {

        return ResultUtil.result(fallIllService.addFallIll(request, dtoFallIll));
    }


    //修改疾病
    @PutMapping("/fallIll")
    @ApiOperation("修改疾病")
    @Log(name = "用户健康档案", value = "修改疾病")
    public ApiResult updateFallIll(HttpServletRequest request,
                                   @ApiParam(name = "fallIll", value = "要保存的图片全部上传，没传的删除", required = true)
                                   @RequestBody DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException {

        return ResultUtil.result(fallIllService.updateFallIll(request, dtoFallIll));

    }


    //根据疾病ID删除疾病（非物理删除）
    @DeleteMapping("/fallIll")
    @ApiOperation("删除疾病")
    @Log(name = "用户健康档案", value = "删除疾病")
    @ApiImplicitParam(name = "fallIllId", value = "疾病列表ID", required = true)
    public ApiResult deleteFallIll(HttpServletRequest request, long fallIllId) {
        return ResultUtil.result(fallIllService.deleteFallIll(request, fallIllId));
    }
}
