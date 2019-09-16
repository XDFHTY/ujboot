package com.cj.sim.service.impl;

import com.cj.common.domain.RespInfo;
import com.cj.core.domain.ApiResult;
import com.cj.core.entity.AdminInfo;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.ObjectUtil;
import com.cj.sim.entity.Conversation;
import com.cj.sim.entity.RespConverstion;
import com.cj.sim.entity.RespUserInfo;
import com.cj.sim.mapper.ConversationMapper;
import com.cj.sim.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 会话管理
 * Created by XD on 2019/3/4.
 */
@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private ImCallUserService imCallUserService;

    @Autowired
    private ImCallConsultService imCallConsultService;

    @Autowired
    private ImCallAdminService imCallAdminService;

    @Autowired
    private MsgService msgService;

    Gson gson = JsonUtil.gson;


    /**
     * 创建会话
     *
     * @param map
     * @return 会话id  会话已存在 则返回0
     */
    @Override
    public RespConverstion createConversation(Map map, HttpServletRequest request) {
        //获取操作者id
        String userId = request.getHeader("id");
        String roles = request.getHeader("userRoleIds");

        //检查会话是否存在  并判断是状态为 未结束
        Conversation conversation = this.conversationMapper.findConversationState(map);
        if (conversation == null){
            //需要创建新的会话 返回会话id

            //创建会话前  先把这两个用户 已结束的会话 全部删除 物理删除
            this.conversationMapper.deleteConversation((String)map.get("fromUser"),(String)map.get("toUser"));

            //创建新的会话 返回会话id
            Conversation conversation2 = new Conversation();
            conversation2.setFromUser((String) map.get("fromUser"));
            conversation2.setToUser((String) map.get("toUser"));
            conversation2.setStartDate(new Date());//会话创建时间
            conversation2.setType(String.valueOf(map.get("type")));
            int j = this.conversationMapper.createRespId(conversation2);
            conversation = new Conversation();
            conversation.setConversationId(conversation2.getConversationId());

            if (j <= 0){
                //创建会话失败
                return null;
            }
        }
        String toUser = (String) map.get("toUser");
        RespConverstion respConverstion = new RespConverstion();
        respConverstion.setConversationId(conversation.getConversationId());
        String type  = String.valueOf(map.get("type"));
        if ("1".equals(type)){//对象是医生
            //查询toUser的头像昵称
            List<RespInfo> respInfos = new ArrayList<>();
            RespInfo respInfo = new RespInfo();
            respInfo.setUserId(Long.valueOf(toUser));
            respInfos.add(respInfo);
            ApiResult apiResult = imCallUserService.getInfo(gson.toJson(respInfos));
            if (!"".equals(apiResult.getData())){
                List<Map> respInfoList = (List<Map>) apiResult.getData();
                if(respInfoList != null && respInfoList.size()!=0){
                    //设置 头像昵称
                    Map map1 = respInfoList.get(0);
                    respConverstion.setHeadUrl(String.valueOf(map1.get("headUrl")));
                    respConverstion.setNickName(String.valueOf(map1.get("nickname")));
                    respConverstion.setName(String.valueOf(map1.get("name")));
                    //如果当前是用户 则判断该医生的用户id列表
                    if (roles.indexOf("31") != -1){//是用户
                        List<Integer> ids = (List<Integer>) map1.get("bindIds");//获取医生绑定的用户id集合
                        boolean b = ids.stream().anyMatch(q -> userId.equals(q + ""));//判断当前用户id 是否存在 与list中
                        if (!b){
                            respConverstion.setBindType("0");//非绑定关系
                        }else {
                            respConverstion.setBindType("1");
                        }
                    }
                }
            }




        }else {//对象是咨询员
            ApiResult info = imCallAdminService.getInfo(Integer.valueOf(toUser));

            if (!"".equals(info.getData())){
                Map map1  = (Map) info.getData();
                AdminInfo adminInfo = null;
                try {
                    adminInfo = (AdminInfo) ObjectUtil.mapToObject(map1,AdminInfo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (adminInfo != null){
                    respConverstion.setHeadUrl(adminInfo.getHeardUrl());
                    respConverstion.setNickName(adminInfo.getNickName());
                }
            }

        }
        return respConverstion;

    }

    /**
     * 结束会话
     *
     * @param conversationId
     * @return 返回成功条数
     */
    @Override
    public int endConversation(Long conversationId,HttpServletRequest request) {
        //获取操作者id
        String userId = request.getHeader("id");


        //判断该会话是否已结束
        Conversation conversation = conversationMapper.selectByPrimaryKey(conversationId);
        //如果 未查到该会话 或者 会话状态为0 那么则是该回话不存在 或 已结束
        if (conversation == null){
            return -1;
        }
        if("0".equals(conversation.getEndState())){
            return -2;
        }
        //结束会话  修改 结束状态 和 结束时间
        int i = this.conversationMapper.endConversation(conversationId, new Date());

        //确定医生id
        String doctorId;
        if (userId.equals(conversation.getFromUser())){
            doctorId = conversation.getToUser();
        }else {
            doctorId = conversation.getFromUser();
        }
        //添加默认评分 5.0分
        Map map = new HashMap();
        map.put("userId",Long.valueOf(userId));
        map.put("doctorId",Long.valueOf(doctorId));
        map.put("score","5.0");
        ApiResult apiResult = imCallConsultService.insertEvaluate(map);
        Integer evaluateId = (Integer) apiResult.getData();

        //发送透传消息 告知医生 会话已结束  禁止聊天
        Map map1 = new HashMap();
        map1.put("msg","closingSession");
        map1.put("toUser",doctorId);
        map1.put("fromUser",userId);
        Object o = msgService.sendCmdMsg(map1);
        return evaluateId;
    }


    /**
     * 删除会话
     * 删除的会话必定是已结束的  未结束的不一定就删除
     * @param
     * @return
     */
    @Override
    public int deleteConversation(Long conversationId,String user) {
        //删除会话前 判断这个会话是否存在
        Conversation conversation = conversationMapper.selectByPrimaryKey(conversationId);
        if (conversation == null){
            //会话不存在，已被逻辑删除
            return -1;
        }

        //删除会话 向删除标记表 加一条记录
        return this.conversationMapper.insertConversationDelete(conversationId,user);
    }


    /**
     * 查询会话列表 未结束的  已结束的
     * @param
     * @param
     * @return
     */
    @Override
    public List<Conversation>  getList(Map map, HttpServletRequest request) {



        //获取操作者id
        //String userId2 = request.getHeader("id");
        //String roles = request.getHeader("userRoleIds");

        //查询该用户的角色
        Long roleId = this.conversationMapper.findRoleIdByUserId(map);

        String endState = (String) map.get("endState");
        List<Conversation> list = new ArrayList<>();
        if ("1".equals(endState)){
            //未结束的会话 直接查就行  因为两个用户直接只会存在一条未结束的会话
            list =  this.conversationMapper.getListByNormal(map);

        }else if ("0".equals(endState)){
            //已结束的会话  需要去重， 因为两个用户 会存在多条已结束的会话
            list =  this.conversationMapper.getListByEnd(map);

        }

        List<RespInfo> respInfos = new ArrayList<>();
        //当前用户
        String userId = (String) map.get("userName");
        //查询用户的头像和昵称
        if (list != null && list.size() != 0){
            for (Conversation info:list){
                RespInfo respInfo = new RespInfo();
                if (userId.equals(info.getFromUser())){//如果当前用户是发送者  则去查 接收者信息
                    respInfo.setUserId(Long.valueOf(info.getToUser()));
                }else {//如果当前用户是接收者  就去查 发送者信息
                    respInfo.setUserId(Long.valueOf(info.getFromUser()));
                }
                //找到医生 设置商品类型
                Long roleId2 = this.conversationMapper.findRoleId(info.getFromUser());
                if (roleId2!=31){//如果fromUser不是用户 则去查他的商品类型
                    info.setGoodType(this.conversationMapper.getGoodTypeById(Long.valueOf(info.getFromUser())));//查询医生的商品类型
                }else {
                    info.setGoodType(this.conversationMapper.getGoodTypeById(Long.valueOf(info.getToUser())));//查询医生的商品类型
                }
                respInfos.add(respInfo);
            }



            ApiResult apiResult = imCallUserService.getInfo(gson.toJson(respInfos));

            List<Map> respInfoList = null;
            if (!"".equals(apiResult.getData())){
                respInfoList = (List<Map>) apiResult.getData();
            }



            //双重for循环 设置 头像昵称
            for (Conversation info:list){
                info.setConversationType("1");//单独聊天会话
                for (Map map1:respInfoList){
                    String s = String.valueOf((Integer) map1.get("userId"));

                    if (s.equals(info.getFromUser()) || s.equals(info.getToUser())){
                        info.setNickName(String.valueOf(map1.get("nickname")));
                        info.setName(String.valueOf(map1.get("name")));
                        info.setHeadUrl(String.valueOf(map1.get("headUrl")));
                    }

                    //如果当前是用户 则判断该医生的用户id列表
//                    if (roles.indexOf("31") != -1) {//是用户
//                        if (s.equals(info.getFromUser()) || s.equals(info.getToUser())){
//                            List<Integer> ids = (List<Integer>) map.get("bindIds");//获取医生绑定的用户id集合
//                            boolean b = ids.stream().anyMatch(q -> userId2.equals(q + ""));//判断当前用户id 是否存在 与list中
//                            if (!b){
//                                info.setBindType("0");//非绑定关系
//                            }else {
//                                info.setBindType("1");
//                            }
//                        }
//                    }

                }
            }
        }







        //查询管理员的昵称头像
//        if (list != null && list.size()!=0){
//            for (Conversation info:list){
//                ApiResult apiResult = null;
//                if (info.getNickName() == null){//如果姓名是null 则去查询管理员
//                    if (userId.equals(info.getFromUser())){//如果当前用户是发送者  则去查 接收者信息
//                        apiResult = imCallAdminService.getInfo(Integer.valueOf(info.getToUser()));
//                    }else {//如果当前用户是接收者  就去查 发送者信息
//                        apiResult = imCallAdminService.getInfo(Integer.valueOf(info.getFromUser()));
//                    }
//                    AdminInfo adminInfo = null;
//                    if (apiResult != null){
//                        if (!"".equals(apiResult.getData())){
//                            Map map1  = (Map) apiResult.getData();
//                            try {
//                                adminInfo = (AdminInfo) ObjectUtil.mapToObject(map1,AdminInfo.class);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (adminInfo != null){
//                            info.setHeadUrl(adminInfo.getHeardUrl());
//                            info.setNickName(adminInfo.getNickName());
//                        }
//                    }
//                }
//            }
//        }


        List<Conversation> list2;
        //查询群会话列表
        if (roleId == 31){
            //查询用户的会话列表
            list2 = this.conversationMapper.findGroupConversionByUser(map);
        }else {
            //查询医生的会话列表
            if ("1".equals(endState)){//查询未结束的
                list2 = this.conversationMapper.findGroupConversionByDoctorNotEnd(map);
            }else {//查询已结束的
                list2 = this.conversationMapper.findGroupConversionByDoctorEnd(map);
            }

        }

        if (list2 != null && list2.size()!=0){
            for (Conversation conversation:list2){
                conversation.setConversationType("2");
                list.add(conversation);
            }
        }


        return list;
    }

    /**
     * 根据医生id查询群会话列表
     * @param
     * @param request
     * @return
     */
    @Override
    public List<Conversation> getTeamMsgList(Long userId, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("userName",userId);
        //查询已结束的
        List<Conversation> list = this.conversationMapper.findGroupConversionByDoctorEnd(map);
        //查询未结束的
        List<Conversation> list2 = this.conversationMapper.findGroupConversionByDoctorNotEnd(map);

        list.addAll(list2);
        return list;
    }

    /**
     * 根据医生id 和 患者id 查询会话列表
     * @param request
     * @return
     */
    @Override
    public List<Conversation> getMsgListByIds(Map map, HttpServletRequest request) {
        //type发起方 1-患者 2-医生
        String tpye = ((Double) map.get("type")).intValue() + "";

        List<Conversation> list = new ArrayList<>();

        //已结束的会话  需要去重， 因为两个用户 会存在多条已结束的会话
        list =  this.conversationMapper.getListByEnd2(map);

        //未结束的会话 直接查就行  因为两个用户直接只会存在一条未结束的会话
        List<Conversation> list2 = this.conversationMapper.getListByNormal2(map);

        list.addAll(list2);




        List<RespInfo> respInfos = new ArrayList<>();

        //查询用户的头像和昵称
        if (list != null && list.size() != 0){
            for (Conversation info:list){
                RespInfo respInfo = new RespInfo();
                Double d = (Double) map.get("doctorId");
                Double d2 = (Double) map.get("userId");
                if ("1".equals(tpye)){//如果当前发起是用户  则去查 医生头像用户名信息

                    respInfo.setUserId(d.longValue());
                    info.setGoodType(this.conversationMapper.getGoodTypeById(d.longValue()));//查询医生的商品类型
                }else {
                    respInfo.setUserId(d2.longValue());
                    info.setGoodType(this.conversationMapper.getGoodTypeById(d.longValue()));//查询医生的商品类型
                }
                respInfos.add(respInfo);
            }



            ApiResult apiResult = imCallUserService.getInfo(gson.toJson(respInfos));

            List<Map> respInfoList = null;
            if (!"".equals(apiResult.getData())){
                respInfoList = (List<Map>) apiResult.getData();
            }



            //双重for循环 设置 头像昵称
            for (Conversation info:list){
                info.setConversationType("1");//单独聊天会话
                for (Map map1:respInfoList){
                    String s = String.valueOf((Integer) map1.get("userId"));

                    if (s.equals(info.getFromUser()) || s.equals(info.getToUser())){
                        info.setNickName(String.valueOf(map1.get("nickname")));
                        info.setName(String.valueOf(map1.get("name")));
                        info.setHeadUrl(String.valueOf(map1.get("headUrl")));
                    }
                }
            }
        }





        List<Conversation> list3;
        //查询群会话列表
        if ("1".equals(tpye)){
            //查询用户的会话列表
            list3 = this.conversationMapper.findGroupConversionByUser2(map);
        }else {
            //查询医生的会话列表
            list3 = this.conversationMapper.findGroupConversionByDoctorEnd2(map);
        }

        if (list3 != null && list3.size()!=0){
            for (Conversation conversation:list3){
                conversation.setConversationType("2");
                list.add(conversation);
            }
        }


        return list;
    }



    /**
     * 根据环信群聊id查询所有用户
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult findUserInfo(Long hxGroupId, HttpServletRequest request) {
        //查询用户的信息
        RespUserInfo userInfo = this.conversationMapper.findUserInfoByHxGroupId(hxGroupId);
        userInfo.setUserType("3");//用户类型 1-群主 2-医生 3-用户

        //查询医生的信息
        List<RespUserInfo> doctorList = this.conversationMapper.findDoctorInfoByHxGroupId(hxGroupId);
        doctorList.add(userInfo);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(doctorList);
        return apiResult;
    }

    /**
     * 根据团队id和用户id查询群会话
     * @param teamId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult findGroupConversation(Long teamId, Long userId, HttpServletRequest request) {
        Conversation conversation = this.conversationMapper.findGroupConversation(teamId,userId);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(conversation);
        return apiResult;
    }
}
