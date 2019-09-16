package com.cj.sbasic.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Department;
import com.cj.core.entity.Disease;
import com.cj.core.entity.Hospital;
import com.cj.core.exception.MyException;
import com.cj.sbasic.mapper.DiseaseMapper;
import com.cj.sbasic.mapper.HospitalMapper;
import com.cj.sbasic.mapper.SBDepartmentMapper;
import com.cj.sbasic.service.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author： 刘磊
 * @Description: 医院、科室、疾病业务实现层
 * @date： 2019/3/20 15:13
 **/
@Service
public class CommServiceImpl implements CommService{

    @Autowired
    private SBDepartmentMapper departmentMapper;
    @Autowired
    private DiseaseMapper diseaseMapper;
    @Autowired
    private HospitalMapper hospitalMapper;

    /**
     * 分页查询医院
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getHospitalByRegion(OldPager oldPager) {
        //查询条数
        oldPager.setRecordTotal(hospitalMapper.findHospitalByRegionCount(oldPager));
        //查询类容
        oldPager.setContent(hospitalMapper.findHospitalByRegion(oldPager));
        return oldPager;
    }

    /**
     * 添加科室
     * @param department
     * @return
     */
    @Override
    public int insertDepar(Department department) {
        department.setIsDisplay("1");
        return departmentMapper.insertSelective(department);
    }

    /**
     * 添加疾病
     * @param disease
     * @return
     */
    @Override
    public int insretDisease(Disease disease) {
        disease.setIsDisplay("1");
        return diseaseMapper.insertSelective(disease);
    }

    /**
     * 添加医院
     * @param hospital
     * @return
     */
    @Override
    public int insertHospital(Hospital hospital) {
        return hospitalMapper.insertSelective(hospital);
    }

    /**
     * 修改科室
     * @param department
     * @return
     */
    @Override
    public int updateDepar(Department department) {
        return departmentMapper.updateByPrimaryKeySelective(department);
    }

    /**
     * 修改疾病
     * @param disease
     * @return
     */
    @Override
    public int updateDisease(Disease disease) {
        return diseaseMapper.updateByPrimaryKeySelective(disease);
    }

    /**
     * 修改医院
     * @param hospital
     * @return
     */
    @Override
    public int updateHospital(Hospital hospital) {
        return hospitalMapper.updateByPrimaryKeySelective(hospital);
    }

    /**
     * 删除科室
     * @param departmentId
     * @return
     */
    @Override
    public int deleteDepar(Long departmentId) {
        if (departmentId == 3){
            throw new MyException("不允许删除此项");
        }
        return departmentMapper.deleteDepartment(departmentId);
    }

    /**
     * 删除疾病
     * @param diseaseId
     * @return
     */
    @Override
    public int deleteDisease(Long diseaseId) {
        return diseaseMapper.deleteDisease(diseaseId);
    }

    /**
     * 删除医院
     * @param hospitalId
     * @return
     */
    @Override
    public int deleteHospital(Long hospitalId) {
        return hospitalMapper.deleteHospital(hospitalId);
    }
}
