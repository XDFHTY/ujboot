package com.cj.skidney.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.UrineResult;
import com.cj.skidney.domain.UrineModel;
import com.cj.skidney.domain.UrineResultVO;


import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface UrineResultMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long urineResultId);

    /**
     *插入
     */
    int insert(UrineResult record);

    /**
     *动态插入
     */
    int insertSelective(UrineResult record);

    /**
     *通过id查找
     */
    UrineResult selectByPrimaryKey(Long urineResultId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UrineResult record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UrineResult record);

    /**
     * 查询尿检结果条数
     * @param oldPager
     * @return
     */
    int findUreCount(OldPager oldPager);

    /**
     * 分页查询尿检结果数据
     * @param oldPager
     * @return
     */
    List<UrineModel> findUrePage(OldPager oldPager);
    /**
     * 查询尿检结果数据
     * @param oldPager
     * @return
     */
    List<UrineModel> findUre(OldPager oldPager);

    /**
     *动态插入
     */
    int replaseSelective(UrineResultVO record);

    /**
     * 根据id查询尿检结果
     * @param oldPager
     * @return
     */
    UrineModel findById(OldPager oldPager);
}