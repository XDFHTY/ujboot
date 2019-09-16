package com.cj.sshop.service;

import com.cj.core.v2entity.V2Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrderService {

    V2Order createOrder(long userId, long goodId);

    String createAliPayNo(String orderNo);

    String aliNotify(HttpServletRequest request);


    String createWxPayNo(HttpServletRequest request,String orderNo);

    String wxNotify(HttpServletRequest request, HttpServletResponse response);


}
