package com.cj.skidney.mapper;

import com.cj.core.entity.UrineProposal;

import java.util.List;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface UrineProposalMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long urineProposalId);

    /**
     *插入
     */
    int insert(UrineProposal record);

    /**
     *动态插入
     */
    int insertSelective(UrineProposal record);

    /**
     *通过id查找
     */
    UrineProposal selectByPrimaryKey(Long urineProposalId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UrineProposal record);

    int updateByPrimaryKeyWithBLOBs(UrineProposal record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UrineProposal record);

    /**
     * 查全部
     * @return
     */
    List<UrineProposal> findAll();
}