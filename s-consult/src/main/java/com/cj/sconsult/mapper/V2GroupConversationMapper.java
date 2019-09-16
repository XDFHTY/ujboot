package com.cj.sconsult.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.v2entity.V2GroupConversation;
import com.cj.core.v2entity.V2Team;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* Created by Mybatis Generator 2019-06-25 11:06:25
*/
public interface V2GroupConversationMapper extends BaseMapper<V2GroupConversation> {

    //根据团队id  结束群会话
    void endConversationByTeamId(@Param("teamId") Long teamId, @Param("date")Date date);

    //创建多个和团长的单独会话
    void addConversion(@Param("doctorId") Long teamCaptainId, @Param("userId") Long userId2,@Param("nowDate")  Date nowDate);

    //查询这个团队下 所有未结束的群会话
    List<String> findHxGroupIdsByTeamId(@Param("teamId") Long teamId);

    //根据团队id和用户id 查询未结束的群会话
    V2GroupConversation findConversionById(@Param("teamId") Long teamId, @Param("userId")Long userId);
}