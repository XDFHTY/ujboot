package com.cj.sconsult.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sconsult.domain.RespConsultant;
import com.cj.common.domain.BindDoctorVo;
import com.cj.sconsult.entity.RespDoctorInfo;
import com.cj.sconsult.entity.RespDoctorOrTeam;
import com.cj.sconsult.entity.RespTeamInfo;
import com.cj.sconsult.service.SCDoctorService;
import com.google.gson.Gson;
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
@RequestMapping("/s-consult/api/v1/doctor")
@Api(tags = "公共接口：前台咨询服务: 医生管理")
public class SCDoctorController {


    @Autowired
    private SCDoctorService doctorService;


    /**
     * 前台查询医生信息列表
     * @param json
     * @return
     */
//    @ApiOperation(value = "查询医生列表", response = DoctorModel.class)
//    @GetMapping("/findPageDoctor")
//    @ApiImplicitParam(name = "json" ,
//            value ="province=省 city=市 area=区 hospitalId=医院表id departmentsId=科室id title=职称 " +
//                    "sex=性别 0-男 1-女 sort=排序 0-咨询量 1-评价 2-综合 userType = 用户类型，1-用户，2-医生，3-专家",
//            required = true,
//            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
//                    "\"parameters\":{\"province\":1,\"city\":1,\"area\":1," +
//                    "\"departmentsId\":1,\"hospitalId\":1,\"sort\":1," +
//                    "\"title\":职称,\"sex\":\"1\",\"userType\":\"1\"}}")
//    @Log(name = "医生信息管理",value = "查询医生账号信息列表")
//    public ApiResult findPageDoctor(String json, HttpServletRequest request){
//
//        ApiResult apiResult = doctorService.getDocPage(json,request);
//        return apiResult;
//    }

    /**
     * 前台查询咨询员列表
     * @param json
     * @return
     */
//    @ApiOperation(value = "分页查询咨询员列表",response = RespConsultant.class)
//    @GetMapping("/findPageConsultant")
//    @ApiImplicitParam(name = "json",
//            required = true,
//            defaultValue = "{\"currentPage\":1,\"pageSize\":10}")
//    @Log(name = "医生信息管理",value = "分页查询咨询员列表")
//    public ApiResult findPageConsultant(String json, HttpServletRequest request){
//
//        OldPager pager1 = JsonUtil.gson.fromJson(json,OldPager.class);
//
//        OldPager pager = new OldPager();
//        pager.setCurrentPage(pager1.getCurrentPage());
//        pager.setPageSize(pager1.getPageSize());
//
//        OldPager pager2 = doctorService.findPageConsultant(pager);
//        ApiResult apiResult = ApiResult.SUCCESS();
//        apiResult.setData(pager2);
//        return apiResult;
//    }




    /**
     * 申请绑定,解绑 医生/专家
     */
//    @ApiOperation(value = "申请绑定,解绑 医生/专家")
//    @PostMapping("/bindDoctor")
//    @ApiImplicitParam(name = "map",value = "doctorId=绑定对象id   state=状态 1-申请绑定 2-解绑" +
//            "   {\"doctorId\":491,\"state\":\"1\"}",required = true)
//    @Log(name = "医生信息管理",value = "申请绑定,解绑 医生/专家")
//    public ApiResult bindDoctor(@RequestBody Map map, HttpServletRequest request){
//        ApiResult apiResult = doctorService.bindDoctor(map,request);
//        return apiResult;
//    }


    /**
     * 医生 同意/拒绝 绑定
     */
//    @ApiOperation(value = "医生 同意/拒绝 绑定")
//    @PostMapping("/acceOrRefuse")
//    @ApiImplicitParam(name = "map",value = "doctorId=医生id   userId=用户id  state=状态 1-同意 2-拒绝" +
//            "   {\"doctorId\":491,\"userId\":1,\"state\":\"1\"}",required = true)
//    @Log(name = "医生信息管理",value = "医生 同意/拒绝 绑定")
//    public ApiResult acceOrRefuse(@RequestBody Map map, HttpServletRequest request){
//        ApiResult apiResult = doctorService.acceOrRefuse(map,request);
//        return apiResult;
//    }



    /**
     * 删除加好友记录  加好友失败  前端调用此接口
     */
//    @ApiOperation(value = "删除加好友记录")
//    @DeleteMapping("/bindData")
//    @ApiImplicitParam(name = "userBindDoctorId", value = "绑定记录Id",required = true)
//    @Log(name = "医生信息管理",value = "删除加好友记录")
//    public ApiResult bindData(Integer userBindDoctorId, HttpServletRequest request){
//        ApiResult apiResult;
//        int i = doctorService.deleteBindData(userBindDoctorId);
//        if (i > 0){
//            apiResult = ApiResult.SUCCESS();
//        }else {
//            apiResult = ApiResult.FAIL();
//        }
//        return apiResult;
//    }



