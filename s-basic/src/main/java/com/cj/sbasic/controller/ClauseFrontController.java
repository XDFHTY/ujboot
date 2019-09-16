package com.cj.sbasic.controller;

import com.cj.core.entity.Clause;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sbasic.service.ClauseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 刘磊
 * @Description: 条款管理前台
 * @date： 2019/3/7 15:52
 **/
@RestController
@RequestMapping("/s-basic/api/v1/clause")
@Api(tags = "用户端: 条款管理")
public class ClauseFrontController {

    @Autowired
    private ClauseService clauseService;

    /**
     * 根据条款名字查询相应隐私条款
     * @param subject 条款id
     * @return
     */
    @ApiOperation(value = "用户端根据条款名字查询相应条款",response = Clause.class)
    @GetMapping("/findClauseBySubjectFront")
    @Log(name = "条款管理",value = "用户端查询相应条款")
    public ApiResult findClauseBySubjectFront(String subject){
        Clause clause = clauseService.getClaBySubject(subject);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(clause);
        return apiResult;
    }
}
