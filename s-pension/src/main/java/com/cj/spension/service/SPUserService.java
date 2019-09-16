package com.cj.spension.service;

import com.cj.core.domain.OldPager;
import com.cj.spension.domain.FillIllModel;
import com.cj.spension.domain.UserModel;

import javax.servlet.http.HttpServletResponse;

/**
 * @author： 刘磊
 * @Description: 用户信息管理业务层
 * @date： 2019/3/11 16:16
 **/
public interface SPUserService {
    //分页查询用户信息
    OldPager getUserPage(OldPager oldPager);
    //分页查询疾病信息
    OldPager getFillPage(OldPager oldPager);
    //根据ID查询用户信息
    UserModel getUserByID(Long userId);
    //根据ID查询疾病
    FillIllModel getFillByID(Long fallIllId);
    //导出Excel
    void exportExcel(OldPager oldPager, HttpServletResponse response);
}
