package com.cj.sadmin.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.OperateLog;
import com.cj.sadmin.mapper.OperateLogMapper;
import com.cj.sadmin.service.LogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Async
    @Override
    public int addLog(OperateLog operateLog) {
        return operateLogMapper.insertSelective(operateLog);
    }

    @Override
    public OldPager findLogs(OldPager oldPager) {
        List<List<?>> lists = operateLogMapper.findLogs(oldPager);
        oldPager.setLists(lists);
        return oldPager;
    }
}
