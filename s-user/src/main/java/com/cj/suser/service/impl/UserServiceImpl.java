package com.cj.suser.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cj.common.domain.LoginResp;
import com.cj.common.domain.RespInfo;
import com.cj.common.mapper.AppVersionMapper;
import com.cj.common.mapper.AuthRoleMapper;
import com.cj.common.mapper.Key64Mapper;
import com.cj.common.service.AuthCustomerRoleService;
import com.cj.common.utils.sms.SmsCodeUtil;
import com.cj.common.utils.sms.VerifyCode;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Customer;
import com.cj.core.entity.*;
import com.cj.core.exception.MyException;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.jwt.JwtUtil;
import com.cj.core.util.reg.RegexUtils;
import com.cj.suser.domain.VoDoctorInfo;
import com.cj.suser.domain.VoUpdatePass;
import com.cj.suser.domain.VoUser;
import com.cj.suser.domain.VoUserInfo;
import com.cj.suser.mapper.DoctorCertificateMapper;
import com.cj.suser.mapper.DoctorInfoMapper;
import com.cj.suser.mapper.UserInfoMapper;
import com.cj.suser.mapper.UserMapper;
import com.cj.suser.service.UserCallImService;
import com.cj.suser.service.UserCallShopService;
import com.cj.suser.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cj.common.utils.md5.Md5Utils.MD5Encode;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private Key64Mapper key64Mapper;

    @Resource
    private AuthRoleMapper authRoleMapper;

    @Resource
    private AuthCustomerRoleService authCustomerRoleService;


    @Resource
    private UserCallImService userCallImService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private DoctorInfoMapper doctorInfoMapper;

    @Resource
    private DoctorCertificateMapper doctorCertificateMapper;

    @Resource
    private AppVersionMapper appVersionMapper;

    @Resource
    private UserCallShopService userCallShopService;

    Gson gson = JsonUtil.gson;


    @Override
    public String getCode(String userName,String phone) {
        String code = "";
        if (stringRedisTemplate.hasKey(userName)){
            code = stringRedisTemplate.opsForValue().get(userName);
        }else {
            code = VerifyCode.getRandomNumCode(6);
        }

        log.info("==============验证码：" + code);
        SendSmsResponse sendSmsResponse = SmsCodeUtil.sendCode(phone, code);
        stringRedisTemplate.opsForValue().set(userName, code, 300l, TimeUnit.SECONDS);
        return code;
    }

    @Value("${jasypt.code}")
    private String rootCode;

    @Override
    public long register(VoUser voUser) throws InvocationTargetException, IllegalAccessException {

        String phone = voUser.getUserName().substring(1);
        if (phone!=null){
            if (!RegexUtils.checkPhone(phone)){

                throw new MyException("手机号错误");
            }
        }

        User user = new User();
        BeanUtils.copyProperties(user, voUser);

        ApiResult apiResult;
        if (!voUser.getCode().equals(rootCode)) {
            if (stringRedisTemplate.hasKey(user.getUserName())) {
                String rcode = stringRedisTemplate.opsForValue().get(user.getUserName());
                if (!rcode.equals(voUser.getCode())) {
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("验证码错误");
                    throw new MyException(apiResult);
                }
            } else {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("验证码不存在");
                throw new MyException(apiResult);
            }

        } else {
            System.out.println("=========>>后台添加账号，无需验证码");
        }

        //检查账号是否已存在
        User oldUser = userMapper.findUserName(user.getUserName());

        long time = System.currentTimeMillis();

        //账号已存在
        if (oldUser != null) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号已存在");
            throw new MyException(apiResult);
        }


        // 获取唯一主键
        Key64 key64 = new Key64();
        key64.setStub("a");
        //获取key-adminId
        key64Mapper.addKey64(key64);
        user.setUserId(key64.getId());
        //生成盐值
        String salval = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        user.setSaltVal(salval);
        user.setCreateTime(new Date(time));
        //加密密码，MD5（主键+盐值+密码）
        String userPass = MD5Encode(user.getUserId() + user.getSaltVal() + user.getUserPass(), "UTF-8", true);
        user.setUserPass(userPass);
        user.setIsPhone("1");
        user.setUserType("0");  //默认都是游客
        user.setState("2");     //都设置成未审核
