package com.cj.suser.service;

import com.cj.common.domain.BindDoctorVo;
import com.cj.core.domain.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/6/28 15:11
 **/
public interface UserCallConsultService {
    /**
     * 绑定医生
     * @return
     */
    @PostMapping(value = "/s-consult/api/v1/doctor/userBindDoctor",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult userBindDoctor(BindDoctorVo bindDoctorVo);

}
