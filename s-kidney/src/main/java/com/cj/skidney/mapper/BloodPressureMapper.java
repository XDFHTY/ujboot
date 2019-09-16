package com.cj.skidney.mapper;

import com.cj.core.entity.BloodPressure;
import com.cj.core.domain.OldPager;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface BloodPressureMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long bloodPressureId);

    /**
     *插入
     */
    int insert(BloodPressure record);

    /**
     *动态插入
     */
    int insertSelective(BloodPressure record);

    /**
     *通过id查找
     */
    BloodPressure selectByPrimaryKey(Long bloodPressureId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(BloodPressure record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(BloodPressure record);

    /**
     * 查询血压条数
     * @param oldPager
     * @return
     */
    int findBloodPressureCount(OldPager oldPager);

    /**
     * 分页查询记录数据
     * @param oldPager
     * @return
     */
    List<BloodPressure> findBloodPressurePage(OldPager oldPager);
    /**
     * 查询记录数据
     * @param oldPager
     * @return
     */
    List<BloodPressure> findBloodPressure(OldPager oldPager);
}