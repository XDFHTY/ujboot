package com.cj.suser.service;

import org.springframework.stereotype.Service;

/**
 * 绑定记录
 * Created by XD on 2019/3/19.
 */
public interface BindDataService {

    //查询数据库 修改操作时间是 3天前的 且状态是 绑定中的 记录   修改状态 为 被拒绝
    int updateBindData(String nowDateAdd3);
}
