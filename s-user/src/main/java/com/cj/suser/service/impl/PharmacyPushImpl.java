package com.cj.suser.service.impl;

import com.cj.suser.mapper.PharmacyPushMapper;
import com.cj.suser.service.PharmacyPush;
import com.cj.suser.service.UserCallImService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.*;

/**
 * 用药提醒
 * Created by XD on 2019/3/19.
 */
@Service
public class PharmacyPushImpl implements PharmacyPush {

    @Resource
    private PharmacyPushMapper pharmacyPushMapper;

    @Autowired
    private UserCallImService userCallImService;


    /**
     * 查询数据库 当前时间至后十分钟的 数据 推送
     */
    @Override
    public int findData(String nowDate,String nowDateAdd10) {

        List<String> userIdList = new ArrayList();


        //查询数据库 当前时间至后十分钟的 数据 推送
        List<Map> mapList = this.pharmacyPushMapper.findDrugwrnByTime(nowDate,nowDateAdd10);
        if(mapList != null && mapList.size() != 0){
            for (Map map:mapList){
                Long s = (Long) map.get("userId");
                userIdList.add(String.valueOf(s));
            }
            Map map = mapList.get(0);
            Time time = (Time) map.get("drugTime");



            //调用推送接口 使用别名推送
            if(userIdList != null && userIdList.size() != 0){
                //转换为 set 去重
                Set set = new HashSet();
                set.addAll(userIdList);
                List userIdList2 = new ArrayList();
                userIdList2.addAll(set);

                Map<String,String> map2 = new HashMap<>();//自定义参数
                map2.put("type","1");//消息类型
                map2.put("date",time.toString());

                Map map1 = new HashMap();
                map1.put("appType","1");//推给用户
                map1.put("days","1");//通知离线保留1天
                map1.put("alias",userIdList2);//推送对象 别名
                map1.put("alert","友情提示，用药时间到了！");
                map1.put("params",map2);
                userCallImService.buildPushObjectAllAliasAlert(map1);
            }
        }



        return userIdList.size();

    }
}
