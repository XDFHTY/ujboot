package com.cj.sim.service.impl;

import cn.jpush.api.push.model.PushPayload;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.sim.service.PushService;
import com.cj.sim.utils.jiguang.MsgPushUtils;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 推送管理
 * Created by XD on 2019/3/8.
 */
@Service
public class PushServiceImpl implements PushService {

    /**
     * 极光推送  true代表生产环境
     */
    @Value("${apnsProduction}")
    private String apnsProduction;

    /**
     * 转换为 true false
     * @return
     */
    private boolean getApns(){
        return Boolean.parseBoolean(apnsProduction);
    }

    private static final Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);


    /**
     * 给全部设备 全部用户 发送 通知
     * @param alert
     * @return
     */
    @Override
    public boolean buildPushObjectAllAllAlert(String alert,String appType) {
        //构建推送
        PushPayload payload = MsgPushUtils.buildPushObjectAllAllAlert(alert);
        //发送
        return MsgPushUtils.sendPushTryCatch(payload,logger,appType);
    }

    /**
     * 给全部设备 全部用户 发送通知和消息
     * @param days
     * @param content
     * @param alert
     * @return
     */
    @Override
    public boolean sendAndroidAndIosMessageAndNotification(String days, String content, String alert,String appType) {
        PushPayload payload = MsgPushUtils.sendAndroidAndIosMessageAndNotification(days,content,alert,getApns());
        return MsgPushUtils.sendPushTryCatch(payload,logger,appType);
    }


    /**
     * 给全部设备按别名发送通知和参数
     *
     * @param days
     * @param params
     * @param alert
     * @param alias
     * @return
     */
    @Override
    public boolean buildPushObjectAllAliasAlert(String days, Map<String, String> params, String alert, List<String> alias,String appType) {
        //极光每次只能支持 1000个别名推送，所以要判断
        if (alias == null || alias.size() == 0){//当前没有用户需要推送
            return true;
        }
        if (params == null){
            params = new HashMap<>();
        }
        String s = ImportExeclUtil.DateUtil.dateToStr(new Date(), ImportExeclUtil.DateUtil.YYYY_MM_DDHHMMSS);
        params.put("nowDate",s);//推送时间
        if (alias.size()<=1000){
            //直接推送
            PushPayload payload = MsgPushUtils.buildPushObjectAllAliasAlert(days,params,alert,getApns(),alias);
            return MsgPushUtils.sendPushTryCatch(payload,logger,appType);
        }else {
            //大于1000 需要 分次发送  拆分集合
            List<List<String>> partition = ListUtils.partition(alias, 1000);
            for (List<String> list:partition){
                PushPayload payload = MsgPushUtils.buildPushObjectAllAliasAlert(days,params,alert,getApns(),list);
                MsgPushUtils.sendPushTryCatch(payload,logger,appType);
            }
            return true;
        }
    }

    /**
     * 给全部设备按标签发送通知和参数
     * @param days
     * @param params
     * @param alert
     * @param tag
     * @param type type= 1-标签之间取并集  2-标签之间取交集
     * @return
     */
    @Override
    public boolean buildPushObjectAllOrAndTagAlert(String days, Map<String, String> params, String alert, List<String> tag, int type,String appType) {
        if (params == null){
            params = new HashMap<>();
        }
        String s = ImportExeclUtil.DateUtil.dateToStr(new Date(), ImportExeclUtil.DateUtil.YYYY_MM_DDHHMMSS);
        params.put("nowDate",s);//推送时间
        PushPayload payload;
        if (type == 1){//1-标签之间取并集
            payload = MsgPushUtils.buildPushObjectAllTagAlert(days,params,alert,getApns(),tag);
        }else {//2-标签之间取交集
            payload = MsgPushUtils.buildPushObjectAndTagAlert(days,params,alert,getApns(),tag);
        }
        return MsgPushUtils.sendPushTryCatch(payload,logger,appType);
    }

}
