package com.cj.suser.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.V2UserFriend;
import com.cj.suser.domain.VoFriend;
import com.cj.suser.domain.VoUserInfo;

import java.util.List;

public interface UserFriendService {


    VoFriend findUserByPhone(String phone);

    int reqBind(long userId, long friendId);

    int confirmBind(long userId , long friendId);

    //查询用户亲友列表
    List<VoFriend> findFriend(long userId);

    //查询亲友详情
    VoUserInfo findFriendInfo(long userId);

    int delFriend(long userId , long friendId);

    //分页查询亲友疾病列表
    OldPager findFallIll(OldPager oldPager);
}
