package com.cj.skidney.mapper;

import com.cj.core.entity.BloodCreatinine;
import com.cj.core.domain.OldPager;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/14
*/
public interface BloodCreatinineMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long bloodCreatinineId);

    /**
     *插入
     */
    int insert(BloodCreatinine record);

    /**
     *动态插入
     */
    int insertSelective(BloodCreatinine record);

    /**
     *通过id查找
     */
    BloodCreatinine selectByPrimaryKey(Long bloodCreatinineId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(BloodCreatinine record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(BloodCreatinine record);
    /**
     * 查询血肌酐记录条数
     * @param oldPager
     * @return
     */
    int findBloodCreatinineCount(OldPager oldPager);

    /**
     * 分页查询血肌酐记录
     * @param oldPager
     * @return
     */
    List<BloodCreatinine> findBloodCreatininePage(OldPager oldPager);
    /**
     * 查询血肌酐记录
     * @param oldPager
     * @return
     */
    List<BloodCreatinine> findBloodCreatinine(OldPager oldPager);
}