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

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 文章管理后台控制器
 * @date： 2019/3/7 10:12
 **/
@RestController
@RequestMapping("/s-basic/api/v1/article")
@Api(tags = "后台: 文章管理")
public class ArticleAfterController {
    @Autowired
    private ArticleService articleService;
    /**
     * 管理端查询文章列表
     */
    @ApiOperation(value = "管理端查询文章列表",response = Article.class)
    @GetMapping("/findPageArticleAfter")
    @ApiImplicitParam(name = "json" ,value =" articleSign = 文章类型 1肾病文章，2健康小知识 ",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"articleSign\":\"1\"}}")
    @Log(name = "文章管理",value = "管理端查询文章列表")
    public ApiResult findPageArticleAfter(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = articleService.getAtl(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }

    /**
     * 根据文章id查询
     * 参数： 文章id
     */
    @ApiOperation(value = "查询具体文章",response = Article.class)
    @GetMapping("/findForArticleByIDAfter")
    @ApiImplicitParam(name = "articleId" , value = "文章id",required = true)
    @Log(name ="文章管理",value = "查询具体文章")
    public ApiResult findForArticleByIDAfter(Long articleId){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(articleService.getAtlByID(articleId));
        return apiResult;
    }

    /**
     * 添加文章
     * 参数：文章表实体类
     */
    @ApiOperation(value = "添加文章",response = Article.class)
    @PostMapping("/insertArticle")
    @ApiImplicitParam(name = "json" ,value ="coverUrl=封面 articleContent = 文章内容 " +
            "articleDate = 发布时间 articleTitle = 文章标题 articleSign = 文章类型 1肾病文章，2健康小知识 ",required = true,
            defaultValue = "{\"coverUrl\":封面,\"articleTitle\":文章标题," +
                    "\"articleContent\":文章内容," +
                    "\"articleDate\":\"2000-02-02\",\"articleSign\":1}")
    @Log(name ="文章管理",value = "添加文章")
    public ApiResult insertArticle(@RequestBody Article json){
//        Article article = JsonUtil.gson.fromJson(json,Article.class);
        json.setArticleViews(0);
        json.setArticleState("1");
        json.setIsDisplay("1");
        int i = articleService.insert(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 修改文章
     */
    @ApiOperation(value = "修改文章",response = Article.class)
    @ApiImplicitParam(name = "json" ,value ="articleId = 文章id articleContent = 文章内容 articleDate = 发布时间 articleTitle = 文章标题 articleSign = 文章类型 1肾病文章，2健康小知识 ",required = true,
            defaultValue = "{\"articleId\":1,\"articleTitle\":文章标题,\"articleContent\":文章内容,\"articleDate\":2000-02-02,\"articleSign\":1}")
    @PutMapping("/update")
    @Log(name ="文章管理",value = "修改文章")
    public ApiResult update(@RequestBody Article json){
//        Article article = JsonUtil.gson.fromJson(json,Article.class);
        json.setArticleViews(0);
        json.setArticleState("1");
        json.setIsDisplay("1");
        int i = articleService.setAtl(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 删除文章
     * 参数：文章id
     */
    @ApiOperation(value = "删除文章")
    @DeleteMapping("/deleteArticleByID")
    @Log(name ="文章管理",value = "删除文章")
    public ApiResult deleteArticleByID(@RequestBody List<Long> list){
        int i = 0;
        for(Long articleId:list){
            i += articleService.delete(articleId);
        }
        if(i==list.size()){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
}
