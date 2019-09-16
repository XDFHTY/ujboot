package com.cj.sadmin.service.impl;


import com.cj.core.domain.Customer;
import com.cj.core.entity.*;
import com.cj.core.exception.MyException;
import com.cj.common.mapper.Key64Mapper;
import com.cj.common.service.AuthCustomerRoleService;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.jwt.JwtUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.sadmin.domain.AddAdminResp;
import com.cj.common.domain.LoginResp;
import com.cj.sadmin.domain.UpdateAdminByAdminPassReq;
import com.cj.sadmin.domain.VoAdmin;
import com.cj.sadmin.mapper.AdminInfoMapper;
import com.cj.sadmin.mapper.AdminMapper;
import com.cj.sadmin.service.AdminCallImService;
import com.cj.sadmin.service.AdminService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cj.common.utils.md5.Md5Utils.MD5Encode;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AdminInfoMapper adminInfoMapper;

    @Resource
    private Key64Mapper key64Mapper;

    @Resource
    private AuthCustomerRoleService authCustomerRoleService;

    @Resource
    private AdminCallImService adminCallImService;


    Gson gson = JsonUtil.gson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加账号
    @Override
    public ApiResult addAdmin(VoAdmin voAdmin) {
        ApiResult apiResult;
        Admin admin = voAdmin.getAdmin();
        AdminInfo adminInfo = voAdmin.getAdminInfo();

        //检查账号是否已存在
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        long time = System.currentTimeMillis();

        //账号已存在
        if (oldAdmin != null) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号已存在");
            return apiResult;
        }
        //获取唯一主键
        Key64 key64 = new Key64();
        key64.setStub("a");
        //获取key-adminId
        key64Mapper.addKey64(key64);

