package com.cj.skidney.service;

import com.cj.core.domain.OldPager;

import javax.servlet.http.HttpServletResponse;

/**
 * @author： 刘磊
 * @Description: 设备管理业务层
 * @date： 2019/3/14 18:34
 **/
public interface SKEquipmentService {
    //查询设备信息列表
    OldPager getEquipmentModelPage(OldPager oldPager);
    //查询设备历史绑定记录
    OldPager getHistoricalPage(OldPager oldPager);
    //导出设备信息列表
    void exportEquipmentModel(OldPager oldPager, HttpServletResponse response);
    //导出设备历史绑定记录
    void exportHistorical(OldPager oldPager, HttpServletResponse response);
}
