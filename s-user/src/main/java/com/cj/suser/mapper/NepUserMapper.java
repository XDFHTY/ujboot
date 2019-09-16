package com.cj.suser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.entity.DoctorCertificate;
import com.cj.core.entity.User;
import com.cj.suser.domain.FnuModel;
import com.cj.suser.domain.FpuModel;
import com.cj.suser.domain.TModel;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/6/28 18:52
 **/
public interface NepUserMapper extends BaseMapper<User> {

    /**
     * 分页查询医生列表
     * @param pager
     * @return
     */
    List<FnuModel> findDoctor(Pager pager);
    /**
     * 分页查询患者列表
     * @param pager
     * @return
     */
    List<FpuModel> findUserByDoctorId(Pager pager);

    /**
     * 根据医生id查询医生团队
     * @param doctorId
     * @return
     */
    TModel findTeamByDoctorId(Long doctorId);

    /**
     * 全查
     * @param map
     * @return
     */
    List<FnuModel> findAll(Map<String,String> map);

    /**
     * 根据ID查询证书
     * @param doctorId
     * @return
     */
    List<DoctorCertificate> findDoctorCertificateById(Long doctorId);
}
