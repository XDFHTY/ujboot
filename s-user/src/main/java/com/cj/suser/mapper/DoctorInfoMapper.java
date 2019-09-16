package com.cj.suser.mapper;

import com.cj.core.entity.DoctorInfo;
import com.cj.core.entity.UserInfo;
import com.cj.suser.domain.VoDoctorInfo;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019/03/09
*/
public interface DoctorInfoMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long doctorInfoId);

    /**
     *插入
     */
    int insert(DoctorInfo record);

    /**
     *动态插入
     */
    int insertSelective(DoctorInfo record);

    /**
     *通过id查找
     */
    DoctorInfo selectByPrimaryKey(Long doctorInfoId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(DoctorInfo record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(DoctorInfo record);

    //通过userId查询doctorInfo
    public VoDoctorInfo findDoctorInfoByUserId(long userId);

    public int updateUserHeadUrl(@Param("doctorId") long doctorId, @Param("headUrl") String headUrl);

    //根据doctorId更新信息
    public int updateDoctorInfo(DoctorInfo doctorInfo);
}