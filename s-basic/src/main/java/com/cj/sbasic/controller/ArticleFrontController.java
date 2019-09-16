package com.cj.sbasic.controller;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.Article;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.ArticleService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author： 刘磊
 * @Description: 文章管理前台控制器
 * @date： 2019/3/6 15:27
 **/
@RestController
@RequestMapping("/s-basic/api/v1/article")
@Api(tags = "用户端: 文章管理")
public class ArticleFrontController {

    @Autowired
    private ArticleService articleService;


    /**
     * 用户端查询文章列表
     */
    @ApiOperation(value = "用户端查询文章列表",response = Article.class)
    @GetMapping("/findPageArticleFront")
    @ApiImplicitParam(name = "json" ,value ="articleSign = 文章类型 1肾病文章，2健康小知识 ",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"articleSign\":\"1\"}}")
    @Log(name = "文章管理",value = "用户端查询文章列表")
    public ApiResult findPageArticleFront(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager1 = articleService.getAtl(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager1);
        return apiResult;
    }

    /**
     * 根据文章id查询 阅读量加一
     * 参数： 文章id
     */
    @ApiOperation(value = "查询具体文章",response = Article.class)
    @GetMapping("/findForArticleByIDFront")
    @ApiImplicitParam(name = "articleId" , value = "文章id")
    @Log(name ="文章管理",value = "查询具体文章")
    public ApiResult findForArticleByID(Long articleId){
        Article article = articleService.getAtlByID(articleId);
        //阅读量加一
        if(article!=null){
            article.setArticleViews(article.getArticleViews()+1);
            //修改数据
            articleService.setAtl(article);
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setData(article);
            return apiResult;
        }else{
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setData("没有文章");
            return apiResult;
        }
    }

}
