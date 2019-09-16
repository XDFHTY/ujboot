package com.cj.suser.service.impl;

import com.cj.core.entity.Feedback;
import com.cj.core.entity.FeedbackPicture;
import com.cj.suser.domain.VoFeed;
import com.cj.suser.mapper.FeedbackMapper;
import com.cj.suser.mapper.FeedbackPictureMapper;
import com.cj.suser.service.FeedService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {


    @Resource
    private FeedbackMapper feedbackMapper;


    @Resource
    private FeedbackPictureMapper feedbackPictureMapper;

    @Override
    public int addfeed(VoFeed voFeed, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        long userId = Long.parseLong(request.getHeader("id"));

        voFeed.setTime(new Date());

        Feedback feedback = new Feedback();
        List<FeedbackPicture> feedbackPictures = voFeed.getFeedbackPictures();
        BeanUtils.copyProperties(feedback,voFeed);

        feedback.setUserId(userId);
        feedback.setState("0");
        int i = feedbackMapper.insertSelective(feedback);

        boolean b = false;
        if (feedbackPictures !=null && feedbackPictures.size()>0){

            feedbackPictures.forEach(feedbackPicture -> {
                feedbackPicture.setFeedbackId(feedback.getFeedbackId());

            });
            b = feedbackPictures.stream().allMatch(feedbackPicture -> {
                feedbackPicture.setFeedbackId(feedback.getFeedbackId());
                return 1==feedbackPictureMapper.insertSelective(feedbackPicture);
            });
        }


        return i;
    }
}
