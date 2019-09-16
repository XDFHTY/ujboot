package com.cj.sim.mapper;



import com.cj.core.domain.OldPager;
import com.cj.sim.entity.Conversation;
import com.cj.sim.entity.GroupConversion;
import com.cj.sim.entity.RespUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ConversationMapper {
    int deleteByPrimaryKey(Long conversationId);

    int insert(Conversation record);

    int insertSelective(Conversation record);

    Conversation selectByPrimaryKey(Long conversationId);

    int updateByPrimaryKeySelective(Conversation record);

    int updateByPrimaryKey(Conversation record);


    //检查会话是否存在  并判断是状态为 未结束
    Conversation  findConversationState(Map map);

    //需要创建新的会话 返回会话id
    int createRespId(Conversation conversation);

    //修改 结束状态 和 结束时间
    int endConversation(@Param("conversationId") Long conversationId, @Param("endDate") Date endDate);

    //删除会话 向删除标记表 加一条记录
    int insertConversationDelete(@Param("conversationId")Long conversationId, @Param("user")String user);

    //物理删除会话
    int deleteConversation(@Param("userA")String userA, @Param("userB")String userB);

    //未结束的会话 直接查就行  因为两个用户直接只会存在一条未结束的会话
    List<Conversation> getListByNormal(Map map);
    List<Conversation> getListByNormal2(Map map);
    //计数
    Integer getListByNormalByCont(OldPager oldPager);

    //已结束的会话  需要去重， 因为两个用户 会存在多条已结束的会话
    List<Conversation> getListByEnd(Map map);
    List<Conversation> getListByEnd2(Map map);
    //计数
    Integer getListByEndByCount(OldPager oldPager);


    //查询医生的商品类型
    String getGoodTypeById(Long sellerId);

    //查询该用户的角色
    Long findRoleIdByUserId(Map map);

    //查询用户的会话列表
    List<Conversation> findGroupConversionByUser(Map map);
    List<Conversation> findGroupConversionByUser2(Map map);

    //查询医生的会话列表
    List<Conversation> findGroupConversionByDoctor(Map map);

    //查询未结束的 医生的会话列表
    List<Conversation> findGroupConversionByDoctorNotEnd(Map map);

    //查询已结束的 医生的会话列表
    List<Conversation> findGroupConversionByDoctorEnd(Map map);
    List<Conversation> findGroupConversionByDoctorEnd2(Map map);

    //查询用户的信息
    RespUserInfo findUserInfoByHxGroupId(@Param("hxGroupId") Long hxGroupId);

    //查询医生的信息
    List<RespUserInfo> findDoctorInfoByHxGroupId(@Param("hxGroupId")Long hxGroupId);

    //根据团队id和用户id查询群会话
    Conversation findGroupConversation(@Param("teamId")Long teamId, @Param("userId")Long userId);

    //查询角色id
    Long findRoleId(@Param("userId") String userId);
}