package com.cj.sbasic.mapper;

import com.cj.core.entity.Feedback;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.vo.FeedbackTemp;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/21
*/
public interface SBFeedbackMapper {
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

    /**
     * 分页查询出所有的投诉信息
     * @param oldPager
     * @return
     */
    List<FeedbackTemp> getFeedbackPage(OldPager oldPager);

    /**
     * 查询条数
     * @param oldPager
     * @return
     */
    int getFeedbackCount(OldPager oldPager);

    /**
     * 根据投诉表id更改状态
     * @param id
     * @return
     */
    int updateFeedbackStateById(Long id);
}