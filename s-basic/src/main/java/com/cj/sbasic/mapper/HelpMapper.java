package com.cj.sbasic.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Help;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface HelpMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long helpId);

    /**
     *插入
     */
    int insert(Help record);

    /**
     *动态插入
     */
    int insertSelective(Help record);

    /**
     *通过id查找
     */
    Help selectByPrimaryKey(Long helpId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Help record);

    int updateByPrimaryKeyWithBLOBs(Help record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Help record);

    /**
     * 查询总条数 按种类
     * @param oldPager
     * @return
     */
    int findHelpCountByType(OldPager oldPager);

    /**
     * 分页查询 按种类
     * @param oldPager
     * @return
     */
    List<Help> findHelpByType(OldPager oldPager);
}