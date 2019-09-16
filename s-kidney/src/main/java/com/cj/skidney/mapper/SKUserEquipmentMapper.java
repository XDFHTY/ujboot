package com.cj.skidney.mapper;

import com.cj.core.entity.UserEquipment;
import com.cj.core.domain.OldPager;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/16
*/
public interface SKUserEquipmentMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long userBindEquipmentId);

    /**
     *插入
     */
    int insert(UserEquipment record);

    /**
     *动态插入
     */
    int insertSelective(UserEquipment record);

    /**
     *通过id查找
     */
    UserEquipment selectByPrimaryKey(Long userBindEquipmentId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UserEquipment record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UserEquipment record);

    /**
     * 查询设备历史绑定记录条数
     * @param oldPager
     * @return
     */
    int findHistoricalCount(OldPager oldPager);

    /**
     * 分页查询设备历史绑定记录
     * @param oldPager
     * @return
     */
    List<UserEquipment> findHistoricalPage(OldPager oldPager);
    /**
     * 查询设备历史绑定记录
     * @param oldPager
     * @return
     */
    List<UserEquipment> findHistorical(OldPager oldPager);
}