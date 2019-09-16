package com.cj.sconsult.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2Team;
import com.cj.sconsult.domain.RespConsultant;
import com.cj.sconsult.entity.RespDoctorInfo;
import com.cj.sconsult.entity.RespTeam;
import com.cj.sconsult.entity.RespTeamInfo;
import com.cj.sconsult.service.TeamService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 团队 管理
 * Created by JuLei on 2019/6/26.
 */
@RestController
@RequestMapping("/s-consult/api/v2/team")
@Api(tags = "后台&医生端：咨询服务-团队管理")
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * 创建团队
     */
    @ApiOperation(value = "创建团队")
    @PostMapping("/create")
    @Log(name = "团队管理",value = "创建团队")
    public ApiResult bindDoctor(@RequestBody V2Team team, HttpServletRequest request){
        ApiResult apiResult = teamService.create(team,request);
        return apiResult;
    }


    /**
     * 解散团队
     */
    @ApiOperation(value = "解散团队")
    @DeleteMapping("/dismiss")
    @Log(name = "团队管理",value = "解散团队")
    @ApiImplicitParam(name = "teamId", value = "团队Id",required = true)
    public ApiResult dismiss(Long teamId, HttpServletRequest request){
        ApiResult apiResult = teamService.dismiss(teamId,request);
        return apiResult;
    }

    /**
     * 邀请入团
     */
    @ApiOperation(value = "邀请入团")
    @PostMapping("/join")
    @Log(name = "团队管理",value = "邀请入团")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "teamId", value = "团队Id",required = true),
//            @ApiImplicitParam(name = "userId", value = "用户id",required = true)
//    })
    public ApiResult join( @RequestBody Map map ,HttpServletRequest request){
        Integer teamId = ((Double)map.get("teamId")).intValue() ;
        Integer userId = ((Double)map.get("userId")).intValue();
        ApiResult apiResult = teamService.join(teamId.longValue(),userId.longValue(),request);
        return apiResult;
    }


    /**
     * 移除团员
     */
    @ApiOperation("移除团员")
    @DeleteMapping("/deletePerson")
    @Log(name = "团队管理",value = "移除团员")
    public ApiResult deletePerson(Long userId,Long teamId,HttpServletRequest request){

        return teamService.deletePerson(userId,teamId,request);

    }




    /**
     * 根据姓名或手机号搜索  医生信息
     * 可以查询的角色包括 专科医生 家庭医生 营养师 乡干部 卫健委干部 离退休干部 护士 健康管理师
     */
    @ApiOperation(value = "根据手机号或姓名搜索关注端角色的信息")
    @GetMapping("/findDoctorInfo/{parameter}")
    @Log(name = "团队管理",value = "根据手机号或姓名搜索关注端角色的信息")
    public ApiResult findDoctorInfo(@PathVariable(value = "parameter")String parameter,
                                      HttpServletRequest request){
        ApiResult apiResult = teamService.findDoctorInfo(parameter,request);
        return apiResult;
    }


    /**
     * 查询医生id查询团队信息
     */
    @ApiOperation(value = "根据医生id查询团队信息",response = RespTeamInfo.class)
    @GetMapping("/findTeamInfo/{doctorId}")
    @Log(name = "团队管理",value = "根据医生id查询团队信息")
    public ApiResult findTeamInfo(@PathVariable(value = "doctorId")Long doctorId,
                                  HttpServletRequest request){
        ApiResult apiResult = teamService.findTeamInfo(doctorId,request);
        return apiResult;
    }


    /**
     * 查询团队列表
     * @param parameters
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询团队列表",response = RespTeam.class)
    @GetMapping("/findTeamList/{current}")
    @ApiImplicitParam(name = "parameters",
            required = true,value = "captainName=团长姓名  teamName=团队名称 type=医生类别",
            defaultValue = "{\"captainName\":\"张xx\",\"teamName\":\"xx团队\",\"type\":\"家庭医生\"}")
    @Log(name = "团队管理",value = "分页查询团队列表")
    public ApiResult findTeamList(@PathVariable(value = "current") Integer current,String parameters, HttpServletRequest request){

        Pager pager = new Pager();
        pager.setCurrent(current);
        Map map = JsonUtil.gson.fromJson(parameters,Map.class);
        pager.setParameters(map);


        Pager pager2 = teamService.findTeamList(pager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager2);
        return apiResult;
    }
    /**
     * 查询成员列表
     */
    @ApiOperation(value = "根据团队id查询成员列表",response = RespDoctorInfo.class)
    @GetMapping("/findPersonList/{current}")
    @ApiImplicitParam(name = "parameter",
            required = true,value = "parameter=团队id ")
    @Log(name = "团队管理",value = "根据团队id查询成员列表")
    public ApiResult findPersonList(@PathVariable(value = "current") Integer current,String parameter, HttpServletRequest request){

        Pager pager = new Pager();
        pager.setCurrent(current);
        pager.setParameter(parameter);


        Pager pager2 = teamService.findPersonList(pager);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager2);
        return apiResult;
    }


    /**
     * 编辑团队信息
     */
    @ApiOperation(value = "编辑团队信息")
    @PutMapping("/update")
    @Log(name = "团队管理",value = "编辑团队信息")
    public ApiResult update(@RequestBody V2Team team, HttpServletRequest request){
        int i = teamService.update(team, request);
        ApiResult apiResult;
        if (i==2){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("团队名称重复");
            return apiResult;
        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

}
