package com.cj.sbasic.mapper;

import com.cj.core.entity.UserInfo;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.vo.UserCheckVO;
import com.cj.sbasic.vo.UserDetailVO;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/21
*/
public interface SBUserInfoMapper {
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
     * 获取所有的患者审核详情信息
     * @param oldPager
     * @return
     */
    List<UserCheckVO> getUserCheckPage(OldPager oldPager);

    /**
     * 获取查询结果集条数
     * @param oldPager
     * @return
     */
    int getUserCheckCount(OldPager oldPager);

    /**
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    UserInfo selectByUserId(Long userId);

    /**
     * 根据用户信息Id查询用户详情信息
     * @param id
     * @return
     */
    UserDetailVO getUserDetailById(Long id);
}