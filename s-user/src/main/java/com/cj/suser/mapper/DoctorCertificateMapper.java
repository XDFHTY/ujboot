package com.cj.suser.mapper;

import com.cj.core.entity.DoctorCertificate;

/**
* Created by Mybatis Generator 2019/03/29
*/
public interface DoctorCertificateMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long doctorCertificateId);

    /**
     *插入
     */
    int insert(DoctorCertificate record);

    /**
     *动态插入
     */
    int insertSelective(DoctorCertificate record);

    /**
     *通过id查找
     */
    DoctorCertificate selectByPrimaryKey(Long doctorCertificateId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(DoctorCertificate record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(DoctorCertificate record);
}