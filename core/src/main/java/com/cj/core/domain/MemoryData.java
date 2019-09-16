package com.cj.core.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建一个内存数据类，用于存放静态的数据，并初始化
 * 用于单设备登录
 *
 */
@Data
public class MemoryData {

    //用于存储 用户-token，默认2小时过期
    private static ExpiryMap<String, String> tokenMap = new ExpiryMap<>(1000*60*60*2);

    public static ExpiryMap<String, String> getTokenMap() {
        return tokenMap;
    }

    //用于存储 角色-权限
    private static Map roleModularMap = new HashMap();


    public static List<AuthRoleModulars> authRoleModulars;


}