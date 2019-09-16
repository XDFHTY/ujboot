package com.cj.sshop.service;

import com.cj.core.v2entity.V2Order;
import com.cj.common.domain.VoScore;
import com.cj.sshop.domain.VoDoctorOrder;

import java.util.Date;
import java.util.List;

public interface UserOrderService {

    long findOrderByUserIdAndDoctorId(long userId,long doctorId, String goodType);


    List<V2Order> findOrderByUserId(long id , String type);


    //评价
    int evaluateOrder(long userId,String orderNo,int commentScore);



    VoScore findScore(long doctorId);

    List<VoDoctorOrder> findDoctorOrder(long doctorId, Date date);


    //退款
    int refund(long userId,String orderNo);
}
