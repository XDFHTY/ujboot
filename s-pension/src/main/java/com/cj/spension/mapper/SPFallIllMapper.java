package com.cj.spension.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.FallIll;
import com.cj.spension.domain.FillIllModel;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/15
*/
public interface SPFallIllMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long fallIllId);

    /**
     *插入
     */
    int insert(FallIll record);

    /**
     *动态插入
     */
    int insertSelective(FallIll record);

    /**
     *通过id查找
     */
    FallIll selectByPrimaryKey(Long fallIllId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(FallIll record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(FallIll record);
    /**
     * 根据用户ID查询疾病条数
     * @param oldPager
     * @return
     */
    int findFillIllModelCount(OldPager oldPager);

    /**
     * 根据用户ID查询疾病
     * @param oldPager
     * @return
     */
    List<FillIllModel> findFillIllModelPage(OldPager oldPager);

    /**
     * 根据ID查询
     * @param fallIllId
     * @return
     */
    FillIllModel findByid(Long fallIllId);

}