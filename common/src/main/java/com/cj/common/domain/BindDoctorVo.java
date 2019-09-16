package com.cj.common.domain;

import lombok.Data;

import java.util.List;

/**
 * 用户绑定医生集合 实体类
 * Created by JuLei on 2019/6/27.
 */
@Data
public class BindDoctorVo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生id集合
     */
    private List<Long> doctorIds;
}
