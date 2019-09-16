package com.cj.skidney.mapper;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.UserInfo;
import com.cj.skidney.domain.EquipmentModel;
import com.cj.skidney.domain.InspectModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* Created by Mybatis Generator 2019/03/08
*/
@Repository("skidneyUserInfoMapper")
public interface SKUserInfoMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long userInfoId);

    /**
     *插入
     */
    int insert(UserInfo record);

    /**
     *动态插入
     */
    int insertSelective(UserInfo record);

    /**
     *通过id查找
     */
    UserInfo selectByPrimaryKey(Long userInfoId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(UserInfo record);
    /**
     * 查询检查记录总条数
     */
    int findUserCount(OldPager oldPager);
    /**
     * 分页检查记录查询记录
     */
    List<InspectModel> findUserPage(OldPager oldPager);
    /**
     * 检查记录查询记录
     */
    List<InspectModel> findUser(OldPager oldPager);
    /**
     * 根据疾病id查询病名
     */
    String findDiseaseName(Long diseaseId);
    /**
     * 查询设备信息条数
     * @param oldPager
     * @return
     */
    int findEquipmentModelCount(OldPager oldPager);

    /**
     * 分页查询设备信息记录
     * @param oldPager
     * @return
     */
    List<EquipmentModel> findEquipmentModelPage(OldPager oldPager);
    /**
     * 查询设备信息记录
     * @param oldPager
     * @return
     */
    List<EquipmentModel> findEquipmentModel(OldPager oldPager);
    /**
     *通过userId查找
     */
    Map<String,Object> findByUserId(Long userId);

}