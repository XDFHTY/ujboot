package com.cj.sadmin.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.OperateLog;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/21
*/
public interface OperateLogMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long operateLogId);

    /**
     *插入
     */
    int insert(OperateLog record);

    /**
     *动态插入
     */
    int insertSelective(OperateLog record);

    /**
     *通过id查找
     */
    OperateLog selectByPrimaryKey(Long operateLogId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(OperateLog record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(OperateLog record);


    public List<List<?>> findLogs(OldPager oldPager);
}