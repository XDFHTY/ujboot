package com.cj.sshop.util.wxpay;

import com.cj.core.util.IpUtil;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.ObjectUtil;
import com.cj.core.util.SpringUtil;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.domain.VoPayNo;
import com.cj.sshop.domain.VoWxPay;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.*;

@Slf4j
public class WxPayUtil {



    private static WxPayClient wxPayClient = SpringUtil.getBean(WxPayClient.class);

    private static WxPayConfig wxPayConfig = SpringUtil.getBean(WxPayConfig.class);

    private static WXPay wxPay = new WXPay(wxPayConfig);

    static Gson gson = JsonUtil.gson;


    public static String createAPPOrder(V2Order order, HttpServletRequest request) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("body", "爱捷健康"+order.getGoodName());
        reqData.put("out_trade_no", order.getOrderNo());
        // 订单总金额，单位为分
        reqData.put("total_fee", order.getActualPay().toString());
        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        reqData.put("spbill_create_ip", IpUtil.getIpAddr(request));
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        reqData.put("notify_url", wxPayConfig.getNotifyUrl());
        reqData.put("trade_type", "APP");
        System.out.println("==========请求参数\n"+gson.toJson(reqData));

        Map<String, String> responseMap = wxPay.unifiedOrder(reqData);
        System.out.println("==========响应数据\n"+gson.toJson(responseMap));
        VoWxPay voWxPay = new VoWxPay();
        voWxPay = (VoWxPay) ObjectUtil.mapStringToObject(responseMap,VoWxPay.class);
        voWxPay.setTimestamp(System.currentTimeMillis()/1000+"");
        if (WXPayConstants.SUCCESS.equals(voWxPay.getReturn_code()) && WXPayConstants.SUCCESS.equals(voWxPay.getResult_code())) {
            System.out.println("================签名");
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", voWxPay.getAppid());
            parameters.put("timestamp", voWxPay.getTimestamp());
            parameters.put("noncestr", voWxPay.getNonce_str());
            parameters.put("package", "Sign=WXPay");
            parameters.put("partnerid", voWxPay.getMch_id());
            parameters.put("prepayid", voWxPay.getPrepay_id());
            String sign = createSign("UTF-8", parameters);
            voWxPay.setSign(sign);
        }
        return gson.toJson(voWxPay);
    }

    public static VoPayNo notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("======================微信回调");
        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);
        System.out.println(reqData.toString());
        VoWxPay voWxPay = new VoWxPay();
        voWxPay = (VoWxPay) ObjectUtil.mapStringToObject(reqData,VoWxPay.class);
        System.out.println("======================订单号: "+voWxPay.getOut_trade_no());
        VoPayNo voPayNo = new VoPayNo();
        if (WXPayConstants.SUCCESS.equals(voWxPay.getReturn_code()) && WXPayConstants.SUCCESS.equals(voWxPay.getResult_code())) {
            boolean signatureValid = wxPay.isPayResultNotifySignatureValid(reqData);
            if (signatureValid) {
                voPayNo.setPayType("2");
                voPayNo.setOutTradeNo(voWxPay.getOut_trade_no());
                voPayNo.setTradeStatus("2");
                voPayNo.setTradeNo(voWxPay.getTransaction_id());
                voPayNo.setGmtPayment(voWxPay.getTime_end());
            }else {
                System.out.println("===============验签失败");
            }
        }
        return voPayNo;
    }
    /**
     * 获取沙盒 sandbox_signkey
     *
     * @author yclimb
     * @date 2018/9/18
     */
    public static String doGetSandboxSignKey() throws Exception {
        return gson.toJson(wxPayClient.getSignKey());
    }

    public static String createSign(String characterEncoding,SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + wxPayConfig.getKey());
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


    public static VoWxPay refund(V2Order order) {

        Map<String, String> reqMap = new LinkedHashMap();
        reqMap.put("out_trade_no",order.getOrderNo());
        reqMap.put("out_refund_no","T"+order.getOrderNo());
        reqMap.put("total_fee",order.getActualPay().toString());
        reqMap.put("refund_fee",order.getActualPay().toString());
        reqMap.put("out_refund_no",order.getOrderNo());
        reqMap.put("out_refund_no",order.getOrderNo());

        Map<String, String> respMap = null;
        try {
            respMap = wxPay.refund(reqMap);


            VoWxPay voWxPay = (VoWxPay) ObjectUtil.mapStringToObject(respMap,VoWxPay.class);

            return voWxPay;
        } catch (Exception e) {
            log.error("===>>退款失败或转换失败");
            e.printStackTrace();
        }


        return null;


    }

    }
