package com.cj.sbasic.controller;

import com.cj.core.entity.Clause;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sbasic.service.ClauseService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： 刘磊
 * @Description: 条款管理后台
 * @date： 2019/3/7 15:51
 **/
@RestController
@RequestMapping("/s-basic/api/v1/clause")
@Api(tags = "后台: 条款管理")
public class ClauseAfterController {
    @Autowired
    private ClauseService clauseService;

    /**
     * 根据id查询相应隐私条款
     * @param clauseId 条款id
     * @return
     */
    @ApiOperation(value = "管理端根据id查询相应条款",response = Clause.class)
    @GetMapping("/findClauseByIDAfter")
    @Log(name = "条款管理",value = "管理端查询相应条款")
    public ApiResult findClauseByIDAfter(Long clauseId){
        Clause clause = clauseService.getClaByID(clauseId);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(clause);
        return apiResult;
    }

    /**
     * 分页显示所有隐私条款
     */
    @ApiOperation(value = "管理端分页显示隐私条款",response = Clause.class)
    @GetMapping("/findPageClauseAfter")
    @ApiImplicitParam(name = "json" ,value ="页数 每页显示条数",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10}")
    @Log(name = "条款管理",value = "管理端分页显示隐私条款")
    public ApiResult findPageClause(String json){
        OldPager oldPager = JsonUtil.gson.fromJson(json,OldPager.class);
        OldPager oldPager2 = clauseService.getClaPage(oldPager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager2);
        return apiResult;
    }
    /**
     * 添加条款
     */
    @ApiOperation(value = "添加隐私条款",response = Clause.class)
    @PostMapping("/insertClaise")
    @ApiImplicitParam(name = "json" ,value ="subject=标题 content = 内容 ",required = true,
            defaultValue = "{\"subject\":标题,\"content\":内容}")
    @Log(name ="条款管理",value = "添加隐私条款")
    public ApiResult insertClaise(@RequestBody Clause json){
//        Clause clause = JsonUtil.gson.fromJson(json,Clause.class);
        json.setIsDisplay("1");
        int i = clauseService.insert(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 修改条款
     */
    @ApiOperation(value = "修改隐私条款",response = Clause.class)
    @PutMapping("/updateClais")
    @ApiImplicitParam(name="json",value = "clauseId 条款ID subject=标题 content = 内容",required = true,
    defaultValue ="{\"clauseId\":1,\"subject\":标题,\"content\":内容}" )
    @Log(name = "条款管理",value = "修改隐私条款")
    public ApiResult updateClais(@RequestBody Clause json){
//        Clause clause = JsonUtil.gson.fromJson(json,Clause.class);
        int i = clauseService.update(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
    /**
     * 删除条款
     */
    @ApiOperation(value = "管理端根据id删除相应条款",response = Clause.class)
    @DeleteMapping("/deleteClaisByID")
    @Log(name = "条款管理",value = "删除相应条款")
    public ApiResult deleteClaisByID(@RequestBody List<Long> list){
        int i = 0;
        for(Long clauseId:list){
            i += clauseService.delete(clauseId);
        }
        if(i==list.size()){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }
}
