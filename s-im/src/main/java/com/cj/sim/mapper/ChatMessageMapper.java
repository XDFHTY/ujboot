package com.cj.sim.mapper;




import com.cj.core.domain.OldPager;
import com.cj.sim.entity.ChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChatMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    ChatMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);

    //存放聊天记录
    int insertByList(List<ChatMessage> list);

    //判断msgId是否在数据库中存在
    int findCountByMsgId(String msgId);

    //查询聊天记录总条数
    int findMsgCount(OldPager oldPager);

    //查询聊天内容
    List<ChatMessage> findMsg(OldPager oldPager);

    //根据一个环信id 查询 与他聊过天的人
    List<Map> getUserListByHxId(OldPager oldPager);

    //计数
    int getUserListByHxIdCount(OldPager oldPager);

    //查询群聊记录
    List<ChatMessage> findGroupMsg(OldPager pager);

    //查询聊天记录总条数
    int findGroupMsgCount(OldPager pager);

    //用医生id和用户id 查询所有会话集合 拿到所有的hxGroupId
    List<String> findGroupConversionById(@Param("userId")Long userId, @Param("doctorId")Long doctorId);

    //用GroupId 和时间段查询 是否有消息记录
    List<Long> findMsgByGroupId(@Param("hxGroupId")String hxGroupId,
                                @Param("startDate")String startDate,
                                @Param("endDate")String endDate);

    //查询角色id
    Long findRoleIdByUserId(@Param("doctorId") Long doctorId2);

    //根据医生id和用户id查询这个时间段有没有聊天记录，拿到 from_user
    List<Long> findMsgByIds(@Param("userId")Long userId,
                     @Param("doctorId")Long doctorId,
                     @Param("startDate")String startDate,
                     @Param("endDate")String endDate);
}