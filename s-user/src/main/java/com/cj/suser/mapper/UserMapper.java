package com.cj.suser.mapper;


import com.cj.common.domain.RespInfo;
import com.cj.core.entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //登录时检查用户是否已存在
    public User findUserByName(String userName);

    //注册时检查账号是否已存在
    public User findUserName(String userName);


    //根据id修改密码
    public int updatePassById(User user);

    //根据userid查询 姓名 昵称、头像
    public List<RespInfo> getInfo(List<RespInfo> respInfos);

    //根据医生ID修改审核状态
    public int updateState(long doctorId);



}