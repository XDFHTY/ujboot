package com.cj.skidney.mapper;

import com.cj.core.entity.UrineIdentification;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface UrineIdentificationMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long urineIdentificationId);

    /**
     *插入
     */
    int insert(UrineIdentification record);

    /**
     *动态插入
     */
    int insertSelective(UrineIdentification record);

    /**
     *通过id查找
     */
    UrineIdentification selectByPrimaryKey(Long urineIdentificationId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UrineIdentification record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UrineIdentification record);
}