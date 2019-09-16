package com.cj.sconsult.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2Team;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by JuLei on 2019/6/26.
 */
public interface TeamService {
    //创建团队
    ApiResult create(V2Team team, HttpServletRequest request);

    //解散团队
    ApiResult dismiss(Long teamId, HttpServletRequest request);

    //加入团队
    ApiResult join(Long teamId, Long userId, HttpServletRequest request);

    //移除团员
    ApiResult deletePerson(Long userId, Long teamId, HttpServletRequest request);

    /**
     * 根据姓名或手机号搜索  医生信息
     * 可以查询的角色包括 专科医生 家庭医生 营养师 乡干部 卫健委干部 离退休干部 护士 健康管理师
     */
    ApiResult findDoctorInfo(String parameter, HttpServletRequest request);

    //查询医生id查询团队信息
    ApiResult findTeamInfo(Long doctorId, HttpServletRequest request);

    //分页查询团队列表
    Pager findTeamList(Pager pager);

    //编辑团信息
    int update(V2Team team, HttpServletRequest request);

    //根据团队id查询成员列表
    Pager findPersonList(Pager pager);
}
