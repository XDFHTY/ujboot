package com.cj.common.service;

import com.cj.core.domain.AuthRoleModulars;

import java.util.List;

/**
 * 角色-权限 关系
 */
public interface AuthRoleModularService {

    //查询角色-权限 树形结构封装
    public List<AuthRoleModulars> findRoleModular();
}
