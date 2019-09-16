package com.cj.skidney.service.impl;

import com.cj.core.entity.UrineProposal;
import com.cj.skidney.mapper.UrineProposalMapper;
import com.cj.skidney.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 建议管理业务实现层
 * @date： 2019/4/17 9:52
 **/
@Service
@Transactional
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    private UrineProposalMapper urineProposalMapper;
    /**
     * 修改建议内容
     * @param urineProposal
     * @return
     */
    @Override
    public int updataProposal(UrineProposal urineProposal) {
        return urineProposalMapper.updateByPrimaryKeySelective(urineProposal);
    }

    /**
     * 查询建议列表
     * @return
     */
    @Override
    public List<UrineProposal> findProposal() {
        return urineProposalMapper.findAll();
    }
}
