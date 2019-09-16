package com.cj.suser.service.impl;

import com.cj.suser.mapper.PharmacyPushMapper;
import com.cj.suser.service.BindDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XD on 2019/4/22.
 */
@Service
public class BindDataServiceImpl implements BindDataService {


    @Autowired
    private PharmacyPushMapper pharmacyPushMapper;
    /**
     * 查询数据库 修改操作时间是 3天前的 且状态是 绑定中的 记录   修改状态 为 被拒绝
     * @param nowDateAdd3
     * @return
     */
    @Override
    public int updateBindData(String nowDateAdd3) {
        return this.pharmacyPushMapper.updateStateByDay(nowDateAdd3);
    }
}
