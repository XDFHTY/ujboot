package com.cj.sconsult.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sconsult.entity.VoEvaluate;
import com.cj.sconsult.service.InterrogationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@RestController
@RequestMapping("/s-consult/api/v1/interrogation")
@Api(tags = "用户端&医生端：前台咨询服务: 问诊管理")
public class InterrogationController {

    @Autowired
    private InterrogationService interrogationService;


    /**
     * 查询是否有问诊资格
     * 查询userInfo 是否传了身份证图片
     */
    @ApiOperation(value = "查询是否有问诊资格（实名认证） 返回1-有资格 0-无资格")
    @GetMapping("/interrogationQualifications")
    @Log(name = "问诊管理",value = "查询是否有问诊资格")
    public ApiResult interrogationQualifications(HttpServletRequest request){
        ApiResult apiResult;
        Long userId = Long.valueOf(request.getHeader("id"));
        //查询是否有问诊资格 返回身份证地址
        String s = interrogationService.getInterrogationQualifications(userId);
        if (s != null && !"".equals(s.trim())){//有资格
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(1);
        }else {//无资格
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(0);
        }
        return apiResult;
    }

    /**
     * 结束聊天后默认评分（后台调用）
     */
//    @ApiOperation(value = "结束聊天后默认评分（后台调用）")
//    @PostMapping("/insertEvaluate")
//    @ApiImplicitParam(name = "map",value = "doctorId=医生id   userId=用户id   score=分数" +
//            "   {\"doctorId\":491,\"userId\":1,\"score\":\"5.0\"}",required = true)
//    @Log(name = "问诊管理",value = "结束聊天后默认评分（后台调用）")
//    public ApiResult bindDoctor(@RequestBody Map map, HttpServletRequest request){
//        ApiResult apiResult;
//        Long userId = Long.valueOf((Integer) map.get("userId"));
//        Long doctorId = Long.valueOf((Integer) map.get("doctorId"));
//        Double score;
//        String s = (String) map.get("score");
//        try {
//            score = Double.valueOf(s);
//        }catch (Exception e){
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("分数异常");
//            return apiResult;
//        }
//        VoEvaluate evaluate = new VoEvaluate();
//        evaluate.setDoctorId(doctorId);
//        evaluate.setUserId(userId);
//        evaluate.setScore(score);
//        int i = interrogationService.insertEvaluate(evaluate);
//        apiResult = ApiResult.SUCCESS();
//        apiResult.setData(evaluate.getEvaluateId());
//        return apiResult;
//    }



    /**
     * 对医生评分
     */
//    @ApiOperation(value = "对医生评分")
//    @PostMapping("/doctorEvaluate")
//    @ApiImplicitParam(name = "map",value = "evaluateId=评分id  score=分数" +
//            "   {\"evaluateId\":1,score\":\"4.5\"}",required = true)
//    @Log(name = "医生信息管理",value = "对医生评分")
//    public ApiResult doctorEvaluate(@RequestBody Map map, HttpServletRequest request){
//        ApiResult apiResult;
//        Long evaluateId = Long.valueOf((Integer) map.get("evaluateId"));
//        Double score;
//        String s = (String) map.get("score");
//        try {
//            score = Double.valueOf(s);
//        }catch (Exception e){
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("分数异常");
//            return apiResult;
//        }
//        //修改评分
//        interrogationService.updateEvaluate(evaluateId,score);
//        apiResult = ApiResult.SUCCESS();
//        return apiResult;
//    }


}
