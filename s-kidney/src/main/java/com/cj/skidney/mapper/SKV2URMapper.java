package com.cj.skidney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.entity.BloodCreatinine;
import com.cj.core.entity.UrineResult;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/7/17 9:51
 **/
public interface SKV2URMapper extends BaseMapper<UrineResult> {

    /**
     * 根据用户id分页查询
     * @param pager
     * @return
     */
    Pager findByUserIdPage(Pager pager);
}
