package com.cj.suser.service;

import java.util.Date;

/**
 * 用药提醒
 * Created by XD on 2019/3/19.
 */
public interface PharmacyPush {

    //查询数据库 当前时间至后十分钟的 数据 推送
    int findData(String nowDate,String nowDateAdd10);
}
