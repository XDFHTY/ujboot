package com.cj.sconsult.service.impl;

import com.cj.sconsult.entity.VoEvaluate;
import com.cj.sconsult.mapper.ConsultMapper;
import com.cj.sconsult.service.InterrogationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问诊管理
 * Created by XD on 2019/3/15.
 */
@Service
public class InterrogationServiceImpl implements InterrogationService {

    @Autowired
    private ConsultMapper consultMapper;

    /**
     * 查询是否有问诊资格 返回身份证地址
     * @param userId
     * @return
     */
    @Override
    public String getInterrogationQualifications(Long userId) {
        return this.consultMapper.getInterrogationQualifications(userId);
    }

    //对医生评分
    @Override
    public int insertEvaluate(VoEvaluate evaluate) {
        return this.consultMapper.insertEvaluate(evaluate);
    }

    /**
     * 修改评分
     * @param evaluateId
     * @param score
     */
    @Override
    public int updateEvaluate(Long evaluateId, Double score) {
        return  this.consultMapper.updateEvaluate(evaluateId, score);
    }
}
