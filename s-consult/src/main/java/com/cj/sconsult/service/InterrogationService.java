package com.cj.sconsult.service;

import com.cj.sconsult.entity.VoEvaluate;

/**
 * Created by XD on 2019/3/15.
 */
public interface InterrogationService {

    //查询是否有问诊资格 返回身份证地址
    String getInterrogationQualifications(Long userId);

    //对医生评分
    int insertEvaluate(VoEvaluate evaluate);

    //修改评分
    int updateEvaluate(Long evaluateId, Double score);
}
