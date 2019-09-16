package com.cj.sadmin.controller;


import com.cj.core.entity.Admin;
import com.cj.core.entity.AdminInfo;
import com.cj.common.utils.ResultUtil;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.sadmin.domain.AddAdminResp;
import com.cj.common.domain.LoginResp;
import com.cj.sadmin.domain.UpdateAdminByAdminPassReq;
import com.cj.sadmin.domain.VoAdmin;
import com.cj.sadmin.service.AdminInfoService;
import com.cj.sadmin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/s-admin/api/v1/account")
@Api(tags = "后台: 账号管理-账号管理,登录、注销等")
public class AdminController {
     Long id=null;

    @Autowired
    private AdminService adminService;

    @Resource
    private AdminInfoService adminInfoService;




    /**
     * ============================================管理员登录、注销====================================================
     */

    /**
     * 登录
     */
    @ApiOperation(value = "管理员登录",response = LoginResp.class)
    @PostMapping("/ifLogin")
    @Log(name = "账号管理",value = "管理员登录")
    public ApiResult ifLogin(@ApiParam(name = "admin",value = "adminName=用户名、adminPass=密码,如：\n{\n" +
            "  \"adminName\": \"qwe\",\n" +
            "  \"adminPass\": \"123456\"\n" +
            "}")
                       @RequestBody Admin admin, HttpServletRequest request){

        ApiResult apiResult = adminService.ifLogin(admin,request);


        return apiResult;


    }



    @ApiOperation("注销")
    @DeleteMapping("/ifLogout")
    @Log(name = "账号管理",value = "注销")
    public ApiResult ifLogout(HttpServletRequest request){
        ApiResult apiResult = null;
        int i = adminService.ifLogout(request);
        if(i == 0 ){
            ApiResult.FAIL().setMsg("注销失败");
            apiResult = ApiResult.FAIL();
        }else {
            apiResult = ApiResult.SUCCESS();
        }

        return apiResult;
    }


    /**
     * ==============================================管理员账号维护===========================================================
     */

    /**
     * 新增账号
     */

    @ApiOperation(value = "添加管理员账号",response = AddAdminResp.class)
    @PostMapping("/addAdmin")
    @Log(name = "账号管理",value = "添加管理员账号")
    public ApiResult addAdmin(@ApiParam(name = "admin",value = "adminName、adminPass、roleId、fullName、phone、nickName、heardUrl 必传",required = true)
                                  @RequestBody VoAdmin voAdmin){

        ApiResult apiResult = adminService.addAdmin(voAdmin);
        return apiResult;
    }

    /**
     * 修改密码，不校验原密码
     */
    @ApiOperation("重置密码，不校验原密码")
    @PutMapping("/updateAdmin")
    @Log(name = "账号管理",value = "重置密码")
    public ApiResult updateAdmin(@ApiParam(name = "admin",value = "id=adminId、账号=adminName、adminPass=新密码",required = true)
            @RequestBody Admin admin){
        int i = adminService.updateAdmin(admin);
        ApiResult apiResult = null;
        if(i == 1){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }



    /**
     * 删除账号
     */
    @ApiOperation("删除账号")
    @DeleteMapping("/deleteAdmin")
    @Log(name = "账号管理",value = "删除账号")
    @ApiImplicitParam(name = "adminId",value = "adminId",required = true)
    public ApiResult deleteAdmins(Long adminId){

        System.out.println("===============");
        System.out.println(adminId);

        int i = adminService.delete(adminId);

        ApiResult apiResult = null;
        if(i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;

    }

    /**
     * 根据ID查询管理员详情
     * @param adminId
     * @return
     */
    @ApiOperation(value = "查询管理员详情",response = AdminInfo.class)
    @GetMapping("/findAdminInfo/{adminId}")
    @Log(name = "账号管理",value = "查询管理员详情")
    public ApiResult findAdminInfo(@PathVariable Long adminId){

        return ResultUtil.result(adminInfoService.findAdminInfoById(adminId));
    }



    /**
     * 分页查询所有账号
     */
    @ApiOperation(value = "分页查询所有账号及详情",response = VoAdmin.class)
    @GetMapping("/findAllAdmin/{currentPage}/{pageSize}")
    @Log(name = "账号管理",value = "分页查询所有账号及详情")
    public ApiResult findAllAdmin(@PathVariable int currentPage,@PathVariable int pageSize){
        OldPager oldPager = new OldPager();
        oldPager.setCurrentPage(currentPage);
        oldPager.setPageSize(pageSize);

        return ResultUtil.result(adminService.findAllAdmin(oldPager));
    }


    //=========================================新接口==========

    /**
     * 修改密码，校验原密码
     */
    @PutMapping("/updateAdminByAdminPass")
    @ApiOperation("修改自己的密码，要校验原密码")
    @Log(name = "账号管理",value = "修改自己的密码")
    public ApiResult updateAdminByAdminPass(@ApiParam(name = "json",value = "输入新旧密码")
                                           @RequestBody UpdateAdminByAdminPassReq updateAdminByAdminPassReq,
                                            HttpServletRequest request){

        return adminService.updateAdminByAdminPass(request,updateAdminByAdminPassReq);
    }

    @PutMapping("/updateAdminInfo")
    @ApiOperation("修改用户详情")
    @Log(name = "账号管理",value = "修改用户详情，adminInfoId必传")
    public ApiResult updaueAdminInfo(@RequestBody AdminInfo adminInfo){
        return ResultUtil.result(adminInfoService.updaueAdminInfo(adminInfo));
    }

}
