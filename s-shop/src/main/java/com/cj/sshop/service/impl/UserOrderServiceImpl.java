package com.cj.sshop.service.impl;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.cj.core.domain.ApiResult;
import com.cj.core.exception.MyException;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2Order;
import com.cj.common.domain.VoScore;
import com.cj.sshop.domain.VoDoctorOrder;
import com.cj.sshop.domain.VoWxPay;
import com.cj.sshop.mapper.V2OrderMapper;
import com.cj.sshop.service.ShopCallImService;
import com.cj.sshop.service.UserOrderService;
import com.cj.sshop.util.alipay.AliPayUtil;
import com.cj.sshop.util.wxpay.WxPayUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderServiceImpl implements UserOrderService {


    @Resource
    private V2OrderMapper orderMapper;


    Gson gson = JsonUtil.gson;


    @Override
    public long findOrderByUserIdAndDoctorId(long userId, long doctorId, String goodType) {
        //查詢用戶的有效咨詢包
        V2Order order = orderMapper.findOrderByGoodType(userId, doctorId, goodType);
        if (order == null) {
            return 0;
        }
        return order.getExpireTime().getTime() - System.currentTimeMillis();
    }

    @Override
    public List<V2Order> findOrderByUserId(long id, String type) {
        return orderMapper.findOrderByUserId(id, type);
    }


    @Override
    public int evaluateOrder(long userId, String orderNo, int commentScore) {
        return orderMapper.evaluateOrder(userId, orderNo, commentScore);
    }


    @Override
    public VoScore findScore(long doctorId) {
        return orderMapper.findScore(doctorId);
    }

    //医生收益
    @Override
    public List<VoDoctorOrder> findDoctorOrder(long doctorId, Date date) {
        return orderMapper.findDoctorOrder(doctorId, date);
    }



    @Resource
    private ShopCallImService shopCallImService;


    @Override
    @Transactional
    public int refund(long userId, String orderNo) {
        System.out.println("===============================申请退款");
        V2Order order = orderMapper.findOrder(orderNo);
        if (order==null){
            throw new  MyException(ApiResult.FAIL_CODE,"订单不存在");
        }else if (userId!=order.getBuyerId()){

            throw new  MyException(ApiResult.FAIL_CODE,"这不是你的订单");
        }else if (new Date().compareTo(order.getLastRefundTime()) == 1){

            throw new  MyException(ApiResult.FAIL_CODE,"已超出退款期限，无法退款");
        }

        Map map = new HashMap();
        map.put("userId",order.getBuyerId());
        map.put("doctorId",order.getSellerId());
        map.put("startDate",order.getPayTime());
        map.put("endDate",order.getExpireTime());

        String json = gson.toJson(map);
        ApiResult apiResult = shopCallImService.userToadmin(json);
        if (apiResult.getCode() == 1000){
            throw new  MyException(ApiResult.FAIL_CODE,"调用失败");
        }else if (!(Boolean) apiResult.getData()){

            throw new  MyException(ApiResult.FAIL_CODE,"不符合退款条件");
        }

        //修改退款状态
        order.setOrderStatus("5");
        int i = orderMapper.updateById(order);
        if (i==0){
            throw new  MyException(ApiResult.FAIL_CODE,"网络异常，请重试");

        }

        int j = 0;

        //允许退款
        if ("1".equals(order.getPayType())){
            AlipayTradeRefundResponse refund = AliPayUtil.refund(order);
            System.out.println(gson.toJson(refund));
            if ("10000".equals(refund.getCode())){
                System.out.println("===========>>修改订单信息");
                j = orderMapper.putOrder(refund.getOutTradeNo());
            }else {
                throw new MyException(refund.getCode()+": "+refund.getMsg());
            }
        }else if ("2".equals(order.getPayType())){
            VoWxPay voWxPay = WxPayUtil.refund(order);
            System.out.println(gson.toJson(voWxPay));
            if ("SUCCESS".equals(voWxPay.getReturn_code()) && "SUCCESS".equals(voWxPay.getResult_code())){
                System.out.println("===========>>修改订单信息");
                j = orderMapper.putOrder(voWxPay.getOut_trade_no());

            }else{
                throw new MyException(voWxPay.getErr_code()+": "+voWxPay.getErr_code_des());
            }
        }


        return j;
    }
}
