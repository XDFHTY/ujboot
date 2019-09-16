package com.cj.sbasic.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Article;
import com.cj.sbasic.mapper.ArticleMapper;
import com.cj.sbasic.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author： 刘磊
 * @Description: 文章管理业务实现层
 * @date： 2019/3/6 15:25
 **/
@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 查询文章类容
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getAtl(OldPager oldPager) {
        //查询文章总条数
        oldPager.setRecordTotal(articleMapper.findArticleCount(oldPager));
        //查询文章内容
        oldPager.setContent(articleMapper.findPageArticle(oldPager));
        return oldPager;
    }

    /**
     * 根据文章id查询
     * @param articleId 文章id
     * @return
     */
    @Override
    public Article getAtlByID(Long articleId) {
        //根据id查询文章
        Article article = articleMapper.selectByPrimaryKey(articleId);
        return article;
    }

    /**
     * 修改数据
     * @param article
     * @return
     */
    @Override
    public int setAtl(Article article) {
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    /**
     * 新增数据
     * @param article
     * @return
     */
    @Override
    public int insert(Article article) {
        article.setArticleDate(new Date());
        return articleMapper.insertSelective(article);
    }

    /**
     * 删除
     * @param articleId
     * @return
     */
    @Override
    public int delete(Long articleId) {
        return articleMapper.deleteByPrimaryKey(articleId);
    }

}
