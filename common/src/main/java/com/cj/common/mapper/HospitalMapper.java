package com.cj.common.mapper;

import com.cj.core.entity.Hospital;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* Created by Mybatis Generator 2019/03/06
*/
@Repository("commonHospitalMapper")
public interface HospitalMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long hospitalId);

    /**
     *插入
     */
    int insert(Hospital record);

    /**
     *动态插入
     */
    int insertSelective(Hospital record);

    /**
     *通过id查找
     */
    Hospital selectByPrimaryKey(Long hospitalId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Hospital record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Hospital record);
    /**
     * 通过地区查询医院列表
     */
    List<Hospital> findHospitalByRegion(Map<String, Long> map);
}