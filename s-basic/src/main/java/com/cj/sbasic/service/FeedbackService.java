package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;

import javax.servlet.http.HttpServletResponse;

public interface FeedbackService {
    /**
     * 分页查询出所有的投诉信息
     * @param oldPager
     * @return
     */
    OldPager getFeedbackPage(OldPager oldPager);

    /**
     * 根据投诉表id更改状态
     * @param id
     * @return
     */
    int updateFeedbackStateById(Long id);

    /**
     * 导出反馈记录
     * @param oldPager
     * @return
     */
    String exportFeedback(OldPager oldPager, HttpServletResponse response);
}
