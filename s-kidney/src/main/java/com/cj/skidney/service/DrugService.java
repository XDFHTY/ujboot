package com.cj.skidney.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.DrugwarnTime;
import com.cj.skidney.domain.DrugModel;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用药管理业务层
 * @date： 2019/3/19 11:52
 **/
public interface DrugService {
    //新增用药计划
    Long insertDrug(DrugModel drugModel);
    //删除用药计划
    int delete(Long drugwarnId);
    //修改用药提醒时间状态
    int update(DrugwarnTime drugwarnTime);
    //查询用药计划
    List<DrugModel> getDrug(OldPager oldPager);
    //查询用药计划
    DrugModel getDrugById(Long drugwarnId);
}
