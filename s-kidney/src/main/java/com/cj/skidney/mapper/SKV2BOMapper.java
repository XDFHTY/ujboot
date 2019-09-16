package com.cj.skidney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2BloodOxygen;

import java.util.List;
import java.util.Map;

/**
* Created by Mybatis Generator 2019-06-29 16:09:33
*/
public interface SKV2BOMapper extends BaseMapper<V2BloodOxygen> {
    /**
     * 根据用户id分页查询
     * @param pager
     * @return
     */
    Pager findByUserIdPage(Pager pager);

    /**
     * 根据用户id查询
     * @param map
     * @return
     */
    List<V2BloodOxygen> findByUserId(Map map);
}