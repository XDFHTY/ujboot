package com.cj.sim.service;


import com.cj.core.domain.OldPager;
import com.cj.sim.entity.ChatMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2019/2/28.
 */
public interface MsgService {
    //存放聊天记录
    int insert(List<ChatMessage> chatMessageList, HttpServletRequest request);

    //查询聊天记录
    OldPager getMsg(OldPager oldPager);

    //发送透传消息
    Object sendCmdMsg(Map map);

    //根据一个环信id 查询 与他聊过天的人
    OldPager getUserListByHxId(OldPager oldPager);

    //获取群聊的记录
    OldPager getGroupMsg(OldPager pager);

    //查询有没有退款资格
    Boolean getRefundEligibility(Long userId, Long doctorId, String startDate, String endDate);
}
