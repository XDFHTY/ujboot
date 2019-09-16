package com.cj.sadmin.service;


import com.cj.core.domain.OldPager;
import com.cj.core.entity.Admin;
import com.cj.core.domain.ApiResult;
import com.cj.common.domain.LoginResp;
import com.cj.sadmin.domain.UpdateAdminByAdminPassReq;
import com.cj.sadmin.domain.VoAdmin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {

    /**
     * =====================================Admin===================================================
     */

    /**
     * 新增管理员账号
     */
    public ApiResult addAdmin(VoAdmin voAdmin);


    /**
     * 修改管理员密码
     */
    public int updateAdmin(Admin admin);



    /**
     * 删除管理员账号
     */
    public int delete(Long adminId);



    /**
     * 查询所有管理员账号
     */
    public OldPager findAllAdmin(OldPager oldPager);


    /**
     * 管理员登录
     */
    public ApiResult ifLogin(Admin admin, HttpServletRequest request);

    /**
     * 管理员从HSE系统登录
     * @param admin
     * @param request
     * @return
     */
    public LoginResp ipLogin(Admin admin, HttpServletRequest request);

    /**
     * 管理员注销
     */
    public int ifLogout(HttpServletRequest request);




    /**
     * 修改密码，校验原密码
     */
    public ApiResult updateAdminByAdminPass(HttpServletRequest request, UpdateAdminByAdminPassReq updateAdminByAdminPassReq);


}
