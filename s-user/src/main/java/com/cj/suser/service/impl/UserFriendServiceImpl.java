package com.cj.suser.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.entity.V2UserFriend;
import com.cj.core.exception.MyException;
import com.cj.suser.domain.VoFriend;
import com.cj.suser.domain.VoUserInfo;
import com.cj.suser.mapper.FallIllMapper;
import com.cj.suser.mapper.UserInfoMapper;
import com.cj.suser.mapper.V2UserFriendMapper;
import com.cj.suser.service.UserCallImService;
import com.cj.suser.service.UserFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Resource
    private V2UserFriendMapper v2UserFriendMapper;

    @Resource
    private UserCallImService userCallImService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private FallIllMapper fallIllMapper;

    @Override
    public VoFriend findUserByPhone(String phone) {
        return v2UserFriendMapper.findUserByPhone("A"+phone);
    }


    @Override
    public int reqBind(long userId, long friendId) {

        if (userId == friendId){
            throw new MyException("不能绑定自己");
        }
        if (v2UserFriendMapper.findCountFrend(userId,friendId) > 0 || v2UserFriendMapper.findCountFrend(friendId,userId) > 0){
            throw new MyException("你们已经绑定了亲友关系了");
        }

        VoFriend user = v2UserFriendMapper.findUserById(userId);
        VoFriend friend = v2UserFriendMapper.findUserById(friendId);

        //推送给亲友
        Map map1 = new HashMap();
        map1.put("appType","1");//推送给用户
        map1.put("days","10");//通知离线保留10天
        List<String> list = new ArrayList<>();
        list.add(friend.getUserId().toString());
        map1.put("alias",list);//推送对象 别名
        map1.put("alert",user.getName()+"("+user.getPhone()+")邀请您成为亲友绑定关系！绑定成功后，您将授权亲友查看您的基本信息、疾病信息、检测数据,请确认 !");

        Map<String,String> map2 = new HashMap<>();//自定义参数
        map2.put("type","5");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知 5-绑定亲友通知
        map2.put("friendId",user.getUserId()+"");

        map1.put("params",map2);

        ApiResult apiResult = userCallImService.buildPushObjectAllAliasAlert(map1);
        if (apiResult.getCode()!=1001){
            throw new MyException("请求发送失败");
        }


        return 1;
    }


    //确认绑定，需检查是否已绑定
    @Override
    public int confirmBind(long userId, long friendId) {
        if (userId == friendId){
            throw new MyException("不能绑定自己");
        }

        Date date = new Date();
        V2UserFriend user = new V2UserFriend();
        user.setUserId(userId);
        user.setFriendId(friendId);
        user.setBindTime(date);

        V2UserFriend friend = new V2UserFriend();
        friend.setUserId(friendId);
        friend.setFriendId(userId);
        friend.setBindTime(date);

        if (v2UserFriendMapper.findCountFrend(userId,friendId) > 0 || v2UserFriendMapper.findCountFrend(friendId,userId) > 0){
            throw new MyException("你们已经绑定了亲友关系了");
        }
        int i = v2UserFriendMapper.insert(user);
        int j = v2UserFriendMapper.insert(friend);
        if ((i+j) == 0){

            throw new MyException("绑定失败");
        }
        //通知 申请人 绑定成功了
        VoFriend user2 = v2UserFriendMapper.findUserById(userId);
        VoFriend friend2 = v2UserFriendMapper.findUserById(friendId);

        //推送给亲友
        Map map1 = new HashMap();
        map1.put("appType","1");//推送给用户
        map1.put("days","10");//通知离线保留10天
        List<String> list = new ArrayList<>();
        list.add(friend2.getUserId().toString());
        map1.put("alias",list);//推送对象 别名
        map1.put("alert",user2.getName()+"("+user2.getPhone()+")同意了您的亲友绑定");

        Map<String,String> map2 = new HashMap<>();//自定义参数
        map2.put("type","6");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知 5-绑定亲友通知
        map2.put("friendId",user2.getUserId()+"");

        map1.put("params",map2);

        ApiResult apiResult = userCallImService.buildPushObjectAllAliasAlert(map1);
        if (apiResult.getCode()!=1001){
            throw new MyException("请求发送失败");
        }


        return i+j;
    }

    //查询亲友
    @Override
    public List<VoFriend> findFriend(long userId) {
        return v2UserFriendMapper.findFrendByUserId(userId);
    }

    @Override
    public VoUserInfo findFriendInfo(long userId) {
        return userInfoMapper.findUserInfoByUserId(userId);
    }

    @Override
    public int delFriend(long userId , long friendId) {
        int i = v2UserFriendMapper.delFriend(userId,friendId);
        if (i==0){
            throw new MyException("解绑失败");
        }
        //通知 亲友 解绑成功了
        VoFriend user2 = v2UserFriendMapper.findUserById(userId);
        VoFriend friend2 = v2UserFriendMapper.findUserById(friendId);

        //推送给亲友
        Map map1 = new HashMap();
        map1.put("appType","1");//推送给用户
        map1.put("days","10");//通知离线保留10天
        List<String> list = new ArrayList<>();
        list.add(friend2.getUserId().toString());
        map1.put("alias",list);//推送对象 别名
        map1.put("alert",user2.getName()+"("+user2.getPhone()+")解除了您的亲友绑定");

        Map<String,String> map2 = new HashMap<>();//自定义参数
        map2.put("type","7");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知 5-绑定亲友通知
        map2.put("friendId",user2.getUserId()+"");

        map1.put("params",map2);

        ApiResult apiResult = userCallImService.buildPushObjectAllAliasAlert(map1);
        if (apiResult.getCode()!=1001){
            throw new MyException("请求发送失败");
        }



        return i;
    }

    @Override
    public OldPager findFallIll(OldPager oldPager) {

        List<List<?>> lists = fallIllMapper.findFallIll(oldPager);

        oldPager.setLists(lists);
        return oldPager;
    }


}
