package com.cj.sadmin.service.impl;

import com.cj.core.entity.AdminInfo;
import com.cj.sadmin.mapper.AdminInfoMapper;
import com.cj.sadmin.service.AdminInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

    @Resource
    private AdminInfoMapper adminInfoMapper;

    @Override
    public AdminInfo findAdminInfoById(Long adminId) {
        return adminInfoMapper.findAdminInfoById(adminId);
    }

    @Override
    public int updaueAdminInfo(AdminInfo adminInfo) {
        adminInfo.setAdminId(null);
        return adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
    }
}
