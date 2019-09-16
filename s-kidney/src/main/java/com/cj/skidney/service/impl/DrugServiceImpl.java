package com.cj.skidney.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.DrugwarnTime;
import com.cj.skidney.domain.DrugModel;
import com.cj.skidney.mapper.DrugwarnMapper;
import com.cj.skidney.mapper.DrugwarnTimeMapper;
import com.cj.skidney.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用药管理业务实现层
 * @date： 2019/3/19 11:52
 **/
@Service
@Transactional
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugwarnMapper drugwarnMapper;
    @Autowired
    private DrugwarnTimeMapper drugwarnTimeMapper;
    /**
     * 新增用药计划
     * @param drugModel
     * @return
     */
    @Override
    public Long insertDrug(DrugModel drugModel) {
        //设置录入时间
        drugModel.getDrugwarn().setCreateTime(new Date());
        //插入用药提醒
        int i = drugwarnMapper.insertSelective(drugModel.getDrugwarn());
        //查询新录入用药提醒id
        Long drugwarnId = drugModel.getDrugwarn().getDrugwarnId();
        drugModel.getDtList().forEach(drugwarnTime -> {
            //设置用药提醒id
            drugwarnTime.setDrugwarnId(drugwarnId);
            if(drugwarnTime.getIsClose()==null){
                drugwarnTime.setIsClose("1");
            }
            //插入用药提醒时间
            drugwarnTimeMapper.insertSelective(drugwarnTime);
        });
        return drugwarnId;
    }

    /**
     * 删除用药计划
     * @param drugwarnId
     * @return
     */
    @Override
    public int delete(Long drugwarnId) {
        int i=0;
        i += drugwarnMapper.deleteByPrimaryKey(drugwarnId);
        i += drugwarnTimeMapper.deleteBydrugwarnId(drugwarnId);
        return i;
    }

    /**
     * 修改用药提醒状态
     * @param drugwarnTime
     * @return
     */
    @Override
    public int update(DrugwarnTime drugwarnTime) {
        return drugwarnTimeMapper.updateByPrimaryKeySelective(drugwarnTime);
    }

    /**
     * 查询用药计划
     * @param oldPager
     * @return
     */
    @Override
    public List<DrugModel> getDrug(OldPager oldPager) {
        return drugwarnMapper.findDrugModel(oldPager);
    }

    @Override
    public DrugModel getDrugById(Long drugwarnId) {
        return drugwarnMapper.findDrugById(drugwarnId);
    }
}
