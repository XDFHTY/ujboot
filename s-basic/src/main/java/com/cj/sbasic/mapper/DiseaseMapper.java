package com.cj.sbasic.mapper;

import com.cj.core.entity.Disease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/08
*/
@Repository("sbasicDiseaseMapper")
public interface DiseaseMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long diseaseId);

    /**
     *插入
     */
    int insert(Disease record);

    /**
     *动态插入
     */
    int insertSelective(Disease record);

    /**
     *通过id查找
     */
    Disease selectByPrimaryKey(Long diseaseId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Disease record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Disease record);

    /**
     * 查询是否使用 未使用删除
     * @param diseaseId
     * @return
     */
    int deleteDisease(Long diseaseId);
}