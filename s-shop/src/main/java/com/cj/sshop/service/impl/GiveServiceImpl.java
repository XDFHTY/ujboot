package com.cj.sshop.service.impl;

import com.cj.core.v2entity.V2Give;
import com.cj.sshop.mapper.V2GiveMapper;
import com.cj.sshop.service.GiveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GiveServiceImpl implements GiveService {

    @Resource
    private V2GiveMapper giveMapper;

    @Override
    public List<V2Give> find0Pay(long userId) {
        return giveMapper.find0Pay(userId);
    }
}
