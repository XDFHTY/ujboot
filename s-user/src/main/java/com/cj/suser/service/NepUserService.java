package com.cj.suser.service;

import com.cj.core.domain.Pager;
import com.cj.suser.domain.TModel;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 肾病医生管理业务层
 * @date： 2019/6/28 18:50
 **/
public interface NepUserService {

    /**
     * 分页查询医生列表
     * @param pager
     * @return
     */
    Pager findDoctor(Pager pager);
    /**
     * 分页查询患者生列表
     * @param pager
     * @return
     */
    Pager findUserByDoctorId(Pager pager);

    /**
     * 根据医生id查询医生团队
     * @param doctorId
     * @return
     */
    TModel findTeamByDoctorId(Long doctorId);

    /**
     * 导出
     * @param map
     * @return
     */
    int export(Map map, HttpServletResponse response);
}
