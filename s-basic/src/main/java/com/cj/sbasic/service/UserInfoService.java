package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.sbasic.vo.UserDetailVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 患者用户审核
 */
public interface UserInfoService {
    /**
     * 按条件分页查询患者审核信息
     * @return
     */
    OldPager getUserCheckPage(OldPager oldPager);

    /**
     * 根据用户信息Id查询用户详情信息
     * @param id
     * @return
     */
    UserDetailVO getUserDetailById(Long id);

    /**
     * 导出用户审核信息
     * @param oldPager
     * @return
     */
    String exportUserCheck(OldPager oldPager, HttpServletResponse response);
}
