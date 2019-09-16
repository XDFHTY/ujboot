package com.cj.skidney.service;

import com.cj.core.entity.UrineProposal;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 建议管理业务层
 * @date： 2019/4/17 9:51
 **/
public interface ProposalService {
    //修改建议
    int updataProposal(UrineProposal urineProposal);
    //查询建议列表
    List<UrineProposal> findProposal();
}
