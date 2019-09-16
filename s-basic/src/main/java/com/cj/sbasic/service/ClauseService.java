package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Clause;

/**
 * @author： 刘磊
 * @Description: 隐私条款业务层
 * @date： 2019/3/7 15:55
 **/
public interface ClauseService {
    //通过id查询条款
    Clause getClaByID(Long clauseId);
    //通过名字查询条款
    Clause getClaBySubject(String subject);
    //分页显示所有
    OldPager getClaPage(OldPager oldPager);
    //添加
    int insert(Clause clause);
    //修改
    int update(Clause clause);
    //删除
    int delete(Long clauseId);
}
