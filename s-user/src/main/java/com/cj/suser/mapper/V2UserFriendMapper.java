package com.cj.suser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.entity.User;
import com.cj.core.entity.V2UserFriend;
import com.cj.suser.domain.VoFriend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019-08-27 10:04:03
*/
public interface V2UserFriendMapper extends BaseMapper<V2UserFriend> {


    VoFriend findUserByPhone(String phone);


    VoFriend findUserById(long userId);

    int findCountFrend(@Param("userId") long userId, @Param("friend") long friend);

    List<VoFriend> findFrendByUserId(long userId);

    int delFriend(@Param("userId") long userId, @Param("friendId") long friendId);



}