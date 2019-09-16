package com.cj.server;

import com.cj.core.util.JsonUtil;
import com.cj.sshop.domain.VoWxPay;
import com.google.gson.Gson;

import java.util.Map;

public class test {

    static Gson gson = JsonUtil.gson;

    public static void main(String[] args) {
        String s = "{\"nonceStr\":\"cTzXWhMFzLaahYCL\",\"appid\":\"wx61df4741640c7e1c\",\"sign\":\"FD83D2071F9D8031D53C57DB413B4FFA\",\"tradeType\":\"APP\",\"returnMsg\":\"OK\",\"resultCode\":\"SUCCESS\",\"mchId\":\"1543896251\",\"returnCode\":\"SUCCESS\",\"prepayId\":\"wx10172946670861a70f08d4101660530500\"}";

        Map map = gson.fromJson(s,Map.class);

        System.out.println(map);
    }
}