//        Customer customer0 = new Customer();
//        customer0.setCustomerId(key64.getId());
//        customer0.setPassword("123456");
//        ApiResult apiResult1 = adminCallImService.imRegister(customer0);
//
//        int i = apiResult1.getCode();
//
//        if (i == 1000){
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("IM注册失败");
//            return apiResult;
//        }

        admin.setAdminId(key64.getId());
        //生成盐值
        String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        admin.setSaltVal(uuid);
        admin.setAdminType("1");  //系统管理员
        admin.setCreateTime(new Date(time));

        if (admin.getAdminPass() == null || "".equals(admin.getAdminPass())) {
            admin.setAdminPass("123456"); //设置初始密码
        }

        //加密密码，MD5（主键+盐值+密码）
        String adminPass = MD5Encode(admin.getAdminId() + admin.getSaltVal() + admin.getAdminPass(), "UTF-8", true);

        admin.setAdminPass(adminPass);
        admin.setAdminType("1");  //设置为管理员

        int j = adminMapper.insertSelective(admin);

        //添加角色
        AuthCustomerRole authCustomerRole = new AuthCustomerRole();
        authCustomerRole.setCustomerId(key64.getId());
        authCustomerRole.setRoleId(voAdmin.getRoleId());
        int k = authCustomerRoleService.addCustomerRole(authCustomerRole);

        adminInfo.setAdminId(admin.getAdminId());
        int l = adminInfoMapper.insertSelective(adminInfo);

        Long adminId = admin.getAdminId();
        AddAdminResp addAdminResp = new AddAdminResp();
        addAdminResp.setAdminId(adminId);
        if (j == 1 && k == 1 && l==1) {
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(addAdminResp);
            return apiResult;
        } else {
            apiResult = ApiResult.FAIL();
            return apiResult;
        }

    }

    @Override
    public int updateAdmin(Admin admin) {
        ApiResult apiResult;
        Admin oldAdmin = adminMapper.findAdminByName(admin);
        String newAdminPass = MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + admin.getAdminPass(), "UTF-8", true);
        admin.setAdminPass(newAdminPass);
        int i = adminMapper.updateAdminPass(admin);

        return i;
    }

    @Override
    public int delete(Long adminId) {
        return adminMapper.deleteAdmin(adminId);
    }

    @Override
    public OldPager findAllAdmin(OldPager oldPager) {

        List<List<?>> lists = adminMapper.findAllAdmin(oldPager);
        oldPager.setLists(lists);
        return oldPager;
    }


    //登录
    @Override
    public ApiResult ifLogin(Admin admin, HttpServletRequest request) {
        ApiResult apiResult = null;

        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        if (oldAdmin == null) {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号不存在");
            apiResult.setParams(request.getRequestURL());
            throw new MyException(apiResult);


        } else if (oldAdmin.getAdminPass().equals(MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + admin.getAdminPass(), "UTF-8", true))) {  //密码正确
            String oldToken = "";
            String token = "";
            String key1 = "";

            Long adminId = oldAdmin.getAdminId();
            String adminName = oldAdmin.getAdminName();


            //查询账号角色信息
            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);

            token = JwtUtil.getToken(adminId, adminName, oldAdmin.getAdminType(), roles);
            key1 = "i" + adminId;

            Customer customer = new Customer();
            customer.setToken(token);
            customer.setCustomerId(adminId);
            customer.setCustomerName(adminName);
            customer.setCustomerType(oldAdmin.getAdminType());
            customer.setRoles(roles);

            String value = gson.toJson(customer);
            stringRedisTemplate.opsForValue().set(key1, value,24l, TimeUnit.HOURS);


            //查询adminInfo信息
            AdminInfo adminInfo = adminInfoMapper.findAdminInfoById(adminId);

            LoginResp loginResp = new LoginResp();
            loginResp.setImId(adminId);
            loginResp.setToken(token);
            loginResp.setIssuedAt(new Date(time));
            loginResp.setRoles(roles);

            apiResult = ApiResult.SUCCESS();
            apiResult.setData(loginResp);
            System.out.println(apiResult);


            request.getSession().setAttribute("name",adminName);
            return apiResult;
        } else {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号不存在或密码错误");
            return apiResult;

        }


    }

    @Override
    public LoginResp ipLogin(Admin admin, HttpServletRequest request) {

        ApiResult apiResult = new ApiResult();


        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        if (oldAdmin == null) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("用户:" + admin.getAdminName() + " 未注册到环境风险管理平台，请联系管理人员");
            throw new MyException(apiResult);

        } else {  //不验证密码，
            String token = "";

            Long adminId = oldAdmin.getAdminId();
            String adminName = oldAdmin.getAdminName();

            String tokenKey = adminId.toString();

            //查询账号角色信息
            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);


            //设置token，有效期
            token = JwtUtil.getToken(adminId, adminName, oldAdmin.getAdminType(), roles);


            LoginResp loginResp = new LoginResp();
            loginResp.setToken(token);
            loginResp.setIssuedAt(new Date(time));
            loginResp.setRoles(roles);

            return loginResp;
        }
    }

    //注销
    @Override
    public int ifLogout(HttpServletRequest request) {
        String ikey = request.getHeader("ikey");
        stringRedisTemplate.delete(ikey);
        return 1;
    }

    //修改密码，校验原密码
    @Override
    public ApiResult updateAdminByAdminPass(HttpServletRequest request, UpdateAdminByAdminPassReq updateAdminByAdminPassReq) {


        String oldAdminPass = updateAdminByAdminPassReq.getOldAdminPass();
        String newAdminPass = updateAdminByAdminPassReq.getNewAdminPass();


        ApiResult apiResult;
        Admin admin = new Admin();
        admin.setAdminName((String) request.getHeader("name"));
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        int i = 0;
        if (MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + oldAdminPass, "UTF-8", true).equals(oldAdmin.getAdminPass())) {

            String md5Pass = MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + newAdminPass, "UTF-8", true);
            oldAdmin.setAdminPass(md5Pass);

            admin.setAdminId(oldAdmin.getAdminId());
            admin.setAdminPass(md5Pass);
            i = adminMapper.updateAdminPass(admin);

            if (i > 0) {
                apiResult = ApiResult.SUCCESS();
            } else {
                apiResult = ApiResult.FAIL();
            }
        } else {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("密码错误");
        }


        return apiResult;
    }


}