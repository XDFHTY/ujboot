package com.cj.sconsult.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户绑定医生实体类
 * Created by XD on 2019/4/9.
 */
@Data
public class VoUserBindDoctor {
    /**
     * 用户绑定医生表
     */
    private Long userBindDoctorId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 医生id
     */
    private Long doctorId;

    /**
     *  医生分类 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部
     */
    private String doctorType;

    /**
     * 绑定状态
     */
    private String isBind;

    /**
     * 操作时间
     */
    private Date operationTime;

}
