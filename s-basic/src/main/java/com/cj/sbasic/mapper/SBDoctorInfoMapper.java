package com.cj.sbasic.mapper;

import com.cj.core.entity.DoctorInfo;
import com.cj.core.domain.OldPager;

import com.cj.sbasic.domain.VoDoctorInfo;
import com.cj.sbasic.vo.DoctorCheckVO;
import com.cj.sbasic.vo.DoctorDetailVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* Created by Mybatis Generator 2019/04/11
*/
public interface SBDoctorInfoMapper {
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
     * 分页获取所有的医生审核详情信息
     * @return
     */
    List<DoctorCheckVO> getDoctorCheckPage(OldPager oldPager);

    /**
     * 分页查询时的数据总条数
     * @param oldPager
     * @return
     */
    int getDoctorCheckCount(OldPager oldPager);

    /**
     * 通过医生id获取医生的详情信息
     * @param id
     * @return 返回医生详情信息
     */
    DoctorDetailVO getDoctorDetailById(Long id);

    /**
     * 通过医生的审核
     * @param id
     * @return
     */
    int updateStatePassById(Long id);

    int updateGoodPrice(@Param("id") long id,@Param("goodPrice") BigDecimal goodPrice);

    /**
     * 驳回医生的审核
     * @param id
     * @return
     */
    int updateStateFailById(Long id);

    /**
     * 根据userId查询医生信息
     * @param userId
     * @return
     */
    DoctorInfo selectByUserId(Long userId);

    VoDoctorInfo findDoctorInfoByDoctorInfoId(long doctorInfoId);
}