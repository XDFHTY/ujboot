package com.cj.sbasic.mapper;

import com.cj.core.entity.Article;
import com.cj.core.domain.OldPager;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/06
*/
public interface ArticleMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long articleId);

    /**
     *插入
     */
    int insert(Article record);

    /**
     *动态插入
     */
    int insertSelective(Article record);

    /**
     *通过id查找
     */
    Article selectByPrimaryKey(Long articleId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Article record);

    /**
     *更新
     */
    int updateByPrimaryKeyWithBLOBs(Article record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Article record);

    /**
     * 查询总条数
     */
    int findArticleCount(OldPager oldPager);
    /**
     * 查询文章内容
     */
    List<Article> findPageArticle(OldPager oldPager);

}