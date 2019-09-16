package com.cj.common.service;

import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 公共接口业务层
 * @date： 2019/3/12 17:59
 **/
public interface CommonService {
    //查询科室列表
    List<Department> getDepar();
    //查询科室对应疾病
    List<Disease> getDiseaseByDepartmentId(Long departmentId);
    //查询疾病
    Disease getDiseaseById(Long diseaseId);
    //查询地区医院
    List<Hospital> getHospitalByRegion(Map<String ,Long> map);

}
