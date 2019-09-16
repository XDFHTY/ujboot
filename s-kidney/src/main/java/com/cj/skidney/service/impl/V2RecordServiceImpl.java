package com.cj.skidney.service.impl;

import com.cj.common.mapper.PublicMapper;
import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2BloodOxygen;
import com.cj.core.v2entity.V2HeartRate;
import com.cj.core.v2entity.V2Location;
import com.cj.skidney.mapper.*;
import com.cj.skidney.service.V2RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 2期检测记录业务实现层
 * @date： 2019/6/29 16:19
 **/
@Service
@Transactional
public class V2RecordServiceImpl implements V2RecordService {

    @Autowired
    private SKV2BOMapper skv2BOMapper;
    @Autowired
    private SKV2HRMapper skv2HRMapper;
    @Autowired
    private SKV2LMapper skv2LMapper;
    @Autowired
    private SKV2UPMapper skv2UPMapper;
    @Autowired
    private SKV2BPMapper skv2BPMapper;
    @Autowired
    private SKV2BCMapper skv2BCMapper;
    @Autowired
    private SKV2URMapper skv2URMapper;
    @Autowired
    private PublicMapper publicMapper;


    /**
     * 新增心率
     * @param list
     * @return
     */
    @Override
    public int insertHeartRate(List<V2HeartRate> list) {
        for(V2HeartRate v2HeartRate:list){
            //修改设备最后使用时间
            if(v2HeartRate.getBluetoothMac()!=null){
                publicMapper.putUseTime(v2HeartRate.getBluetoothMac(),
                        v2HeartRate.getCreateTime());
            }
            int i = skv2HRMapper.insert(v2HeartRate);
            if(i==0){
                return 0;
            }
        }
        return 1;
    }

    /**
     * 新增血氧
     * @param list
     * @return
     */
    @Override
    public int insertBloodOxygen(List<V2BloodOxygen> list) {
        for(V2BloodOxygen v2BloodOxygen:list){
            //修改设备最后使用时间
            if(v2BloodOxygen.getBluetoothMac()!=null){
                publicMapper.putUseTime(v2BloodOxygen.getBluetoothMac(),
                        v2BloodOxygen.getCreateTime());
            }
            int i = skv2BOMapper.insert(v2BloodOxygen);
            if(i==0){
                return 0;
            }
        }
        return 1;
    }

    /**
     * 新增定位
     * @param list
     * @return
     */
    @Override
    public int insertLocation(List<V2Location> list) {
        for(V2Location v2Location:list){
            int i = skv2LMapper.insert(v2Location);
            if(i==0){
                return 0;
            }
        }
        return 1;
    }

    /**
     * 分页查询检查结果列表
     * @param pager
     * @return
     */
    @Override
    public Pager findRecordPage(Pager pager) {
        String type = ((Map)pager.getParameters()).get("type").toString();
        if(type.equals("0")){
            //心率
            return skv2HRMapper.findByUserIdPage(pager);
        }else if(type.equals("1")){
            //血氧
            return skv2BOMapper.findByUserIdPage(pager);
        }else if(type.equals("2")){
            //定位
            return skv2LMapper.findByUserIdPage(pager);
        }else if(type.equals("3")){
            //尿检
            return skv2URMapper.findByUserIdPage(pager);
        }else if(type.equals("4")){
            //血压
            return skv2BPMapper.findByUserIdPage(pager);
        }else if(type.equals("5")){
            //血肌酐
            return skv2BCMapper.findByUserIdPage(pager);
        }else if(type.equals("6")){
            //24小时尿蛋白
            return skv2UPMapper.findByUserIdPage(pager);
        }
        return null;
    }

    /**
     * 查询检查结果列表
     * @param map
     * @return
     */
    @Override
    public List findRecord(Map map) {
        String type = map.get("type").toString();
        if(type.equals("0")){
            //心率
            return skv2HRMapper.findByUserId(map);
        }else if(type.equals("1")){
            //血氧
            return skv2BOMapper.findByUserId(map);
        }else if(type.equals("2")){
            //定位
            return skv2LMapper.findByUserId(map);
        }
        return null;
    }
}
