package com.cj.sadmin.mapper;

import com.cj.core.entity.AdminInfo;

/**
* Created by Mybatis Generator 2019/04/10
*/
public interface AdminInfoMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long adminInfoId);

    /**
     *插入
     */
    int insert(AdminInfo record);

    /**
     *动态插入
     */
    int insertSelective(AdminInfo record);

    /**
     *通过id查找
     */
    AdminInfo selectByPrimaryKey(Long adminInfoId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(AdminInfo record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(AdminInfo record);

    public AdminInfo findAdminInfoById(Long adminId);
}