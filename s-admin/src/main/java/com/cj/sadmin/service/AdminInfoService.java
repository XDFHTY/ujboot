package com.cj.sadmin.service;

import com.cj.core.entity.AdminInfo;

public interface AdminInfoService {


    public AdminInfo findAdminInfoById(Long adminId);


    public int updaueAdminInfo(AdminInfo adminInfo);
}
