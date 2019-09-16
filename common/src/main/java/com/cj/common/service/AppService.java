package com.cj.common.service;

import com.cj.core.entity.AppVersion;

import java.util.List;

public interface AppService {

    int addApp(AppVersion appVersion);

    List<AppVersion> findApp();

    int delApp(String url);
}
