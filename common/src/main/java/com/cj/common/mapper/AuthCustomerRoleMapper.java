package com.cj.common.mapper;

import com.cj.core.entity.AuthCustomerRole;

import java.util.List;

public interface AuthCustomerRoleMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    AuthCustomerRole selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthCustomerRole record);

    /**
     * 统计使用此角色的用户ID
     * @return
     */
    public int findCustomerNumByRoleId(Long roleId);


}