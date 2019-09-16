package com.cj.sshop.service.impl;

import com.cj.core.util.JsonUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.exception.MyException;
import com.cj.core.v2entity.V2Give;
import com.cj.core.v2entity.V2Good;
import com.cj.core.v2entity.V2Order;
import com.cj.core.v2entity.V2Ratio;
import com.cj.sshop.domain.VoPayNo;
import com.cj.sshop.mapper.V2GiveMapper;
import com.cj.sshop.mapper.V2GoodMapper;
import com.cj.sshop.mapper.V2OrderMapper;
import com.cj.sshop.mapper.V2RatioMapper;
import com.cj.sshop.service.OrderService;
import com.cj.sshop.util.alipay.AliPayUtil;
import com.cj.sshop.util.wxpay.WxPayUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private V2GoodMapper goodMapper;

    @Resource
    private V2GiveMapper giveMapper;

    @Resource
    private V2RatioMapper ratioMapper;

    @Resource
    private V2OrderMapper orderMapper;

    private Lock lock = new ReentrantLock();// 锁对象


    @Override
    public V2Order createOrder(long userId, long goodId) {
        //查询商品
        V2Good good = goodMapper.selectById(goodId);
        if (good == null) {
            throw new MyException("商品不存在");
        }

        long bindId = getBindId(userId, good);
        if (bindId == 0) {
            throw new MyException("无法下单，你没有绑定此类医生");
        }
        V2Order order = null;
        order = addOrder(userId, bindId, good);
        return order;
    }

    public V2Order addOrder(long userId, long bindId, V2Good good) {
        V2Order order = new V2Order();
        //查询有效订单
        V2Order order0 = orderMapper.findOrderByGoodType(userId, bindId, good.getGoodType());
        if (order0 != null) {
            throw new MyException("订单已存在，请过期后再购买");
        }

        //查询此商品用户是否可以0元支付
        int num = giveMapper.findGiveNum(userId, good.getGoodType());
        //查询此商品平台抽成千分比
        V2Ratio v2Ratio = ratioMapper.findRatioByType(good.getGoodType());


        //应付金额 分
        int shouldPay = good.getGoodPrice().multiply(new BigDecimal(100)).intValue();
        int actualPay = num > 0 ? 0 : shouldPay;
        //生成订单号,当前时间+商品id+应付+实付
        String orderNo = "";
        lock.lock();
        try {
            orderNo = DateUtil.dateToStr(new Date(), DateUtil.YYYYMMDDHHMMSSSSS) + "G" + good.getGoodId() + "S" + shouldPay + "A" + actualPay;
        } finally {
            lock.unlock();
        }
        System.out.println("===================订单编号" + orderNo);

        order.setOrderNo(orderNo);
        order.setGoodId(good.getGoodId());
        order.setGoodType(good.getGoodType());
        order.setGoodName(good.getGoodName());
        order.setShouldPay(shouldPay);
        order.setActualPay(actualPay);
        order.setSellerId(good.getSellerId());
        order.setBindId(bindId);
        order.setBuyerId(userId);
        order.setBiosGet(new BigDecimal(actualPay * v2Ratio.getRatioNum()).divide(BigDecimal.valueOf(1000)));
        order.setCreateTime(new Date());
        order.setOrderStatus("1");

        V2Give v2Give = null;
        if (shouldPay > 0 && actualPay == 0) {
            v2Give = new V2Give();
            //有赠送
            order.setOrderStatus("2"); //已支付
            //次數-1
            v2Give.setUserId(userId);
            v2Give.setGoodType(good.getGoodType());
            v2Give.setGiveNum(-1);
            v2Give.setOrderNo(orderNo);

            Date payTime = new Date();
            //0元商品
            order.setPayType("3");
            order.setPayTime(payTime);
            order.setOrderStatus("2");
            order.setExpireTime(DateUtil.dayAddNum(payTime, good.getValidDate()));
            order.setLastRefundTime(DateUtil.dayAddNum(order.getExpireTime(), 2));


        } else if (shouldPay == 0 && actualPay == 0) {
            Date payTime = new Date();
            //0元商品
            order.setPayType("3");
            order.setPayTime(payTime);
            order.setOrderStatus("2");

            order.setExpireTime(DateUtil.dayAddNum(payTime, good.getValidDate()));
            order.setLastRefundTime(DateUtil.dayAddNum(order.getExpireTime(), 2));


        }


        int i = orderMapper.insert(order);
        //处理赠送
        if (v2Give != null) {
            v2Give.setOrderId(order.getOrderId());
            giveMapper.insert(v2Give);
        }


        return order;

    }

    @Override
    public String createAliPayNo(String orderNo) {
        V2Order order = orderMapper.findOrder(orderNo);
        return AliPayUtil.payAppApi(order);
    }


    @Override
    public String aliNotify(HttpServletRequest request) {
        VoPayNo voPayNo = null;
        try {
            voPayNo = AliPayUtil.notify(request);
            if (voPayNo == null) {
                return "fail";
            }
            if (voPayNo.getTradeStatus().equals("TRADE_SUCCESS")) {
                int i = putNotify(voPayNo);
                if (i == 1) {
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createWxPayNo(HttpServletRequest request, String orderNo) {
        V2Order order = orderMapper.findOrder(orderNo);

        String str = null;
        try {
            str = WxPayUtil.createAPPOrder(order, request);
        } catch (Exception e) {
            log.error("=================微信下单失败");
            log.error(e.getMessage());
        }
        return str;
    }

    @Override
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) {
        String s = null;
        try {
            VoPayNo voPayNo = WxPayUtil.notify(request, response);
            if (voPayNo != null) {
                int i = putNotify(voPayNo);
                if (i > 0) {
                    System.out.println("==============微信回调处理成功");
                    s = "<xml>\n" +
                            "\n" +
                            "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                            "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                            "</xml>";


                }
            }


        } catch (Exception e) {
            log.error("=================微信回调解析失败");
            log.error(e.getMessage());
        }
        return s;
    }

    //处理回调
    public int putNotify(VoPayNo voPayNo) {

        String str = voPayNo.getGmtPayment();
        System.out.println("==========>>支付时间：" + str);
        Date payTime = new Date();
        if ("1".equals(voPayNo.getPayType())) {
            payTime = DateUtil.strToDate(str, DateUtil.YYYY_MM_DDHHMMSS);

        } else if ("2".equals(voPayNo.getPayType())) {
            payTime = DateUtil.strToDate(str, DateUtil.YYYYMMDDHHMMSS);

        }

        //查詢訂單
        V2Order order = orderMapper.findOrder(voPayNo.getOutTradeNo());
        if (!"1".equals(order.getOrderStatus())) {
            return 10;
        }
        order.setPayType(voPayNo.getPayType());
        order.setPayNo(voPayNo.getTradeNo());
        order.setPayTime(payTime);
        order.setOrderStatus("2");
        //查詢商品有效期
        V2Good good = goodMapper.selectById(order.getGoodId());

        order.setExpireTime(DateUtil.dayAddNum(payTime, good.getValidDate()));
        order.setLastRefundTime(DateUtil.dayAddNum(order.getExpireTime(), 2));

        //更新订单
        int i = orderMapper.updateById(order);

        //处理赠送
        V2Give give = new V2Give();
        give.setUserId(order.getBuyerId());
        give.setOrderId(order.getOrderId());
        give.setOrderNo(order.getOrderNo());
        give.setGiveNum(3);
        int j = 0;
        switch (good.getGoodType()) {
            case "1":
                give.setGoodType("3");
                j += giveMapper.insert(give);
                break;
            case "2":
                give.setGoodType("4");
                j += giveMapper.insert(give);
                give.setGoodType("5");
                j += giveMapper.insert(give);

                break;


        }

        return i;

    }


    public long getBindId(long id, V2Good good) {
        Long bindId = null;
        switch (good.getGoodType()) {
            case "1":
                bindId = giveMapper.findBindIdByType(id, "1");
                break;
            case "2":
                bindId = giveMapper.findBindIdByType(id, "2");
                break;
            case "3":
            case "4":
            case "5":
            case "6":
                bindId = good.getSellerId();
                break;
        }

        return bindId == null ? 0 : bindId;
    }

}
