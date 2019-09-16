package com.cj.suser.mapper;

import com.cj.core.entity.FeedbackPicture;

/**
* Created by Mybatis Generator 2019/04/04
*/
public interface FeedbackPictureMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long feedbackPictureId);

    /**
     *插入
     */
    int insert(FeedbackPicture record);

    /**
     *动态插入
     */
    int insertSelective(FeedbackPicture record);

    /**
     *通过id查找
     */
    FeedbackPicture selectByPrimaryKey(Long feedbackPictureId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(FeedbackPicture record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(FeedbackPicture record);
}