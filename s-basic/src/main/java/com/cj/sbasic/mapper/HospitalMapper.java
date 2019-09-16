package com.cj.sbasic.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Hospital;
import com.cj.sbasic.domain.HospitalModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
@Repository("sbasicHospitalMapper")
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
     * 通过地区查询医院条数
     * @param oldPager
     * @return
     */
    int findHospitalByRegionCount(OldPager oldPager);
    /**
     * 通过地区查询医院列表
     */
    List<HospitalModel> findHospitalByRegion(OldPager oldPager);
    /**
     * 删除否使用医院
     * @param hospitalId
     * @return
     */
    int deleteHospital(Long hospitalId);
}