    /**
     * 查询已绑定的 家庭医生 / 专家 / 卫健委 /乡干部 / 离退休干部 / 营养师  的个人或团队
     */
    @ApiOperation(value = "查询已绑定的管理员",response = RespDoctorOrTeam.class)
    @GetMapping("/getDoctorById")
    @ApiImplicitParam(name = "type",value = "type=医生类型 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部", defaultValue = "1", type = "String",required = true)
    @Log(name = "医生信息管理",value = "查询已绑定的管理员")
    public ApiResult getDoctorById(String type, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("id"));
        ApiResult apiResult = doctorService.getDoctorById(type,userId,request);
        return apiResult;
    }

    /**
     * 查询已  绑定的 / 卫健委 /乡干部 / 离退休干部 的个人或团队
     */
    @ApiOperation(value = "查询已绑定的管理机构（卫健委、乡干部、离退休干部）",response = RespDoctorOrTeam.class)
    @GetMapping("/getBindManage")
    @Log(name = "医生信息管理",value = "查询已绑定的管理机构（卫健委、乡干部、离退休干部）")
    public ApiResult getBindManage(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("id"));
        ApiResult apiResult = doctorService.getBindManage(userId,request);
        return apiResult;
    }


    /**
     * 查询医生绑定的患者列表
     */
    @ApiOperation(value = "查询医生绑定的患者列表",response = RespConsultant.class)
    @GetMapping("/findBindPatientList")
    @ApiImplicitParam(name = "json",
            required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":\"10\"," +
                    "\"parameters\":{\"doctoryId\":491}}")
    @Log(name = "医生信息管理",value = "查询医生绑定的患者列表")
    public ApiResult findBindPatientList(String json, HttpServletRequest request){

        OldPager oldPager = doctorService.findBindPatientList(JsonUtil.gson.fromJson(json,OldPager.class));
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(oldPager);
        return apiResult;
    }


    /**
     * 用户绑定医生
     */
    @ApiOperation(value = "用户绑定医生")
    @PostMapping("/userBindDoctor")
    @Log(name = "医生信息管理",value = "用户绑定医生")
    public ApiResult userBindDoctor(@RequestBody BindDoctorVo bindDoctorVo, HttpServletRequest request){
        ApiResult apiResult = doctorService.userBindDoctor(bindDoctorVo,request);
        return apiResult;
    }


    /**
     * 用户解绑医生
     */
    @ApiOperation(value = "用户解绑医生")
    @PutMapping("/userRelieveDoctor")
    @Log(name = "医生信息管理",value = "用户解绑医生")
    @ApiImplicitParam(name = "map",value = "doctorId=绑定对象id  userId=用户id" +
          "   {\"doctorId\":491,\"userId\":477}",required = true)
    public ApiResult userRelieveDoctor(@RequestBody Map map, HttpServletRequest request){
        ApiResult apiResult = doctorService.userRelieveDoctor(map,request);
        return apiResult;
    }


    /**
     * 聊天界面点击更多时调用
     * 根据团队id 查询团队详情和成员信息
     */
    @ApiOperation(value = "查询团队详情和成员列表",response = RespTeamInfo.class)
    @GetMapping("/findTeamInfo/{teamId}")
    @Log(name = "医生信息管理",value = "查询团队详情和成员列表")
    public ApiResult findTeamInfo(@PathVariable(value = "teamId")Long teamId,
                                    HttpServletRequest request){
        ApiResult apiResult = doctorService.findTeamInfo(teamId,request);
        return apiResult;
    }

    /**
     * 团队详情界面点击成员头像时调用
     * 根据医生id  查询医生详情
     */
    @ApiOperation(value = "根据医生id查询医生详情",response = RespDoctorInfo.class)
    @GetMapping("/findDoctorInfo/{userId}")
    @Log(name = "医生信息管理",value = "根据医生id查询医生详情")
    public ApiResult findDoctorInfo(@PathVariable(value = "userId")Long userId,
                                  HttpServletRequest request){
        ApiResult apiResult = doctorService.findDoctorInfo(userId,request);
        return apiResult;
    }


    /**
     * 查询有没有解绑资格
     */
    @ApiOperation(value = "查询有没有解绑资格")
    @GetMapping("/findUntying/{userId}/{doctorId}")
    @Log(name = "医生信息管理",value = "查询有没有解绑资格")
    public ApiResult findUntying(@PathVariable(value = "userId")Long userId,
                                 @PathVariable(value = "doctorId")Long doctorId,
                                    HttpServletRequest request){
        ApiResult apiResult = doctorService.findUntying(userId,doctorId,request);
        return apiResult;
    }

    /**
     * 查询用户的头像和昵称
     */
    @ApiOperation(value = "查询用户的头像和昵称")
    @GetMapping("/findUserInfo/{userId}")
    @Log(name = "医生信息管理",value = "查询用户的头像和昵称")
    public ApiResult findUserInfo(@PathVariable(value = "userId")Long userId,
                                 HttpServletRequest request){
        ApiResult apiResult = doctorService.findUserInfo(userId,request);
        return apiResult;
    }


}
