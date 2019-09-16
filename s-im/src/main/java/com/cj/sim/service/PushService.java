package com.cj.sim.service;

import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2019/3/8.
 */
public interface PushService {

    //给全部设备 全部用户 发送 通知
    boolean buildPushObjectAllAllAlert(String alert,String appType);

    //给全部设备 全部用户 发送通知和消息
    boolean sendAndroidAndIosMessageAndNotification(String days, String content, String alert,String appType);

    //给全部设备按别名发送通知和参数
    boolean buildPushObjectAllAliasAlert(String days, Map<String, String> params, String alert, List<String> alias,String appType);

    //给全部设备按标签发送通知和参数
    boolean buildPushObjectAllOrAndTagAlert(String days, Map<String, String> params, String alert, List<String> tag, int type,String appType);
}