//        if ("2".equals(user.getUserType()) || "3".equals(user.getUserType())) user.setState("2");
        user.setPhoneNumber(phone);
        int j = userMapper.insertSelective(user);


        //添加角色,默认都是游客
        AuthCustomerRole authCustomerRole = new AuthCustomerRole();
        authCustomerRole.setCustomerId(key64.getId());
        authCustomerRole.setRoleId(getRoleId(user.getUserType()));
        int k = authCustomerRoleService.addCustomerRole(authCustomerRole);


        //注册环信
        Customer customer0 = new Customer();
        customer0.setCustomerId(key64.getId());
        customer0.setPassword("123456");
        ApiResult apiResult1 = userCallImService.imRegister(customer0);
        int i = apiResult1.getCode();
        if (i == 1000) {
            throw new MyException("IM注册失败");
        }

        if ((j & k) == 1) {
            return key64.getId();
        }

        return 0;
    }

    @Override
    public LoginResp login(User user, HttpServletRequest request) {
        User oldUser = userMapper.findUserByName(user.getUserName());
        LoginResp loginResp = new LoginResp();
        ApiResult apiResult = ApiResult.SUCCESS();
        if (oldUser == null) {
            throw new MyException("用户名或密码错误");
        }
//        else if ("2".equals(oldUser.getState())) {
//            loginResp.setImId(oldUser.getUserId());
//            loginResp.setUserType(oldUser.getUserType());
//
//            throw new MyException(ApiResult.CHECK_AGO(loginResp));
//
//        } else if ("3".equals(oldUser.getState())) {
//
//            loginResp.setImId(oldUser.getUserId());
//            loginResp.setUserType(oldUser.getUserType());
//
//            throw new MyException(ApiResult.CHECK_IN(loginResp));
//        } else if ("4".equals(oldUser.getState())) {
//
//            loginResp.setImId(oldUser.getUserId());
//            loginResp.setUserType(oldUser.getUserType());
//            throw new MyException(ApiResult.CHECK_FAIL(loginResp));
//        }
        else if (oldUser.getUserPass().equals(MD5Encode(oldUser.getUserId() + oldUser.getSaltVal() + user.getUserPass(), "UTF-8", true))) {  //密码正确
            String token = "";
            String key1 = "";

            Long userId = oldUser.getUserId();
            String userName = oldUser.getUserName();


            //查询账号角色信息
            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(userId);

            token = JwtUtil.getToken(userId, userName, oldUser.getUserType(), roles);
            key1 = "i" + userId;

            Customer customer = new Customer();
            customer.setToken(token);
            customer.setCustomerId(userId);
            customer.setCustomerName(userName);
            customer.setCustomerType(oldUser.getUserType());
            customer.setRoles(roles);

            String value = gson.toJson(customer);
            stringRedisTemplate.opsForValue().set(key1, value, 24l, TimeUnit.HOURS);


            loginResp.setImId(userId);
            loginResp.setUserType(oldUser.getUserType());
            loginResp.setToken(token);
            loginResp.setIssuedAt(new Date(System.currentTimeMillis()));
            loginResp.setRoles(roles);
            loginResp.setState(oldUser.getState());


            //查询个人信息info
            switch (oldUser.getUserType()) {
                case "0":
                    loginResp.setIsInfo(false);
                    break;
                case "1":
                    VoUserInfo voUserInfo = userInfoMapper.findUserInfoByUserId(userId);
                    if (voUserInfo == null) {
                        loginResp.setIsInfo(false);
                    } else {
                        loginResp.setIsInfo(true);
                    }
                    break;
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    VoDoctorInfo voDoctorInfo = doctorInfoMapper.findDoctorInfoByUserId(userId);
                    if (voDoctorInfo == null) {
                        loginResp.setIsInfo(false);
                    } else {
                        loginResp.setIsInfo(true);
                    }
                    break;
            }

            request.getSession().setAttribute("name", userName);
            return loginResp;
        } else {
            throw new MyException("用户名或密码错误");

        }

    }

    @Override
    public int logout(HttpServletRequest request) {
        String ikey = request.getHeader("ikey");
        stringRedisTemplate.delete(ikey);
        return 1;
    }

    /**
     * 找回密码
     *
     * @param voUser
     * @return
     */
    @Override
    public int retrieveUserPass(VoUser voUser) {
        if (stringRedisTemplate.hasKey(voUser.getUserName())) {
            String rcode = stringRedisTemplate.opsForValue().get(voUser.getUserName());
            if (!rcode.equals(voUser.getCode())) {
                throw new MyException("验证码错误");
            }
        } else {
            throw new MyException("验证码错误");
        }

        //检查账号是否已存在
        User oldUser = userMapper.findUserByName(voUser.getUserName());
        if (oldUser == null) {
            throw new MyException("账号不存在");
        }
        String newPass = MD5Encode(oldUser.getUserId() + oldUser.getSaltVal() + voUser.getUserPass(), "UTF-8", true);
        oldUser.setUserPass(newPass);


        return userMapper.updatePassById(oldUser);
    }



    @Override
    public AppVersion findVersion(String type) {
        return appVersionMapper.findByType(type);
    }

    @Override
    public int updatePass(HttpServletRequest request, VoUpdatePass voUpdatePass) {
        long userId = Long.parseLong(request.getHeader("id"));
        User oldUser = userMapper.selectByPrimaryKey(userId);
        if (!oldUser.getUserPass().equals(MD5Encode(oldUser.getUserId() + oldUser.getSaltVal() + voUpdatePass.getOldPass(), "UTF-8", true))) {
            throw new MyException("原密码错误");
        }
        oldUser.setUserPass(MD5Encode(oldUser.getUserId() + oldUser.getSaltVal() + voUpdatePass.getNewPass(), "UTF-8", true));
        int i = userMapper.updateByPrimaryKeySelective(oldUser);
        if (i == 1) {
            stringRedisTemplate.delete("i" + userId);
            stringRedisTemplate.delete("v" + userId);
            return 1;
        }

        return 0;
    }

    @Override
    public ApiResult getCardInfo(String idCard, String idCardSide) {

        Map map = new HashMap();
        map.put("idCardUrl", idCard);
        map.put("idCardSide", (idCardSide == null || idCardSide.length() == 0) ? "1" : idCardSide);
        ApiResult apiResult = userCallImService.getCardInfo(map);

        return apiResult;
    }

    @Override
    public List<RespInfo> getInfo(String json) {
        List<RespInfo> respInfos = gson.fromJson(json, new TypeToken<List<RespInfo>>() {
        }.getType());

        return userMapper.getInfo(respInfos);
    }

    @Override
    public VoUserInfo findUserInfo(HttpServletRequest request) {
        long userId = Long.parseLong(request.getHeader("id"));

        return userInfoMapper.findUserInfoByUserId(userId);
    }


    @Override
    public int addUserInfo(UserInfo userInfo) {

        if (userInfo.getUserId() != null && userInfo.getName() != null) {
            long roleId = getRoleId("1");
            //修改此用户角色
            int i = authRoleMapper.updateRoleIdByConsumerId(userInfo.getUserId(), roleId);
            //修改此用户类型
            User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
            user.setUserType("1");
            if (userInfo.getIdJustUrl() != null && userInfo.getIdBackUrl() != null) {
                user.setState("1");
            }
            int j = userMapper.updateByPrimaryKeySelective(user);
            int k = userInfoMapper.insertSelective(userInfo);

            if (i == 0 || j == 0 || k == 0) {
                throw new MyException("新增用户详情失败");
            }

            String key1 = "i" + userInfo.getUserId();
            if (stringRedisTemplate.hasKey(key1)) {
                //更新redis角色信息
                String json = stringRedisTemplate.opsForValue().get(key1);
                Customer customer = gson.fromJson(json, Customer.class);
                List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(userInfo.getUserId());
                customer.setRoles(roles);
                String value = gson.toJson(customer);
                stringRedisTemplate.opsForValue().set(key1, value, 24l, TimeUnit.HOURS);
            }
            return i + j + k;
        }
        return 0;

    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {

        if (userInfo.getIdJustUrl() != null && userInfo.getIdBackUrl() != null) {
            long roleId = getRoleId("1");
            //修改此用户角色
            int i = authRoleMapper.updateRoleIdByConsumerId(userInfo.getUserId(), roleId);
            //修改此用户类型
            User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
            user.setUserType("1");
            user.setState("1");
            user.setUpdateTime(new Date());
            int j = userMapper.updateByPrimaryKeySelective(user);
            int k = userInfoMapper.updateByPrimaryKeySelective(userInfo);

            if (i == 0 || j == 0 || k == 0) {
                throw new MyException("修改用户详情失败");
            }

            return i + j + k;

        } else {
            int k = userInfoMapper.updateByPrimaryKeySelective(userInfo);
            return k;
        }
    }

    @Override
    public int updateUserHeadUrl(String headUrl, HttpServletRequest request) {
        long userId = Long.parseLong(request.getHeader("id"));
        return userInfoMapper.updateUserHeadUrl(userId, headUrl);
    }

    @Override
    public VoDoctorInfo findDoctorInfo(HttpServletRequest request, Long doctorId) {

        if (doctorId == null) {
            doctorId = Long.parseLong(request.getHeader("id"));
        }

        return doctorInfoMapper.findDoctorInfoByUserId(doctorId);
    }


    @Override
    public int addDoctorInfo(VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException {

        long roleId = getRoleId(voDoctorInfo.getUserType());


        //修改此用户角色
        int i = authRoleMapper.updateRoleIdByConsumerId(voDoctorInfo.getUserId(), roleId);
        //修改此用户类型
        User user = userMapper.selectByPrimaryKey(voDoctorInfo.getUserId());
        user.setUserType(voDoctorInfo.getUserType());
        int j = userMapper.updateByPrimaryKeySelective(user);

        voDoctorInfo.setRegisterTime(new Date());
        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtils.copyProperties(doctorInfo, voDoctorInfo);
        if ("3".equals(voDoctorInfo.getUserType())){
            doctorInfo.setDepartmentsId(3l);
        }
        int k = doctorInfoMapper.insertSelective(doctorInfo);
        List<DoctorCertificate> doctorCertificates = voDoctorInfo.getDoctorCertificates();

        int l = 0;
        if (doctorCertificates != null && doctorCertificates.size() > 0) {
            l = doctorCertificates.stream().mapToInt(doctorCertificate -> {
                doctorCertificate.setDoctorId(voDoctorInfo.getUserId());
                return doctorCertificateMapper.insertSelective(doctorCertificate);
            }).sum();

        }

        //自动提交审核
        if (voDoctorInfo.getState().equals("2") || voDoctorInfo.getState().equals("4")) {
            submitCheck(voDoctorInfo.getUserId());
        }
        ApiResult apiResult = ApiResult.FAIL();
        if (roleId == 36 || roleId == 37) {
            apiResult = ApiResult.SUCCESS();
        } else if (i > 0 && j > 0 && k > 0) {
            apiResult = userCallShopService.addGood(voDoctorInfo.getUserId(), roleId);

        }

        //调用创建商品接口
        int m = apiResult.getCode();
        if (i == 0 || j == 0 || k == 0 || m != 1001) {
            throw new MyException(1000, "新增医生扩展信息失败");
        }

        String key1 = "i" + voDoctorInfo.getUserId();
        if (stringRedisTemplate.hasKey(key1)) {


            //更新redis角色信息
            String json = stringRedisTemplate.opsForValue().get(key1);
            if (json != null) {
                Customer customer = gson.fromJson(json, Customer.class);
                List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(voDoctorInfo.getUserId());
                customer.setRoles(roles);
                String value = gson.toJson(customer);
                stringRedisTemplate.opsForValue().set(key1, value, 24l, TimeUnit.HOURS);

            }
        }

        return i + j + k + l;
    }

    @Override
    public int updateDoctorInfo(VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException {

        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtils.copyProperties(doctorInfo, voDoctorInfo);
        int i = doctorInfoMapper.updateDoctorInfo(doctorInfo);
        List<DoctorCertificate> doctorCertificates = voDoctorInfo.getDoctorCertificates();

        if (doctorCertificates != null && doctorCertificates.size() > 0) {
            boolean b =
                    doctorCertificates
                            .stream()
                            .allMatch(doctorCertificate -> {
                                doctorCertificate.setDoctorId(voDoctorInfo.getUserId());
                                return 1 == doctorCertificateMapper.updateByPrimaryKeySelective(doctorCertificate);
                            });

        }
        //自动提交审核
        if (voDoctorInfo.getState().equals("2") || voDoctorInfo.getState().equals("4")) {
            submitCheck(voDoctorInfo.getUserId());
        }
        return i;
    }

    @Override
    public int submitCheck(long doctorId) {

        return userMapper.updateState(doctorId);
    }

    @Override
    public int updateDoctorHeadUrl(String headUrl, HttpServletRequest request) {

        long doctorId = Long.parseLong(request.getHeader("id"));
        return doctorInfoMapper.updateUserHeadUrl(doctorId, headUrl);
    }


    //用户类型，0-游客、1-用户 、2-家庭医生、3-专科医生 、4-营养师、5-乡干部、6-卫健委干部、7-离退休干部、8-护士、9-健康管理师
    private long getRoleId(String userType) {
//        查询所有的角色信息
        List<AuthRole> authRoles = authRoleMapper.findAllAuthRole();
        long roleId = 0;
        for (AuthRole authRole : authRoles) {
            switch (userType) {
                case "0":
                    if (authRole.getRoleName().equals("游客")) roleId = authRole.getRoleId();
                    break;
                case "1":
                    if (authRole.getRoleName().equals("用户")) roleId = authRole.getRoleId();
                    break;
                case "2":
                    if (authRole.getRoleName().equals("家庭医生")) roleId = authRole.getRoleId();
                    break;
                case "3":
                    if (authRole.getRoleName().equals("肾病医生")) roleId = authRole.getRoleId();
                    break;
                case "4":
                    if (authRole.getRoleName().equals("营养师")) roleId = authRole.getRoleId();
                    break;
                case "5":
                    if (authRole.getRoleName().equals("乡干部")) roleId = authRole.getRoleId();
                    break;
                case "6":
                    if (authRole.getRoleName().equals("卫健委干部")) roleId = authRole.getRoleId();
                    break;
                case "7":
                    if (authRole.getRoleName().equals("离退休干部")) roleId = authRole.getRoleId();
                    break;
                case "8":
                    if (authRole.getRoleName().equals("护士")) roleId = authRole.getRoleId();
                    break;
                case "9":
                    if (authRole.getRoleName().equals("健康管理师")) roleId = authRole.getRoleId();
                    break;
            }
        }

        return roleId;
    }

    //管理员修改密码调用
    @Override
    public int putPass(long userId, String userPass) {
        User oldUser = userMapper.selectByPrimaryKey(userId);
        if (oldUser == null) {
            return 0;
        }
        oldUser.setUserPass(MD5Encode(oldUser.getUserId() + oldUser.getSaltVal() + userPass, "UTF-8", true));
        int i = userMapper.updateByPrimaryKeySelective(oldUser);
        return i;
    }

}
