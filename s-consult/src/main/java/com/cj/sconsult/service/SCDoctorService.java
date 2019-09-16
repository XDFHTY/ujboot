package com.cj.sconsult.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.common.domain.BindDoctorVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
public interface SCDoctorService {

    //前台咨询服务: 查询医生列表
    ApiResult getDocPage(String json, HttpServletRequest request);

    //前台查询咨询员列表
    OldPager findPageConsultant(OldPager oldPager);

    //绑定,解绑 医生/专家
    ApiResult bindDoctor(Map map, HttpServletRequest request);

    //医生 同意/拒绝 绑定
    ApiResult acceOrRefuse(Map map, HttpServletRequest request);

    //查询已绑定的家庭医生或专家
    ApiResult getDoctorById(String type, Long userId,HttpServletRequest request);

    //查询医生绑定的患者列表
    OldPager findBindPatientList(OldPager oldPager);

    //删除加好友记录
    int deleteBindData(Integer userBindDoctorId);

    //用户绑定医生
    ApiResult userBindDoctor(BindDoctorVo bindDoctorVo, HttpServletRequest request);

    //用户解绑医生
    ApiResult userRelieveDoctor(Map map, HttpServletRequest request);

    //查询团队详情和成员信息
    ApiResult findTeamInfo(Long teamId, HttpServletRequest request);

    //根据医生id查询医生详情
    ApiResult findDoctorInfo(Long userId, HttpServletRequest request);

    //查询已绑定的管理机构（卫健委、乡干部、离退休干部）
    ApiResult getBindManage(Long userId, HttpServletRequest request);

    //查询有没有解绑资格
    ApiResult findUntying(Long userId, Long doctorId, HttpServletRequest request);

    //查询用户的头像和昵称
    ApiResult findUserInfo(Long userId, HttpServletRequest request);
}
