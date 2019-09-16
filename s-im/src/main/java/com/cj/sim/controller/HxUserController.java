package com.cj.sim.controller;


import com.cj.core.domain.Customer;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sim.service.HxUserService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 环信用户模块 控制器
 * Created by XD on 2019/2/28.
 */

@RestController
@RequestMapping(value = "/s-im/api/v1/hxUser",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "公共接口：环信用户管理")
public class HxUserController {

    @Autowired
    private HxUserService hxUserService;


    /**
     * 注册环信用户
     * 返回注册成功后的用户名  返回null则是注册失败
     * @param customer userName
     * @param customer password
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册环信")
    @Log(name = "消息服务", value = "注册环信用户")
    public ApiResult register(@RequestBody Customer customer) {
        ApiResult apiResult;
        String id = hxUserService.register(String.valueOf(customer.getCustomerId()), customer.getPassword());
        if (id!=null){
           apiResult = ApiResult.SUCCESS();
           apiResult.setData(id);
        }else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }


}
