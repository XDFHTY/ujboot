package com.cj.skidney.controller;

import com.cj.core.entity.UrineProposal;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import com.cj.skidney.service.ProposalService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author： 刘磊
 * @Description: 建议管理
 * @date： 2019/4/17 9:50
 **/
@RestController
@RequestMapping("/s-kidney/api/v1/proposal")
@Api(tags = "后台: 建议管理")
public class ProposalController {
    @Autowired
    private ProposalService proposalService;

    /**
     * 修改建议内容
     * @param json
     * @return
     */
    @ApiOperation(value = "修改建议内容",response = UrineProposal.class)
    @PutMapping("/updataProposal")
    @Log(name = "建议管理",value = "修改建议内容")
    public ApiResult updataProposal(@ApiParam(name = "json",value ="{\n" +
            "      \"urineProposalId\": 1,\n" +
            "      \"urineProposalName\": null,\n" +
            "      \"urineProposalContent\": null\n" +
            "    }",required = true)
                                        @RequestBody UrineProposal json){
//        UrineProposal urineProposal = JsonUtil.gson.fromJson(json,UrineProposal.class);
        int i = proposalService.updataProposal(json);
        if(i==1){
            return ApiResult.SUCCESS();
        }
        return ApiResult.FAIL();
    }

    /**
     * 查询建议列表
     * @return
     */
    @ApiOperation(value = "查询建议列表",response = UrineProposal.class)
    @GetMapping("/findProposal")
    @Log(name = "建议管理",value = "查询建议列表")
    public ApiResult findProposal(){
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(proposalService.findProposal());
        return apiResult;
    }
}
