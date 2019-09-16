package com.cj.suser.mapper;

import com.cj.core.entity.FallIllImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/15
*/
public interface FallIllImgMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long fallIllImgId);

    /**
     *插入
     */
    int insert(FallIllImg record);

    /**
     *动态插入
     */
    int insertSelective(FallIllImg record);

    /**
     *通过id查找
     */
    FallIllImg selectByPrimaryKey(Long fallIllImgId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(FallIllImg record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(FallIllImg record);

    /**
     * 根据疾病id删除其所有图片
     * @return
     */
    public int deleteFallIllImgs(long fallIllId);

    /**
     * 批量新增图片
     * @param fallIllImgs
     */
    public int addFallIllImgs(List<FallIllImg> fallIllImgs);
}