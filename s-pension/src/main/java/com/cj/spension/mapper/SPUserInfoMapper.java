package com.cj.spension.mapper;

import com.cj.common.domain.VoRegion;
import com.cj.core.domain.OldPager;
import com.cj.core.entity.UserInfo;
import com.cj.spension.domain.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/08
*/
@Repository("spensionUserInfoMapper")
public interface SPUserInfoMapper {
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
    /**
     * 查询总条数
     */
    int findUserCount(OldPager oldPager);
    /**
     * 分页查询记录
     */
    List<UserModel> findUserPage(OldPager oldPager);
    /**
     * 查询记录
     */
    List<UserModel> findUser(OldPager oldPager);
    /**
     * 根据ID查询
     */
    UserModel findUserByID(Long userId);
    /**
     * 根据id查地名
     */
    VoRegion findById(Long id);



}