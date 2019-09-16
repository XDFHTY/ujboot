package com.cj.common.service.impl;

import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;
import com.cj.common.mapper.DepartmentMapper;
import com.cj.common.mapper.DiseaseMapper;
import com.cj.common.mapper.HospitalMapper;
import com.cj.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 公共接口业务实现层
 * @date： 2019/3/12 18:00
 **/
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private HospitalMapper hospitalMapper;

    /**
     * 查询科室列表
     * @return
     */
    @Override
    public List<Department> getDepar() {
        return departmentMapper.findDepartment();
    }

    /**
     * 查询病史列表 根据科室
     * @param departmentId
     * @return
     */
    @Override
    public List<Disease> getDiseaseByDepartmentId(Long departmentId) {
        return diseaseMapper.findDiseaseByDepartmentId(departmentId);
    }

    /**
     * 根据id查疾病
     * @param diseaseId
     * @return
     */
    @Override
    public Disease getDiseaseById(Long diseaseId) {
        return diseaseMapper.selectByPrimaryKey(diseaseId);
    }

    /**
     * 根据地区查询医院
     * @param map
     * @return
     */
    @Override
    public List<Hospital> getHospitalByRegion(Map<String, Long> map) {
        return hospitalMapper.findHospitalByRegion(map);
    }

}
