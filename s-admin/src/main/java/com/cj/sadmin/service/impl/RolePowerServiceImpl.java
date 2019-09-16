package com.cj.sadmin.service.impl;

import com.cj.core.entity.AuthRole;
import com.cj.core.exception.MyException;
import com.cj.common.mapper.AuthCustomerRoleMapper;
import com.cj.common.mapper.AuthModularMapper;
import com.cj.common.mapper.AuthRoleMapper;
import com.cj.common.mapper.AuthRoleModularMapper;
import com.cj.common.service.AuthRoleModularService;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.AuthModulars;
import com.cj.core.domain.AuthRoleModulars;
import com.cj.core.util.JsonUtil;
import com.cj.sadmin.domain.FindAllRoleResp;
import com.cj.sadmin.domain.UpdateModularByRoleIdReq;
import com.cj.sadmin.service.RolePowerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RolePowerServiceImpl implements RolePowerService {


    @Resource
    private AuthRoleMapper authRoleMapper;

    @Resource
    private AuthModularMapper authModularMapper;

    @Resource
    private AuthCustomerRoleMapper authCustomerRoleMapper;

    @Resource
    private AuthRoleModularMapper authRoleModularMapper;

    @Resource
    private AuthRoleModularService authRoleModularService;


    Gson gson = JsonUtil.gson;

    @Resource
    private RedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ApiResult findAllRole() {
        String str = "";
        try {

            str = (String) stringRedisTemplate.opsForValue().get("authModulars");
        } catch (RedisConnectionFailureException e) {
            System.out.println("========>>>Redis连接失败");
            throw new MyException( "Redis连接失败");
        }

        //查询所有角色
        List<AuthRole> authRoles = authRoleMapper.findAllAuthRole();
        //获取系统内所有权限
//        AuthModulars authModulars = (AuthModulars) MemoryData.getRoleModularMap().get("authModulars");


        AuthModulars authModulars = gson.fromJson(str, AuthModulars.class);


        FindAllRoleResp findAllRoleResp = new FindAllRoleResp();
        findAllRoleResp.setAuthRoles(authRoles);
        findAllRoleResp.setAuthModulars(authModulars);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(findAllRoleResp);
        return apiResult;
    }

    @Override
    public ApiResult addRole(AuthRole authRole) {
        ApiResult apiResult = null;

        AuthRole oldAuthRole = authRoleMapper.findAuthRoleByName(authRole.getRoleName());
        if (oldAuthRole != null) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("角色已存在");

            return apiResult;
        }

        int i = authRoleMapper.insertSelective(authRole);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    @Override
    public ApiResult deleteRole(Long roleId) {
        ApiResult apiResult = null;

        int i = authCustomerRoleMapper.findCustomerNumByRoleId(roleId);
        if (i > 0) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("此角色已有用户在使用");
            throw new MyException(apiResult);
        }
        //物理删除角色信息
        int j = authRoleMapper.deleteByPrimaryKey(roleId);

        if (j > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }

    //根据角色ID查询权限集合
    @Override
    public ApiResult findModularByRoleId(String json) {
        ApiResult apiResult = ApiResult.SUCCESS();
        Gson gson = JsonUtil.gson;
        Map map = gson.fromJson(json, Map.class);
        long roleId = ((Double) map.get("roleId")).longValue();


        List<AuthRoleModulars> authRoleModularss = gson.fromJson(
                stringRedisTemplate.opsForValue().get("modulars").toString().trim(),
                new TypeToken<List<AuthRoleModulars>>() {
                }.getType());

        authRoleModularss
                .forEach((AuthRoleModulars authRoleModulars) -> {
                    if (authRoleModulars.getRoleId() == roleId) {
                        apiResult.setData(authRoleModulars.getAuthModulars());
                    }
                });


        return apiResult;
    }

    @Override
    public ApiResult updateModularByRoleId(UpdateModularByRoleIdReq updateModularByRoleIdReq) {
        ApiResult apiResult = null;
        Gson gson = JsonUtil.gson;

        long roleId = updateModularByRoleIdReq.getRoleId();
        List<Long> modularIds = updateModularByRoleIdReq.getModularIds();

        Map map = new HashMap();
        map.put("roleId", roleId);
        map.put("modularIds", modularIds);


        int i = authRoleModularMapper.deleteModularByRoleId(roleId);

        int j = authRoleModularMapper.addModularByRoleId(map);

        if (j > 0) {
            apiResult = ApiResult.SUCCESS();
            //更新系统权限
            authRoleModularService.findRoleModular();
            refreshURL();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    @Override
    public ApiResult readRolePower() {
        authRoleModularService.findRoleModular();


        return ApiResult.SUCCESS();
    }



    @Autowired
    private Environment environment;

    int count = 1;

    //通知刷新URL
    public void refreshURL() {
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        String num = environment.getProperty("zuul.num");

        System.out.println("========================通知"+num+"次Zuul刷新权限");
        for (int i = 0; i <Integer.valueOf(num) ; i++) {
            rabbitTemplate.convertAndSend("ExPower", "key-power", count);
        }
        count++;
    }
}
