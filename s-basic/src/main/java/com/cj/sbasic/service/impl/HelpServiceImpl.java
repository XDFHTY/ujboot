package com.cj.sbasic.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Help;
import com.cj.sbasic.mapper.HelpMapper;
import com.cj.sbasic.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/3/7 17:42
 **/
@Service
public class HelpServiceImpl implements HelpService {
    @Autowired
    private HelpMapper helpMapper;

    /**
     * 分页显示
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getHelpPage(OldPager oldPager) {
        //查询总条数
        oldPager.setRecordTotal(helpMapper.findHelpCountByType(oldPager));
        //查询数据
        oldPager.setContent(helpMapper.findHelpByType(oldPager));
        return oldPager;
    }

    /**
     * 根据ID查询
     * @param helpId
     * @return
     */
    @Override
    public Help getHelpByID(Long helpId) {
        return helpMapper.selectByPrimaryKey(helpId);
    }

    /**
     * 增加
     * @param help
     * @return
     */
    @Override
    public int insert(Help help) {
        return helpMapper.insertSelective(help);
    }

    /**
     * 修改
     * @param help
     * @return
     */
    @Override
    public int update(Help help) {
        return helpMapper.updateByPrimaryKeySelective(help);
    }

    /**
     * 删除
     * @param helpId
     * @return
     */
    @Override
    public int delete(Long helpId) {
        return helpMapper.deleteByPrimaryKey(helpId);
    }
}
