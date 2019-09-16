package com.cj.sconsult.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2Team;
import com.cj.sconsult.entity.RespDoctorInfo;
import com.cj.sconsult.entity.RespDoctorOrTeam;
import com.cj.sconsult.entity.RespTeam;
import com.cj.sconsult.entity.RespTeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* Created by Mybatis Generator 2019-06-25 11:06:25
*/
public interface V2TeamMapper extends BaseMapper<V2Team> {

    //查询此人是否创建了未解散的团队
    int findTeamNumById(Long teamCaptainId);

    //结束此人 所有和用户的单独聊天
    int endConversion(@Param("userId") Long teamCaptainId, @Param("date") Date nowDate);

    //查询团队名称唯一性
    int findTeamNumByName(String teamName);

    //查询此人绑定的所有用户
    List<Long> findBindUserById(Long teamCaptainId);

    //根据团队id查询未解散的团队
    V2Team findTeamById(@Param("teamId") Long teamId);

    //结束 单独对话
    int updateConversionById(@Param("userId") Long userId, @Param("doctorId") Long doctorId, @Param("date") Date date);


    //查询医生是否创建有未解散的团队
    V2Team findTeamByDoctorId(@Param("doctorId") Long doctorId);

    //查询医生信息
    RespDoctorOrTeam findDoctorInfo(@Param("doctorId")Long doctorId);

    //查询团队信息
    RespDoctorOrTeam findDoctorTeamInfo(@Param("doctorId")Long doctorId, @Param("userId")Long userId);

    //查询团队详情和成员信息
    RespTeamInfo findTeamInfoById(@Param("teamId")Long teamId);

    //查询这个医生加入 或创建的团队
    V2Team findTeamIdByDoctorId(@Param("doctorId")Long doctorId);

    //分页查询团队列表
    List<RespTeam> findAll(Pager pager);

    //根据团队id查询成员列表
    List<RespDoctorInfo> findPersonListByTeamId(Pager pager);
}