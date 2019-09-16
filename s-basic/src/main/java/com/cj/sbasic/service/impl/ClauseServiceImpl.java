package com.cj.sbasic.service.impl;

import com.cj.core.entity.Clause;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.mapper.ClauseMapper;
import com.cj.sbasic.service.ClauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author： 刘磊
 * @Description: 隐私条款业务实现层
 * @date： 2019/3/7 15:55
 **/
@Service
public class ClauseServiceImpl implements ClauseService {
    @Autowired
    private ClauseMapper clauseMapper;
    /**
     * 通过ID查询内容
     * @param clauseId
     * @return
     */
    @Override
    public Clause getClaByID(Long clauseId) {
        return clauseMapper.selectByPrimaryKey(clauseId);
    }

    /**
     * 根据条款名字查询
     * @param subject
     * @return
     */
    @Override
    public Clause getClaBySubject(String subject) {
        return clauseMapper.findBySubject(subject);
    }

    /**
     * 分页查询条款
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getClaPage(OldPager oldPager) {
        //查询条款总条数
        oldPager.setRecordTotal(clauseMapper.findClauseCount(oldPager));
        //查询内容
        oldPager.setContent(clauseMapper.findClausePage(oldPager));
        return oldPager;
    }

    /**
     * 新增
     * @param clause
     * @return
     */
    @Override
    public int insert(Clause clause) {
        return clauseMapper.insertSelective(clause);
    }

    /**
     * 修改
     * @param clause
     * @return
     */
    @Override
    public int update(Clause clause) {
        return clauseMapper.updateByPrimaryKeySelective(clause);
    }

    /**
     * 删除
     * @param clauseId
     * @return
     */
    @Override
    public int delete(Long clauseId) {
        return clauseMapper.deleteByPrimaryKey(clauseId);
    }
}
