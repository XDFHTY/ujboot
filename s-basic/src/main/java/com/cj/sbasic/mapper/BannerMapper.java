package com.cj.sbasic.mapper;

import com.cj.core.entity.Banner;
import com.cj.core.domain.OldPager;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface BannerMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long bannerId);

    /**
     *插入
     */
    int insert(Banner record);

    /**
     *动态插入
     */
    int insertSelective(Banner record);

    /**
     *通过id查找
     */
    Banner selectByPrimaryKey(Long bannerId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Banner record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Banner record);

    /**
     * 查询条数
     * @param oldPager
     * @return
     */
    int findBannerCountByType(OldPager oldPager);

    /**
     * 查询数据
     * @param oldPager
     * @return
     */
    List<Banner> findBannerByType(OldPager oldPager);
}