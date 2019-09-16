package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;

/**
 * @author： 刘磊
 * @Description: 医院、科室、疾病业务层
 * @date： 2019/3/20 15:15
 **/
public interface CommService {
    //查询地区医院
    OldPager getHospitalByRegion(OldPager oldPager);
    //新增科室
    int insertDepar(Department department);
    //新增疾病
    int insretDisease(Disease disease);
    //新增医院
    int insertHospital(Hospital hospital);
    //修改科室
    int updateDepar(Department department);
    //修改疾病
    int updateDisease(Disease disease);
    //修改医院
    int updateHospital(Hospital hospital);
    //删除科室
    int deleteDepar(Long departmentId);
    //删除疾病
    int deleteDisease(Long diseaseId);
    //删除医院
    int deleteHospital(Long hospitalId);
}
