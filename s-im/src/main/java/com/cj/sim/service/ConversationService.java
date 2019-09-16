package com.cj.sim.service;



import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.sim.entity.Conversation;
import com.cj.sim.entity.RespConverstion;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 * Created by XD on 2019/3/4.
 */
public interface ConversationService {

    //创建会话
    RespConverstion createConversation(Map map, HttpServletRequest request);

    //结束会话
    int endConversation(Long conversationId,HttpServletRequest request);

    //删除会话
    int deleteConversation(Long conversationId,String user);

    //查询会话列表 未结束的  已结束的
    List<Conversation> getList(Map map, HttpServletRequest request);

    //根据医生id查询群会话列表
    List<Conversation> getTeamMsgList(Long userId, HttpServletRequest request);

    //根据医生id 和 患者id 查询会话列表
    List<Conversation> getMsgListByIds( Map map, HttpServletRequest request);

    //根据环信群聊id查询所有用户
    ApiResult findUserInfo(Long hxGroupId, HttpServletRequest request);

    //根据团队id和用户id查询群会话
    ApiResult findGroupConversation(Long teamId, Long userId, HttpServletRequest request);
}
