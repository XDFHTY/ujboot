package com.cj.sconsult.entity;

import lombok.Data;

/**
 * Created by XD on 2019/4/12.
 */
@Data
public class VoEvaluate {
    //评分id
    private Long evaluateId;

    //医生id
    private Long doctorId;

    //用户id
    private Long userId;

    //评分
    private Double score;
}
