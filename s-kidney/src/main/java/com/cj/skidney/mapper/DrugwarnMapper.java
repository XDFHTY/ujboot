package com.cj.skidney.mapper;

import com.cj.core.entity.Drugwarn;
import com.cj.core.domain.OldPager;
import com.cj.skidney.domain.DrugModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface DrugwarnMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long drugwarnId);

    /**
     *插入
     */
    int insert(Drugwarn record);

    /**
     *动态插入
     */
    int insertSelective(Drugwarn record);

    /**
     *通过id查找
     */
    Drugwarn selectByPrimaryKey(Long drugwarnId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Drugwarn record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Drugwarn record);

    /**
     * 查询用药计划列表
     * @param oldPager
     * @return
     */
    List<DrugModel> findDrugModel(OldPager oldPager);

    /**
     * 查询用药提醒id
     * @param userId
     * @param date
     * @return
     */
    Long findDrugwarnId(@Param("userId") Long userId,@Param("date") Date date);
    /**
     * 查询用药提醒id
     * @param drugwarnId
     * @return
     */
    DrugModel findDrugById(Long drugwarnId);
}