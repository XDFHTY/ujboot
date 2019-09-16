package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Banner;

/**
 * @author： 刘磊
 * @Description: 轮播图 业务层
 * @date： 2019/3/8 10:34
 **/
public interface BannerService {
    //分页查询列表
    OldPager getBarPage(OldPager oldPager);
    //按ID查询
    Banner getBarByID(Long bannerId);
    //新增
    int insert(Banner banner);
    //删除
    int delete(Long bannerId);
    //修改
    int update(Banner banner);
}
