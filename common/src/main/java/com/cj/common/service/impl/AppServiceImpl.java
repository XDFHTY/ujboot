package com.cj.common.service.impl;

import com.cj.common.mapper.AppVersionMapper;
import com.cj.common.service.AppService;
import com.cj.core.entity.AppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppVersionMapper appVersionMapper;


    @Override
    public int addApp(AppVersion appVersion) {
        return appVersionMapper.insertSelective(appVersion);
    }

    @Override
    public List<AppVersion> findApp() {
        return appVersionMapper.findApp();
    }

    @Override
    public int delApp(String url) {
        return appVersionMapper.delApp(url);
    }
}
