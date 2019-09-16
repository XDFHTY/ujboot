package com.cj.spension.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.DoctorInfo;
import com.cj.common.domain.DoctorModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface SPDoctorInfoMapper {
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
    /**
     * 分页查询条数
     */
    int findDocInfoCount(OldPager oldPager);
    /**
     * 分页查询数据
     */
    List<DoctorModel> findDocInfoPage(OldPager page);
    /**
     * 查询数据
     */
    List<DoctorModel> findDocInfo(OldPager page);
    /**
     * 根据id查询
     */
    DoctorModel findDocByID(Long userId);
    /**
     * 修改证书
     */
    int updateDocByID(DoctorCertificate doctorCertificate);

    /**
     * 查询是否绑定
     */
    int findIsBind(@Param("doctorId") Long doctorId,@Param("userId") Long userId);
}