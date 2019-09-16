package com.cj.spension.service;

import com.cj.core.domain.OldPager;
import com.cj.common.domain.DoctorModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author： 刘磊
 * @Description: 医生信息管理业务层
 * @date： 2019/3/8 16:07
 **/
public interface SPDoctorService {
    //分页查询医生信息
    OldPager getDocPage(OldPager oldPager, HttpServletRequest request);
    //根据id查询医生信息
    DoctorModel getDocByID(Long userId,HttpServletRequest request);
    //删除医生
    int deleteByID(Long userId);
    //改密码
    int updateForPassByID(Long userId,String pass);
    //修改信息
    int updateForInfo(DoctorModel doctorModel);
    //导出Excel
    void exportExcel(OldPager oldPager, HttpServletResponse response, HttpServletRequest request);
}
