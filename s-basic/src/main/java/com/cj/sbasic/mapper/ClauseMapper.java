package com.cj.sbasic.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Clause;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface ClauseMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long clauseId);

    /**
     *插入
     */
    int insert(Clause record);

    /**
     *动态插入
     */
    int insertSelective(Clause record);

    /**
     *通过id查找
     */
    Clause selectByPrimaryKey(Long clauseId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Clause record);

    int updateByPrimaryKeyWithBLOBs(Clause record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Clause record);

    /**
     * 查询总条数
     * @param oldPager
     * @return
     */
    int findClauseCount(OldPager oldPager);

    /**
     * 查询内容
     * @param oldPager
     * @return
     */
    List<Clause> findClausePage(OldPager oldPager);

    /**
     *通过id查找
     */
    Clause findBySubject(String subject);
}