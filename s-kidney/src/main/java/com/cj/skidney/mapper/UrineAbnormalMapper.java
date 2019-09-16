package com.cj.skidney.mapper;

import com.cj.core.entity.UrineAbnormal;

/**
* Created by Mybatis Generator 2019/04/01
*/
public interface UrineAbnormalMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long urineAbnormalId);

    /**
     *插入
     */
    int insert(UrineAbnormal record);

    /**
     *动态插入
     */
    int insertSelective(UrineAbnormal record);

    /**
     *通过id查找
     */
    UrineAbnormal selectByPrimaryKey(Long urineAbnormalId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UrineAbnormal record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UrineAbnormal record);
}