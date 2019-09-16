package com.cj.suser.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2019/3/19.
 */
public interface PharmacyPushMapper {
    //查询数据库 当前时间至后十分钟的 数据 推送
    List<Map> findDrugwrnByTime(@Param("nowDate")String nowDate, @Param("addDate")String nowDateAdd10);

    //查询数据库 修改操作时间是 3天前的 且状态是 绑定中的 记录   修改状态 为 被拒绝
    int updateStateByDay(String nowDateAdd3);
}
