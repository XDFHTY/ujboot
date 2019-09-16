package com.cj.common.mapper;

import com.cj.core.entity.AppVersion;

import java.util.List;

/**
* Created by Mybatis Generator 2019/04/19
*/
public interface AppVersionMapper {
    /**
     *插入
     */
    int insert(AppVersion record);

    /**
     *动态插入
     */
    int insertSelective(AppVersion record);


    AppVersion findByType(String type);

    List<AppVersion> findApp();

    int delApp(String url);
}