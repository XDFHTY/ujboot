package com.cj.suser.service;

import com.cj.core.domain.Pager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 个人端用户管理业务层
 * @date： 2019/6/28 10:30
 **/
public interface PerUserService {
    /**
     * 新增用户信息
     * @param id
     * @param name
     * @param sex
     * @return
     */
    int insertUserInfo(Long id,String name ,String sex);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(Long id);

    /**
     * 修改密码
     * @param userId
     * @param userPass
     * @return
     */
    int updatePass(Long userId,String userPass);

    /**
     * 分页查询用户列表
     * @param pager
     * @return
     */
    Pager findUser(Pager pager);

    /**
     * 分页查询用户订单
     * @param pager
     * @return
     */
    Pager findOrder(Pager pager);

    /**
     * 模糊查询医生
     * @param current
     * @param parameter
     * @return
     */
    List<Map<String,String>> findPeople(int current, String parameter);
    /**
     * 导出
     * @return
     */
    int export(Map<String,String> map, HttpServletResponse response);
}
