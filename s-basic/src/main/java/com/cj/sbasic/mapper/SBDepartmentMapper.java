package com.cj.sbasic.mapper;

import com.cj.core.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/08
*/
@Repository("sbasicDepartmentMapper")
public interface SBDepartmentMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long departmentId);

    /**
     *插入
     */
    int insert(Department record);

    /**
     *动态插入
     */
    int insertSelective(Department record);

    /**
     *通过id查找
     */
    Department selectByPrimaryKey(Long departmentId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Department record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Department record);
    /**
     * 删除否使用科室
     * @param departmentId
     * @return
     */
    int deleteDepartment(Long departmentId);
}