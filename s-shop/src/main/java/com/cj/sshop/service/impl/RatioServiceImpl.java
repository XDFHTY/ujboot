package com.cj.sshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.core.exception.MyException;
import com.cj.core.v2entity.V2Ratio;
import com.cj.sshop.mapper.V2RatioMapper;
import com.cj.sshop.service.RatioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RatioServiceImpl implements RatioService {

    @Resource
    private V2RatioMapper ratioMapper;

    @Override
    public List<V2Ratio> findAllRatios() {
        V2Ratio v2Ratio = new V2Ratio();
        v2Ratio.setState("1");
        Wrapper<V2Ratio> wrapper = new QueryWrapper<>(v2Ratio).orderByAsc("good_type");
        return ratioMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public int updateRatio(V2Ratio ratio) {

        int i = ratioMapper.updateRatio(ratio.getRatioId());
        ratio.setRatioId(null);
        ratio.setCreatetime(new Date());
        ratio.setState("1");
        int j = ratioMapper.insert(ratio);
        if ((i&j)==0){
            throw new MyException(1000,"修改失败");
        }
        return i+j;
    }
}
