package com.cj.skidney.service;

import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2BloodOxygen;
import com.cj.core.v2entity.V2HeartRate;
import com.cj.core.v2entity.V2Location;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 2期检测记录业务层
 * @date： 2019/6/29 16:18
 **/
public interface V2RecordService {
    /**
     * 新增心率
     * @param list
     * @return
     */
    int insertHeartRate(List<V2HeartRate> list);

    /**
     * 新增血氧
     * @param list
     * @return
     */
    int insertBloodOxygen(List<V2BloodOxygen> list);

    /**
     * 新增定位
     * @param list
     * @return
     */
    int insertLocation(List<V2Location> list);

    /**
     * 分页查询检查结果列表
     * @param pager
     * @return
     */
    Pager findRecordPage(Pager pager);

    /**
     * 查询检查结果列表
     * @param map
     * @return
     */
    List findRecord(Map map);
}
