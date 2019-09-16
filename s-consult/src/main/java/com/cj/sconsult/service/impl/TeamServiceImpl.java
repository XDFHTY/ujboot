package com.cj.sconsult.service.impl;

import com.cj.common.domain.VoScore;
import com.cj.common.utils.hx.IMUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2GroupConversation;
import com.cj.core.v2entity.V2Team;
import com.cj.core.v2entity.V2TeamPerson;
import com.cj.sconsult.entity.RespDoctorInfo;
import com.cj.sconsult.entity.RespTeam;
import com.cj.sconsult.entity.RespTeamInfo;
import com.cj.sconsult.mapper.ConsultMapper;
import com.cj.sconsult.mapper.V2GroupConversationMapper;
import com.cj.sconsult.mapper.V2TeamMapper;
import com.cj.sconsult.mapper.V2TeamPersonMapper;
import com.cj.sconsult.service.ConsultCallImService;
import com.cj.sconsult.service.ConsultCallShopService;
import com.cj.sconsult.service.TeamService;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 团队管理
 * Created by JuLei on 2019/6/26.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    private V2TeamMapper teamMapper;

    @Autowired
    private V2TeamPersonMapper teamPersonMapper;

    @Autowired
    private V2GroupConversationMapper groupConversationMapper;

    @Autowired
    private ConsultCallImService consultCallImService;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private ConsultCallShopService consultCallShopService;

    IMUtil imUtil = new IMUtil();

    Gson gson = JsonUtil.gson;
    /**
     * 创建团队
     * @param team
     * @param request
     * @return
     */
    @Override
    public ApiResult create(V2Team team, HttpServletRequest request) {
        ApiResult apiResult;
        try {
            //获取操作人
            String userId = request.getHeader("id");
            //获取操作时间
            Date nowDate = new Date();


            //判断此角色是否可以创建团队
            //可以创建团队的角色：29家庭医生 30肾病专家 32营养师 33乡干部 34卫健委 35离退休干部
            Long roleId = this.consultMapper.findRoleById(team.getTeamCaptainId());
            if (roleId!=29 && roleId!=30 && roleId!=32 && roleId!=33 && roleId!=34 && roleId!=35){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("只有角色为：家庭医生、肾病专家、营养师、乡干部、卫健委干部、离退休干部，能创建团队");
                return apiResult;
            }

            //查询此人是否创建或加入了未解散的团队
            int i = this.teamMapper.findTeamNumById(team.getTeamCaptainId());
            if (i>0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("创建团队失败，此用户已经创建或加入过团队");
                return apiResult;
            }

            //查询团队名称唯一性
            int k = this.teamMapper.findTeamNumByName(team.getTeamName());
            if (k>0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("创建团队失败，团队名称已经被使用");
                return apiResult;
            }

            //创建团队
            team.setFounderId(Long.valueOf(userId));
            team.setCreateTime(nowDate);
            int l = this.teamMapper.insert(team);
            if (l == 0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("创建团队失败，请重试");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                return apiResult;
            }

            //团队成员表添加一条记录
            V2TeamPerson teamPerson = new V2TeamPerson();
            teamPerson.setCustomerId(team.getTeamCaptainId());
            teamPerson.setJoinTime(nowDate);
            teamPerson.setTeamId(team.getTeamId());
            int i2 = this.teamPersonMapper.insert(teamPerson);
            if (i2 == 0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("创建团队失败,无法添加团长，请重试");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                return apiResult;
            }

            //查询此人绑定的所有用户
            List<Long> bindUserIdList = this.teamMapper.findBindUserById(team.getTeamCaptainId());
            if (bindUserIdList != null && bindUserIdList.size()!=0){//有绑定的用户
                //结束此人 所有和用户的单独聊天
                int j = this.teamMapper.endConversion(team.getTeamCaptainId(),nowDate);

                for (Long userId2:bindUserIdList){
                    List<String> list = new ArrayList<>();
                    list.add(userId2 + "");//用户作为群成员
                    //创建多个 群聊
                    String hxGroupId =imUtil.createGroup(team.getTeamCaptainId() + "", list, "");
                    if (hxGroupId == null || "".equals(hxGroupId)){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("环信创建团队失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                    //创建群会话
                    V2GroupConversation groupConversation = new V2GroupConversation();
                    groupConversation.setHxGroupId(hxGroupId);
                    groupConversation.setTeamId(team.getTeamId());
                    groupConversation.setUserId(userId2);
                    int j2 = this.groupConversationMapper.insert(groupConversation);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("创建团队失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        try {
            //推送给医生 创建成功
            List<String> doctors = new ArrayList<>();
            doctors.add(team.getTeamCaptainId() + "");
            Map map1 = new HashMap();
            map1.put("appType","2");//推送给医生
            map1.put("days","10");//通知离线保留10天
            map1.put("alias",doctors);//推送对象 别名
            map1.put("alert", "团队：" + team.getTeamName() + ",创建成功~");

            Map<String,String> map2 = new HashMap<>();//自定义参数
            map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map1.put("params",map2);

            consultCallImService.buildPushObjectAllAliasAlert(map1);
        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("推送消息失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 解散团队
     * @param teamId
     * @param request
     * @return
     */
    @Override
    public ApiResult dismiss(Long teamId, HttpServletRequest request) {
        ApiResult apiResult;
        V2Team team;
        try {
            //获取操作人
            String userId = request.getHeader("id");
            //获取操作时间
            Date nowDate = new Date();

            //根据团队id查询未解散的团队
            team = this.teamMapper.findTeamById(teamId);
            if (team == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("没有此团队，请刷新列表");
                return apiResult;
            }

            //解散团队
            team.setIsDismiss("0");//解散标识
            int i = this.teamMapper.updateById(team);
            if (i == 0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("解散失败，请重试");
                return apiResult;
            }

            //清空团队成员
            int j = this.teamPersonMapper.updatePersonByTeamId(teamId);
            if (j == 0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("解散失败，无法清空团队成员，请重试");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                return apiResult;
            }
            //解散IM的群组
            //查询这个团队下 所有未结束的群会话
            List<String> hxGroupIds = this.groupConversationMapper.findHxGroupIdsByTeamId(teamId);
            if (hxGroupIds != null && hxGroupIds.size()!=0){
                for (String hxId:hxGroupIds){
                    if (!imUtil.deleteGroup(hxId)){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("删除环信群组失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                }
            }


            //查询此人绑定的所有用户
            List<Long> bindUserIdList = this.teamMapper.findBindUserById(team.getTeamCaptainId());
            if (bindUserIdList != null && bindUserIdList.size()!=0){//有绑定的用户
                //根据团队id  结束群会话
                this.groupConversationMapper.endConversationByTeamId(teamId,nowDate);

                //创建多个和团长的单独会话
                for (Long userId2:bindUserIdList){
                    this.groupConversationMapper.addConversion(team.getTeamCaptainId(),userId2,nowDate);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("解散团队失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }


        try {
            //推送给医生 创建成功
            List<String> doctors = new ArrayList<>();
            doctors.add(team.getTeamCaptainId() + "");

            Map map1 = new HashMap();
            map1.put("appType","2");//推送给医生
            map1.put("days","10");//通知离线保留10天
            map1.put("alias",doctors);//推送对象 别名
            map1.put("alert","团队：" + team.getTeamName() + ",已解散");

            Map<String,String> map2 = new HashMap<>();//自定义参数
            map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map1.put("params",map2);

            consultCallImService.buildPushObjectAllAliasAlert(map1);
        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("推送消息失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 加入团队
     * @param teamId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult join(Long teamId, Long userId2, HttpServletRequest request) {
        ApiResult apiResult;
        V2Team team;
        try {
            //获取操作人
            String userId = request.getHeader("id");
            //获取操作时间
            Date nowDate = new Date();


            //查询此人是否创建或加入了未解散的团队
            int i = this.teamMapper.findTeamNumById(userId2);
            if (i>0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("加入团队失败，此用户已经创建或加入过团队");
                return apiResult;
            }


            //根据团队id查询未解散的团队
            team = this.teamMapper.findTeamById(teamId);
            if (team == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("没有此团队，请刷新列表");
                return apiResult;
            }



            //判断角色 是否有资格入团
            Long roleId = this.consultMapper.findRoleById(team.getTeamCaptainId());//邀请者角色
            Long roleId2 = this.consultMapper.findRoleById(userId2);//被邀请者角色

            //29-专科医生  30-家庭医生  32-营养师 33-乡干部 34-卫健委 35 离退休干部
            //36-护士  37-健康管理师
            if (roleId == 29 || roleId == 30){//专科医生和家庭医生 可以邀请 29、30、36
                if (roleId2!=29 && roleId2!=30 && roleId2!=36){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("角色不符，请查看邀请规则");
                    return apiResult;
                }
            }else if (roleId == 32){//营养师  可以邀请 32、37
                if (roleId2!=32 && roleId2!=37){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("角色不符，请查看邀请规则");
                    return apiResult;
                }
            }else if (roleId == 33){//乡干部 可以邀请 33
                if (roleId2!=33){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("角色不符，请查看邀请规则");
                    return apiResult;
                }
            }else if (roleId == 34){//卫健委干部 可以邀请 34
                if (roleId2!=34){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("角色不符，请查看邀请规则");
                    return apiResult;
                }
            }else if (roleId == 35){//离退休干部 可以邀请 35
                if (roleId2!=35){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("角色不符，请查看邀请规则");
                    return apiResult;
                }
            }








            //根据用户id和团队id 查询成员
            V2TeamPerson person = this.teamPersonMapper.findDataByUserIdAndTeamId(userId2,teamId);
            if (person != null){
                if ("1".equals(person.getDeleteState())){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("此用户已在团队中，请勿重复添加");
                    return apiResult;
                }else {
                    //修改
                    person.setDeleteState("1");
                    this.teamPersonMapper.updateById(person);
                }
            }else {
                //添加
                V2TeamPerson person2 = new V2TeamPerson();
                person2.setTeamId(teamId);
                person2.setJoinTime(nowDate);
                person2.setCustomerId(userId2);
                this.teamPersonMapper.insert(person2);
            }

            //向环信 添加群成员
            //查询这个团队下 所有未结束的群会话
            List<String> hxGroupIds = this.groupConversationMapper.findHxGroupIdsByTeamId(teamId);
            if (hxGroupIds != null && hxGroupIds.size()!=0){
                for (String hxId : hxGroupIds) {
                    if (!imUtil.addUserToGroup(hxId,userId2 + "")){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("加入环信群组失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("加入团队失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        try {
            //查询医生名字
            String doctorName = this.teamPersonMapper.findDoctorNameById(userId2);
            //推送给团长
            List<String> doctors = new ArrayList<>();
            doctors.add(team.getTeamCaptainId() + "");

            Map map1 = new HashMap();
            map1.put("appType","2");//推送给医生
            map1.put("days","10");//通知离线保留10天
            map1.put("alias",doctors);//推送对象 别名
            map1.put("alert", doctorName + "已加入您的团队");

            Map<String,String> map2 = new HashMap<>();//自定义参数
            map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map1.put("params",map2);

            consultCallImService.buildPushObjectAllAliasAlert(map1);





            //查询医生名字
            String doctorName2 = this.teamPersonMapper.findDoctorNameById(team.getTeamCaptainId());
            //推送给成员
            List<String> doctors2 = new ArrayList<>();
            doctors2.add(userId2 + "");

            Map map3 = new HashMap();
            map3.put("appType","2");//推送给医生
            map3.put("days","10");//通知离线保留10天
            map3.put("alias",doctors2);//推送对象 别名
            map3.put("alert", "您已加入" + doctorName2 + "的团队");

            Map<String,String> map4 = new HashMap<>();//自定义参数
            map4.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map3.put("params",map2);

            consultCallImService.buildPushObjectAllAliasAlert(map3);

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("推送消息失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 移除团员
     * @param userId
     * @param teamId
     * @param request
     * @return
     */
    @Override
    public ApiResult deletePerson(Long userId2, Long teamId, HttpServletRequest request) {
        ApiResult apiResult;
        V2Team team;
        try {
            //获取操作人
            String userId = request.getHeader("id");
            //获取操作时间
            Date nowDate = new Date();


            //根据团队id查询未解散的团队
            team = this.teamMapper.findTeamById(teamId);
            if (team == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("没有此团队，请刷新列表");
                return apiResult;
            }
            if (userId2 == team.getTeamCaptainId()){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("不能移除团长");
                return apiResult;
            }

            //查询此人是否创建或加入了未解散的团队
//            int i = this.teamMapper.findTeamNumById(userId2);
//            if (i==0){
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("移除团员失败，此用户没有加入团队,请刷新列表");
//                return apiResult;
//            }

            //修改记录  移除团员
            int j = this.teamPersonMapper.updateStateById(teamId,userId2);
            if (j == 0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("移除团员失败，此用户没有加入团队,请刷新列表");
                return apiResult;
            }

            //向环信 移除群成员
            //查询这个团队下 所有未结束的群会话
            List<String> hxGroupIds = this.groupConversationMapper.findHxGroupIdsByTeamId(teamId);
            if (hxGroupIds != null && hxGroupIds.size()!=0){
                for (String hxId : hxGroupIds) {
                    if (!imUtil.removeUserFromGroup(hxId,userId2 + "")){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("移除环信群组成员失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("移除成员失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }


        try {
            //查询医生名字
            String doctorName = this.teamPersonMapper.findDoctorNameById(userId2);
            //推送给团长
            List<String> doctors = new ArrayList<>();
            doctors.add(team.getTeamCaptainId() + "");
            Map map1 = new HashMap();
            map1.put("appType","2");//推送给医生
            map1.put("days","10");//通知离线保留10天
            map1.put("alias",doctors);//推送对象 别名
            map1.put("alert",doctorName + "已退出您的团队");

            Map<String,String> map2 = new HashMap<>();//自定义参数
            map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map1.put("params",map2);

            consultCallImService.buildPushObjectAllAliasAlert(map1);

            //查询医生名字
//            String doctorName2 = this.teamPersonMapper.findDoctorNameById(team.getTeamCaptainId());
//            //推送给成员
//            Map map2 = new HashMap();
//            map2.put("days","10");//消息离线保留10天
//            map2.put("alert", "您已被移除" + doctorName2 + "的团队");
//            List<String> doctors2 = new ArrayList<>();
//            doctors2.add(userId2 + "");
//            map2.put("alias",doctors2);
//            consultCallImService.buildPushObjectAllAliasAlert(map2);

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("推送消息失败，请重试");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 根据姓名或手机号搜索  医生信息
     * 可以查询的角色包括 专科医生 家庭医生 营养师 乡干部 卫健委干部 离退休干部 护士 健康管理师
     */
    @Override
    public ApiResult findDoctorInfo(String parameter, HttpServletRequest request) {
        //根据手机号或姓名搜索关注端角色的信息
        List<RespDoctorInfo> list = this.consultMapper.findDoctorInfo(parameter);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }

    /**
     * 查询医生id查询团队信息
     * @param doctorId
     * @param request
     * @return
     */
    @Override
    public ApiResult findTeamInfo(Long doctorId, HttpServletRequest request) {
        ApiResult apiResult = ApiResult.SUCCESS();
        //查询这个医生加入 或创建的团队Id
        V2Team team = this.teamMapper.findTeamIdByDoctorId(doctorId);
        if (team!=null){
            RespTeamInfo teamInfo = this.teamMapper.findTeamInfoById(team.getTeamId());
            if (teamInfo != null){
                teamInfo.setNumber(teamInfo.getList().size());//团员人数
                teamInfo.setRoleId(this.consultMapper.findRoleById(teamInfo.getTeamCaptainId()));//团长角色id
            }
            apiResult.setData(teamInfo);


            //获取团长的咨询量和评分
            ApiResult apiResult2 = this.consultCallShopService.findScore(team.getTeamCaptainId());
            VoScore voScore = gson.fromJson(gson.toJson(apiResult2.getData()),VoScore.class);
            if (voScore != null){
                teamInfo.setInquiry(voScore.getConsultNum());//设置咨询量
                teamInfo.setScore(voScore.getAverageScore());//设置评分
            }
        }
        return apiResult;
    }

    /**
     * 分页查询团队列表
     * @param pager
     * @return
     */
    @Override
    public Pager findTeamList(Pager pager) {
        List<RespTeam> list = this.teamMapper.findAll(pager);
        //查询咨询量和评分
        if (list!=null && list.size()!=0){
            for (RespTeam info:list){
                //获取团长的咨询量和评分
                ApiResult apiResult2 = this.consultCallShopService.findScore(info.getTeamCaptainId());
                VoScore voScore = gson.fromJson(gson.toJson(apiResult2.getData()),VoScore.class);
                if (voScore != null){
                    info.setInquiry(voScore.getConsultNum());//设置咨询量
                    info.setScore(voScore.getAverageScore());//设置评分
                }
            }
        }

        pager.setRecords(list);
        return pager;
    }

    /**
     * 编辑团信息
     * @param team
     * @param request
     * @return
     */
    @Override
    public int update(V2Team team, HttpServletRequest request) {
        //查询团队名称唯一性
        if (team.getTeamName() != null && !"".equals(team.getTeamName().trim())){
            int k = this.teamMapper.findTeamNumByName(team.getTeamName());
            if (k>0){
                return 2;
            }
        }
        return this.teamMapper.updateById(team);
    }

    /**
     * 根据团队id查询成员列表
     * @param pager
     * @return
     */
    @Override
    public Pager findPersonList(Pager pager) {
        pager.setRecords(this.teamMapper.findPersonListByTeamId(pager));
        return pager;
    }
}
