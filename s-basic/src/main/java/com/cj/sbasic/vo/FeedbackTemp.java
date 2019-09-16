package com.cj.sbasic.vo;

import com.cj.core.entity.FeedbackPicture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 存放中间查询结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackTemp {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username = "";

    /**
     * 用户类型
     */
    private String userType = "";

    /**
     * 邮箱
     */
    private String email = "";

    /**
     * 手机号
     */
    private String phone = "";

    /**
     * 时间
     */
    private Date time;

    /**
     * 投诉类型
     */
    private String object = "";

    /**
     * 投诉内容
     */
    private String content = "";

    /**
     * 图片列表(最多四张)
     */
    private List<FeedbackPicture> imageList = new ArrayList<>();

    /**
     * 状态
     */
    private String state = "";


    /**
     * 组装数据，将中间查询结果转换为视图对象
     * @param name
     * @return
     */
    public FeedbackVO convertToFeedBackVO(String name){
        FeedbackVO feedBackVO = new FeedbackVO();
        feedBackVO.setFeedbackId(this.id);
        feedBackVO.setUsername(this.username);
        feedBackVO.setName(name);
        feedBackVO.setUserType(this.userType);
        feedBackVO.setEmail(this.email);
        feedBackVO.setPhone(this.phone);
        feedBackVO.setTime(this.time);
        feedBackVO.setFeedbackType(this.object);
        feedBackVO.setContent(this.content);
        feedBackVO.setState(this.state);
        List<String> imageList = new ArrayList<>();
        //处理没有图片的情况
        if (this.imageList.size() == 1 && this.imageList.get(0).getFeedbackPictureId() == null){
            feedBackVO.setImageList(imageList);
        } else {
            for (FeedbackPicture feedbackPicture:this.imageList){
                imageList.add(feedbackPicture.getRouteUrl());
            }
            feedBackVO.setImageList(imageList);
        }
        return feedBackVO;
    }
}
