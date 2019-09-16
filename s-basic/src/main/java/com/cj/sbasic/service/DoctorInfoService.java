package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.sbasic.vo.DoctorDetailVO;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


/**
 * 医生用户审核
 */
public interface DoctorInfoService {
    /**
     * 按条件分页查询医生审核信息
     * @return
     */
    OldPager getDoctorCheckPage(OldPager oldPager);


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
    int updateStatePassById(Long id, BigDecimal goodPrice);

    /**
     * 驳回医生的审核
     * @param id
     * @return
     */
    int updateStateFailById(Long id);

    /**
     * 导出医生审核信息
     * @param oldPager
     * @return
     */
    String exportDoctorCheck(OldPager oldPager, HttpServletResponse response);
}
