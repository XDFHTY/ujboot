package com.cj.sshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2Good;
import com.cj.sshop.domain.VoGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019-06-21 16:16:06
*/
public interface V2GoodMapper extends BaseMapper<V2Good> {


    public V2Good findGood(@Param("doctorId") long doctorId, @Param("goodType")String goodType);

    List<VoGoods> findAllGoods(Pager pager);

    V2Good findGoodById(long id);

}