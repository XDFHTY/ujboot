package com.cj.sshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.common.domain.VoScore;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.domain.VoDoctorOrder;
import com.cj.sshop.domain.VoFinanceOrder;
import com.cj.sshop.domain.VoFinanceResp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* Created by Mybatis Generator 2019-06-21 16:16:06
*/
public interface V2OrderMapper extends BaseMapper<V2Order> {


    V2Order findOrder(String orderNo);


    V2Order findOrderByUserIdAndDoctorId(@Param("doctorId") long doctorId, @Param("userId") long userId);

    //查詢用戶所有訂單
    List<V2Order> findOrderByUserId(@Param("id") long id, @Param("type") String type);

    //查詢用戶綁定醫生端的有效的訂單
    V2Order findOrderByGoodType(@Param("id") long userId,@Param("doctorId") long doctorId, @Param("goodType") String goodType);


    //财务查询
    List<VoFinanceOrder> findAllOrder(@Param("dateTime") Date dateTime, @Param("incomeType") String incomeType, @Param("bindId") long bindId);

    VoScore findScore(long doctorId);

    List<VoDoctorOrder> findDoctorOrder(@Param("doctorId") long doctorId, @Param("date") Date date);



    //订单评价
    public int evaluateOrder(@Param("userId") long userId, @Param("orderNo")String orderNo, @Param("commentScore")int commentScore);

    //用户退款
    int putOrder(String orderNo);
}