package com.cj.sshop.controller;

import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.AccessLimit;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.v2entity.V2Give;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.service.GiveService;
import com.cj.sshop.service.OrderService;
import com.cj.sshop.service.UserOrderService;
import com.cj.sshop.util.wxpay.WxPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/s-shop/api/v2/orders/u/order")
@Api(tags = "用户端: 订单业务（下单、支付、查询等）")
@RestController
@Slf4j
public class OrderUController {

    @Resource
    private OrderService orderService;


    //=============================================用户下单
    @ApiOperation(value = "用户下单,传入商品id,返回订单详情",response = V2Order.class)
    @PostMapping("/createOrder/{goodId}")
    @Log(name = "订单业务", value = "用户下单")
    @AccessLimit
    public ApiResult createOrder(HttpServletRequest request, @PathVariable long goodId) {
        long userId = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(orderService.createOrder(userId, goodId));
    }

    //================================================支付宝支付
    //app支付宝下单
    @ApiOperation(value = "支付宝下单,传入订单编号，返回支付宝单号")
    @PostMapping("/createAliPayNo/{orderNo}")
    @Log(name = "订单业务", value = "支付宝下单")
    public ApiResult createAliPayNo(HttpServletRequest request, @PathVariable String orderNo) {


        return ResultUtil.result(orderService.createAliPayNo(orderNo));
    }


    //支付宝异步回调
    @ApiOperation(value = "支付宝异步回调")
    @PostMapping("/aliNotify")
    @Log(name = "订单业务", value = "支付宝异步回调")
    public String payAliNotify(HttpServletRequest request) {
        return orderService.aliNotify(request);
    }



    //================================================微信支付


    @ApiOperation(value = "微信下单,传入订单编号，返回订单详情")
    @PostMapping("/createWxPayNo/{orderNo}")
    @Log(name = "订单业务", value = "微信下单")
    public ApiResult createWxPayNo(HttpServletRequest request, @PathVariable String orderNo) throws Exception {


        return ResultUtil.result(orderService.createWxPayNo(request, orderNo));
    }


    /**
     * 注意：如果是沙箱环境，一提交订单就会立即异步通知，而无需拉起微信支付收银台的中间页面
     *
     * @param request
     * @throws Exception
     */
    @PostMapping("/wxNotify")
    @ApiOperation("微信异步回调")
    @Log(name = "订单业务", value = "微信异步回调")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        orderService.wxNotify(request, response);
    }


    //==========================================查询
    @Resource
    private UserOrderService userOrderService;


    @ApiOperation(value = "查询用户咨詢訂單有效時間,返回倒計時秒數,用於判斷是否能聊天")
    @GetMapping("/find/{userId}/{doctorId}/{goodType}")
    @Log(name = "用戶订单", value = "查询用户咨詢訂單有效時間")
    public ApiResult findOrderByUserIdAndDoctorId(@PathVariable long userId, @PathVariable long doctorId, @PathVariable String goodType) {

        return ResultUtil.result(userOrderService.findOrderByUserIdAndDoctorId(userId, doctorId, goodType));
    }

    @Resource
    private GiveService giveService;

    //=========================================用戶訂單頁面==========================================================
    //查询用户所有订单

    @ApiOperation(value = "查询用户所有订单,1-有效訂單，0-過期訂單，2-未支付订单",response = V2Order.class)
    @GetMapping("/find/{type}")
    @Log(name = "用戶订单", value = "查询用户所有订单")
    public ApiResult findOrderByUserId(HttpServletRequest request, @PathVariable String type) {
        long id = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(userOrderService.findOrderByUserId(id, type));
    }


    @ApiOperation(value = "查询用户0元支付次數",response = V2Give.class)
    @GetMapping("/find0Pay")
    @Log(name = "用戶订单", value = "查询用户0元支付次數")
    public ApiResult find0Pay(HttpServletRequest request) {
        long id = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(giveService.find0Pay(id));
    }

    @ApiOperation(value = "用戶評價訂單,只需要传入 orderNo 和 commentScore(1-10整数得分)")
    @PutMapping("/evaluate/{orderNo}/{commentScore}")
    @Log(name = "用戶订单", value = "用戶評價訂單")
    public ApiResult evaluate(HttpServletRequest request, @PathVariable String orderNo,@PathVariable int commentScore) {
        long id = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(userOrderService.evaluateOrder(id,orderNo,commentScore));
    }


    @ApiOperation(value = "用戶退款")
    @PutMapping("/refund/{orderNo}")
    @Log(name = "用戶订单", value = "用戶退款")
    @AccessLimit
    public ApiResult refund(HttpServletRequest request, @PathVariable String orderNo) {
        long id = Long.parseLong(request.getHeader("id"));

        return ResultUtil.result(userOrderService.refund(id,orderNo));
    }

    @ApiOperation(value = "获取wxkey")
    @GetMapping("/get")
    @Log(name = "获取wxkey", value = "获取wxkey")
    public ApiResult get() throws Exception {

        return ResultUtil.result(WxPayUtil.doGetSandboxSignKey());
    }
}
