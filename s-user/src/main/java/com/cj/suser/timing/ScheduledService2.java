package com.cj.suser.timing;

import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.core.service.DBService;
import com.cj.suser.service.BindDataService;
import com.cj.suser.service.PharmacyPush;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
@EnableScheduling
public class ScheduledService2 {


    @Autowired
    private PharmacyPush pharmacyPush;

    @Autowired
    private BindDataService bindDataService;

    /**
     * 每个 9 19 29 39 49 59 分钟  查询数据库 后10分钟的数据
     */
    @Scheduled(cron = "0 9,19,29,39,49,59 * * * *")
    @Async("getAsyncExecutor")
    public void scheduled(){
        log.info("=====>>>>>推送用药提醒");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String nowDate = sdf.format(now);
        System.out.println("当前时间：" + nowDate);
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 10);
        String nowDateAdd10 = sdf.format(nowTime.getTime());
        System.out.println("延时后时间：" + nowDateAdd10);

        int i = pharmacyPush.findData(nowDate, nowDateAdd10);
        System.out.println("推送人数：" + i);
    }

    /**
     * 每天凌晨2点  清理 三天前加好友记录 未处理的记录
     */
    @Scheduled(cron = "0 0 2 * * *")
    //@Scheduled(cron = "0 */1 * * * *")
    @Async("getAsyncExecutor")
    public void scheduled2(){
        log.info("=====>>>>>清理 加好友 未处理的记录");

        SimpleDateFormat sdf = new SimpleDateFormat(ImportExeclUtil.DateUtil.YYYY_MM_DDHHMMSS);
        Date now = new Date();
        String nowDate = sdf.format(now);
        System.out.println("当前时间：" + nowDate);
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, -3);
        String nowDateAdd3 = sdf.format(nowTime.getTime());
        System.out.println("三天前的时间：" + nowDateAdd3);

        int i = bindDataService.updateBindData(nowDateAdd3);
        System.out.println("处理记录数：" + i);
    }
}
