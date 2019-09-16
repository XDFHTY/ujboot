package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Article;

/**
 * @author： 刘磊
 * @Description: 文章管理业务成
 * @date： 2019/3/6 15:25
 **/
public interface ArticleService {
    //分页查询文章类容
    OldPager getAtl(OldPager oldPager);
    //根据id查询文章
    Article getAtlByID(Long articleId);
    //修改
    int setAtl(Article article);
    //添加
    int insert(Article article);
    //删除文章
    int delete(Long articleId);
}
