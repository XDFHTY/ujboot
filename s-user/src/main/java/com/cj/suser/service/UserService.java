package com.cj.suser.service;

import com.cj.common.domain.LoginResp;
import com.cj.common.domain.RespInfo;
import com.cj.core.domain.ApiResult;
import com.cj.core.entity.AppVersion;
import com.cj.core.entity.User;
import com.cj.core.entity.UserInfo;
import com.cj.suser.domain.VoDoctorInfo;
import com.cj.suser.domain.VoUpdatePass;
import com.cj.suser.domain.VoUser;
import com.cj.suser.domain.VoUserInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {


    public String getCode(String userName,String phone);


    public long register(VoUser voUser) throws InvocationTargetException, IllegalAccessException;


    public LoginResp login(User user,HttpServletRequest request);

    public int logout(HttpServletRequest request);


    public int retrieveUserPass(VoUser voUser);



    public AppVersion findVersion(String type);

    public int updatePass(HttpServletRequest request, VoUpdatePass voUpdatePass);

    public ApiResult getCardInfo(String idCard,String idCardSide);

    public List<RespInfo> getInfo(String json);


    int putPass( long userId,String userPass);

    //==========================userInfo==================================


    public VoUserInfo findUserInfo(HttpServletRequest request);

    public int addUserInfo(UserInfo userInfo);

    public int updateUserInfo(UserInfo userInfo);

    public int updateUserHeadUrl(String headUrl,HttpServletRequest request);


    //========================doctorInfo================================

    public VoDoctorInfo findDoctorInfo(HttpServletRequest request,Long doctorId);

    public int addDoctorInfo(VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException;

    public int updateDoctorInfo(VoDoctorInfo voDoctorInfo) throws InvocationTargetException, IllegalAccessException;

    public int submitCheck(long doctorId);

    public int updateDoctorHeadUrl(String headUrl,HttpServletRequest request);
}
