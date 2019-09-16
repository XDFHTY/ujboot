package com.cj.suser.mapper;

import com.cj.core.entity.Feedback;

/**
* Created by Mybatis Generator 2019/03/26
*/
public interface FeedbackMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long feedbackId);

    /**
     *插入
     */
    int insert(Feedback record);

    /**
     *动态插入
     */
    int insertSelective(Feedback record);

    /**
     *通过id查找
     */
    Feedback selectByPrimaryKey(Long feedbackId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Feedback record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Feedback record);
}