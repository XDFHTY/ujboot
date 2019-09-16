package com.cj.sbasic.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Banner;
import com.cj.sbasic.mapper.BannerMapper;
import com.cj.sbasic.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author： 刘磊
 * @Description: 轮播图 业务层实现
 * @date： 2019/3/8 10:34
 **/
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 分页查询 类型
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getBarPage(OldPager oldPager) {
        //查询总条数
        oldPager.setRecordTotal(bannerMapper.findBannerCountByType(oldPager));
        //查询内容
        oldPager.setContent(bannerMapper.findBannerByType(oldPager));
        return oldPager;
    }

    /**
     * 根据ID查询
     * @param bannerId
     * @return
     */
    @Override
    public Banner getBarByID(Long bannerId) {
        return bannerMapper.selectByPrimaryKey(bannerId);
    }

    /**
     * 插入
     * @param banner
     * @return
     */
    @Override
    public int insert(Banner banner) {
        banner.setCreateTime(new Date());
        return bannerMapper.insertSelective(banner);
    }

    /**
     * 删除
     * @param bannerId
     * @return
     */
    @Override
    public int delete(Long bannerId) {

        return bannerMapper.deleteByPrimaryKey(bannerId);
    }

    /**
     * 修改
     * @param banner
     * @return
     */
    @Override
    public int update(Banner banner) {
        return bannerMapper.updateByPrimaryKeySelective(banner);
    }
}
