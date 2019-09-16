package com.cj.sconsult.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.v2entity.V2TeamPerson;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019-06-25 11:06:25
*/
public interface V2TeamPersonMapper extends BaseMapper<V2TeamPerson> {

    //清空团队成员
    int updatePersonByTeamId(Long teamId);

    //根据用户id和团队id 查询成员
    V2TeamPerson findDataByUserIdAndTeamId(@Param("userId") Long userId2, @Param("teamId") Long teamId);

    //查询医生名字
    String findDoctorNameById(@Param("userId")Long userId);

    //修改记录  移除团员
    int updateStateById(@Param("teamId")Long teamId, @Param("userId")Long userId2);
}