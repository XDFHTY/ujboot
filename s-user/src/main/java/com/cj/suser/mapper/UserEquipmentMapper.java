package com.cj.suser.mapper;

import com.cj.core.entity.UserEquipment;
import com.cj.suser.domain.VoUserEquipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/16
*/
public interface UserEquipmentMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long userBindEquipmentId);

    /**
     *插入
     */
    int insert(UserEquipment record);

    /**
     *动态插入
     */
    int insertSelective(UserEquipment record);

    /**
     *通过id查找
     */
    UserEquipment selectByPrimaryKey(Long userBindEquipmentId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UserEquipment record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UserEquipment record);


    public List<VoUserEquipment> findAllEquipmentByUserId(long userId);

    public int updateEquipmentByUserId(@Param("userId") long userId, @Param("keyId") long keyId);

    int findEquipmentNumByIdAndType(@Param("userId") long userId, @Param("type") String type);
}