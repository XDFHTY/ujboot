package com.cj.sshop.util.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.domain.VoPayNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class AliPayUtil {


    @Autowired
    private AlipayConfig alipayConfig;

    private static AlipayConfig config;

    static AlipayClient alipayClient = null;

    @PostConstruct
    public void init(){
        config = this.alipayConfig;
        //实例化客户端
        alipayClient = new DefaultAlipayClient(config.getUrl(), config.getAppid(),
                config.getRsa_private_key(), config.getFormat(), config.getCharset(), config.getAlipay_public_key(),
                config.getSigntype());
    }




    /**
     * APP支付
     * @param order
     * @return
     */
    public static String payAppApi(V2Order order){

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();


        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(order.getGoodName());
        model.setOutTradeNo(order.getOrderNo());
        model.setTotalAmount(div(order.getActualPay(), 100));
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setGoodsType("0");
        model.setTimeoutExpress("1d");  //当天24点关闭订单


        request.setBizModel(model);
        request.setNotifyUrl(config.getNotify_url());
        try {

            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (AlipayApiException e) {
            log.error("==================>>>支付宝生成APP订单失败");
            log.error(e.getErrMsg());

        }

        return null;

    }


    public static AlipayTradeRefundResponse refund(V2Order order) {

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();

        model.setOutTradeNo(order.getOrderNo());
        model.setRefundAmount(div(order.getActualPay(), 100));

        request.setBizModel(model);

        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("==================>>>支付宝APP订单退款失败");
            log.error(e.getErrMsg());

        }

        return null;
    }

//    交易状态：
// WAIT_BUYER_PAY（交易创建，等待买家付款）、
// TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
// TRADE_SUCCESS（交易支付成功）、
// TRADE_FINISHED（交易结束，不可退款）

    public static String query(String orderNo) {

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderNo);

        request.setBizModel(model);

        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeQueryResponse response = alipayClient.sdkExecute(request);
            AlipayTradeQueryResponse response = alipayClient.execute(request);

            System.out.println(response.getBody());


        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("==================>>>支付宝APP订单查询失败");
            log.error(e.getErrMsg());

        }

        return null;
    }

    public static VoPayNo notify(HttpServletRequest request) throws Exception{
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        System.out.println("=============支付宝异步回调>>\n" +JsonUtil.gson.toJson(params));

        VoPayNo voPayNo = new VoPayNo();
        boolean signVerified = false; // 调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, config.getAlipay_public_key(), config.getCharset(), config.getSigntype());
            System.out.println("===============验签："+signVerified);
            if (signVerified) { // 验证成功 更新订单信息
                voPayNo.setPayType("1");
                // 商户订单号
                voPayNo.setOutTradeNo(request.getParameter("out_trade_no"));
                // 交易状态
                voPayNo.setTradeStatus(request.getParameter("trade_status"));

                //  支付宝交易单号
                voPayNo.setTradeNo(request.getParameter("trade_no"));

                //支付时间
                voPayNo.setGmtPayment(request.getParameter("gmt_payment"));

                return voPayNo;

            } else {
                System.out.println("===========异步通知失败");
            }
        } catch (AlipayApiException e) {
            log.error("==============支付宝验签失败");
        }

        return null;
    }


    public static String createOrder(V2Order order){
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();

        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(order.getOrderNo());
        model.setTotalAmount(div(order.getActualPay(), 100));
        model.setSubject(order.getGoodName());


//        request.setBizModel(model);
        request.setNotifyUrl(config.getNotify_url());
        request.setReturnUrl(config.getReturn_url());
        String str = JsonUtil.gson.toJson(model);
        System.out.println(str);
        request.setBizContent(str);
        try {

            AlipayTradeCreateResponse response = alipayClient.execute(request);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (AlipayApiException e) {
            log.error("==================>>>支付宝生成统一订单失败");
            log.error(e.getErrMsg());

        }

        return null;

    }


    // a/b
    public static String div(int a, int b) {
        BigDecimal b1 = BigDecimal.valueOf(a);
        BigDecimal b2 = BigDecimal.valueOf(b);
        String s = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();
        return s;
    }


}
