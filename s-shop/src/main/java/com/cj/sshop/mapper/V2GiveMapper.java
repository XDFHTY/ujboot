package com.cj.sshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.v2entity.V2Give;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
* Created by Mybatis Generator 2019-06-21 16:16:06
*/
public interface V2GiveMapper extends BaseMapper<V2Give> {

    Integer findGiveNum(@Param("id") long id, @Param("goodType") String goodType);


    List<V2Give> find0Pay(long userId);

    Long findBindIdByType(@Param("userId") long id, @Param("type") String type);
}