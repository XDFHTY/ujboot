package com.cj.sadmin.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.OperateLog;

public interface LogService {

    public int addLog(OperateLog operateLog);

    public OldPager findLogs(OldPager oldPager);
}
