package com.cj.suser.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.FallIll;
import com.cj.suser.domain.VoFallIllById;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/15
*/
public interface FallIllMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long fallIllId);

    /**
     *插入
     */
    int insert(FallIll record);

    /**
     *动态插入
     */
    int insertSelective(FallIll record);

    /**
     *通过id查找
     */
    FallIll selectByPrimaryKey(Long fallIllId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(FallIll record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(FallIll record);

    public List<List<?>> findFallIll(OldPager oldPager);

    public VoFallIllById findFallIllById(long fallIllId);

    //删除用户疾病信息
    public int deleteFallIll(@Param("userId")long userId, @Param("fallIllId")long fallIllId);
}