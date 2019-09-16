package com.cj.suser.mapper;

import com.cj.core.entity.UserInfo;
import com.cj.suser.domain.VoUserInfo;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019/03/09
*/
public interface UserInfoMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long userInfoId);

    /**
     *插入
     */
    int insert(UserInfo record);

    /**
     *动态插入
     */
    int insertSelective(UserInfo record);

    /**
     *通过id查找
     */
    UserInfo selectByPrimaryKey(Long userInfoId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UserInfo record);

    //通过userId查询userInfo
    public VoUserInfo findUserInfoByUserId(Long userId);

    public int updateUserHeadUrl(@Param("userId") long userId,@Param("headUrl") String headUrl);
}