package com.cj.skidney.mapper;

import com.cj.core.entity.DrugwarnTime;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface DrugwarnTimeMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long drugwarnTimeId);

    /**
     *插入
     */
    int insert(DrugwarnTime record);

    /**
     *动态插入
     */
    int insertSelective(DrugwarnTime record);

    /**
     *通过id查找
     */
    DrugwarnTime selectByPrimaryKey(Long drugwarnTimeId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(DrugwarnTime record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(DrugwarnTime record);

    /**
     * 通过用药提醒id删除
     * @param drugwarnId
     * @return
     */
    int deleteBydrugwarnId(Long drugwarnId);
}