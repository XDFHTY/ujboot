package com.cj.skidney.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.BloodCreatinine;
import com.cj.core.entity.BloodPressure;
import com.cj.core.entity.UrineProtein;
import com.cj.skidney.domain.UrineModel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 检查记录业务实现层
 * @date： 2019/3/13 10:27
 **/
public interface UrineService {
    //新增尿检记录
    List<Long> insertUrine(List<UrineModel> list);
    //插入24小时尿蛋白记录
    int insertUrineProtein(UrineProtein urineProtein);
    //插入血压记录
    int insertBloodPressure(BloodPressure bloodPressure);
    //插入血肌酐记录
    int insertBloodCreatinine(BloodCreatinine bloodCreatinine);
    //查询检查记录列表
    OldPager getInsM(OldPager oldPager);
    //查询检查结果记录列表
    OldPager getResult(OldPager oldPager);
    //根据id查询检查结果
    OldPager getResultById(OldPager oldPager);
    //导出检查结果
    void export(OldPager oldPager, HttpServletResponse response);
    //导出检查记录列表
    void exportIspect(OldPager oldPager, HttpServletResponse response);
}
