package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Help;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/3/7 17:40
 **/
public interface HelpService {
    //分页显示
    OldPager getHelpPage(OldPager oldPager);
    //根据id查询
    Help getHelpByID(Long helpId);
    //增加
    int insert(Help help);
    //修改
    int update(Help help);
    //删除
    int delete(Long helpId);
}
