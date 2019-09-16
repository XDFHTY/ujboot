package com.cj.spension.mapper;

import com.cj.core.entity.User;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface SPUserMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long userId);

    /**
     *插入
     */
    int insert(User record);

    /**
     *动态插入
     */
    int insertSelective(User record);

    /**
     *通过id查找
     */
    User selectByPrimaryKey(Long userId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(User record);
    /**
     * 修改密码
     */
    int updateForPassById(@Param("userId") Long userId,@Param("pass") String pass);

    /**
     * 删除账号
     * @param userId
     * @return
     */
    int deleteByIdL(Long userId);
}