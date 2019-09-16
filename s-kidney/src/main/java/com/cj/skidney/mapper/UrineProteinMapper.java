package com.cj.skidney.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.UrineProtein;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/14
*/
public interface UrineProteinMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long urineProteinId);

    /**
     *插入
     */
    int insert(UrineProtein record);

    /**
     *动态插入
     */
    int insertSelective(UrineProtein record);

    /**
     *通过id查找
     */
    UrineProtein selectByPrimaryKey(Long urineProteinId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UrineProtein record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UrineProtein record);

    /**
     * 查询尿蛋白记录条数
     * @param oldPager
     * @return
     */
    int findUrineProteinCount(OldPager oldPager);

    /**
     * 分页查询尿蛋白记录
     * @param oldPager
     * @return
     */
    List<UrineProtein> findUrineProteinPage(OldPager oldPager);
    /**
     * 查询尿蛋白记录
     * @param oldPager
     * @return
     */
    List<UrineProtein> findUrineProtein(OldPager oldPager);
}