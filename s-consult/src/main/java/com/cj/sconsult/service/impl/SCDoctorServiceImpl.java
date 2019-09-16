package com.cj.sconsult.service.impl;

import com.cj.common.domain.RespInfo;
import com.cj.common.domain.VoScore;
import com.cj.common.utils.hx.IMUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.core.v2entity.V2GroupConversation;
import com.cj.core.v2entity.V2Team;
import com.cj.common.domain.BindDoctorVo;
import com.cj.sconsult.entity.*;
import com.cj.sconsult.mapper.ConsultMapper;
import com.cj.sconsult.mapper.V2GroupConversationMapper;
import com.cj.sconsult.mapper.V2TeamMapper;
import com.cj.sconsult.service.ConsultCallPensionService;
import com.cj.sconsult.service.ConsultCallShopService;
import com.cj.sconsult.service.ConsultCallUserService;
import com.cj.sconsult.service.SCDoctorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 前台咨询服务: 查询医生
 * Created by XD on 2019/3/14.
 */
@Service
@Transactional
public class SCDoctorServiceImpl implements SCDoctorService {

    //数据库中的 医生角色ID
    private Long DB_DOCTER_ROLE_ID = 30l;

    //数据库中的 专家角色ID
    private Long DB_EXPERT_ROLE_ID = 29l;

    //数据库中的 营养师角色ID
    private Long DB_NUTRITION_ROLE_ID = 32l;

    //数据库中的 乡干部角色ID
    private Long DB_CADRES_ROLE_ID = 33l;

    //数据库中的 卫健委角色ID
    private Long DB_COMMISSION_ROLE_ID = 34l;

    //数据库中的 离退休干部角色ID
    private Long DB_RETIRE_ROLE_ID = 35l;

    Gson gson = JsonUtil.gson;


    @Autowired
    private ConsultCallPensionService consultCallPensionService;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private ConsultCallUserService consultCallUserService;

    @Autowired
    private V2TeamMapper teamMapper;

    @Autowired
    private V2GroupConversationMapper groupConversationMapper;

    @Autowired
    private ConsultCallImServiceImpl consultCallImService;

    @Autowired
    private ConsultCallShopService consultCallShopService;

    IMUtil imUtil = new IMUtil();


    @Override
    public ApiResult getDocPage(String json, HttpServletRequest request) {
        return consultCallPensionService.getInfo(json,request);
    }

    @Override
    public OldPager findPageConsultant(OldPager oldPager) {
        //计数
        oldPager.setRecordTotal(this.consultMapper.findPageConsultantCount());

        //分页查询咨询员
        oldPager.setContent(this.consultMapper.findPageConsultant(oldPager));

        return oldPager;
    }

    /**
     * 绑定,解绑 医生/专家
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult bindDoctor(Map map, HttpServletRequest request) {
        ApiResult apiResult;
        Long userId = Long.valueOf(request.getHeader("id"));
        String state = (String) map.get("state");//绑定状态
        Long doctorId = Long.valueOf((Integer) map.get("doctorId"));//医生id

        if("1".equals(state)){//申请绑定操作
            String docterType = null;
            //查询医生是 专家 还是家庭医生
            Long roleId = this.consultMapper.findRoleById(doctorId);
            if (DB_DOCTER_ROLE_ID == roleId){//是家庭医生
                docterType = "1";
            }else if (DB_EXPERT_ROLE_ID == roleId){//是专家
                docterType = "2";
            }
            //判断是否有绑定资格
            int i = this.consultMapper.findHaveBindPower(userId,docterType);
            if (i>0){//已经绑定了
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("请先解绑或等待医生同意");
                return apiResult;
            }else {
                //添加申请绑定记录
                VoUserBindDoctor userBindDoctor = new VoUserBindDoctor();
                userBindDoctor.setUserId(userId);
                userBindDoctor.setDoctorId(doctorId);
                userBindDoctor.setDoctorType(docterType);
                userBindDoctor.setOperationTime(new Date());
                int j = this.consultMapper.insertBindData(userBindDoctor);
                if (j>0){
                    apiResult = ApiResult.SUCCESS();
                    apiResult.setData(userBindDoctor.getUserBindDoctorId());
                    return apiResult;
                }else {
                    apiResult = ApiResult.FAIL();
                    return apiResult;
                }
            }

        }else {//解绑操作
            //修改绑定状态 0-已解绑  1-已绑定 2-绑定中，待同意  3-被拒绝
            int j = this.consultMapper.updateBindState1(userId, doctorId, "0", new Date());
            if (j>0){
                apiResult = ApiResult.SUCCESS();
                return apiResult;
            }else {
                apiResult = ApiResult.FAIL();
                return apiResult;
            }
        }
    }

    /**
     * 医生 同意/拒绝 绑定
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult acceOrRefuse(Map map, HttpServletRequest request) {
        ApiResult apiResult;
        String s = (String) map.get("state");
        Long userId = Long.valueOf((Integer) map.get("userId"));
        Long doctorId = Long.valueOf((Integer) map.get("doctorId"));

        String bindState;

        if ("1".equals(s)){
            //同意
            bindState = "1";
        }else {
            //拒绝
            bindState = "3";
        }
        //医生 同意/拒绝 绑定
        int i = this.consultMapper.updateBindState2(userId,doctorId,bindState,new Date());

        apiResult = ApiResult.SUCCESS();
        return apiResult;

    }

    /**
     * 查询已绑定的 家庭医生 / 专家 / 卫健委 /乡干部 / 离退休干部 / 营养师  的个人或团队
     * 医生类型 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部
     * @param type
     * @param userId
     * @return
     */
    @Override
    public ApiResult getDoctorById(String type, Long userId,HttpServletRequest request) {
        ApiResult apiResult;
        //查询已绑定的家庭医生或专家
        Long doctorId = this.consultMapper.getDoctorById(userId, type);

        if (doctorId == null){
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        }


        //查询这个医生有没有创建的未结束的团队
        V2Team team = this.teamMapper.findTeamByDoctorId(doctorId);
        RespDoctorOrTeam  respDoctorOrTeam;
        if (team == null){
            //查询医生信息
            respDoctorOrTeam = this.teamMapper.findDoctorInfo(doctorId);
            respDoctorOrTeam.setInfoType("1");//1-个人信息 2-团队信息
        }else {
            //查询团队信息
            respDoctorOrTeam = this.teamMapper.findDoctorTeamInfo(doctorId,userId);
            respDoctorOrTeam.setInfoType("2");//1-个人信息 2-团队信息
            respDoctorOrTeam.setDoctorId(doctorId);
        }

        //获取医生咨询量和评分
        ApiResult apiResult2 = this.consultCallShopService.findScore(doctorId);
        VoScore voScore = gson.fromJson(gson.toJson(apiResult2.getData()),VoScore.class);
        if (voScore != null){
            respDoctorOrTeam.setInquiry(voScore.getConsultNum());//设置咨询量
            respDoctorOrTeam.setScore(voScore.getAverageScore());//设置评分
        }

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(respDoctorOrTeam);
        return apiResult;
    }

    /**
     * 查询医生绑定的患者列表
     * @param oldPager
     * @return
     */
    @Override
    public OldPager findBindPatientList(OldPager oldPager) {
        oldPager.setContent(this.consultMapper.findBindPatientList(oldPager));
        oldPager.setRecordTotal(this.consultMapper.findBindPatientListCount(oldPager));
        return oldPager;
    }

    /**
     * 删除加好友记录
     * @param userBindDoctorId
     * @return
     */
    @Override
    public int deleteBindData(Integer userBindDoctorId) {
        return this.consultMapper.deleteBindData(userBindDoctorId);
    }

    /**
     * 用户绑定医生
     *
     * @param bindDoctorVo
     * @param request
     * @return
     */
    @Override
    public ApiResult userBindDoctor(BindDoctorVo bindDoctorVo, HttpServletRequest request) {
        ApiResult apiResult;
        //用户id
        Long userId = bindDoctorVo.getUserId();
        Long userRoleId = this.consultMapper.findRoleById(userId);
        if (userRoleId==38){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("绑定失败，用户未完善个人信息");
            return apiResult;
        }

        //医生id集合
        List<Long> doctorIdList = bindDoctorVo.getDoctorIds();
        Date nowDate = new Date();

        List<String> list = new ArrayList<>();
        list.add(userId + "");//用户作为群成员

        if (doctorIdList!=null && doctorIdList.size()!=0){
            for (Long doctorId:doctorIdList){
                //查询医生角色
                Long roleId = this.consultMapper.findRoleById(doctorId);
                //医生分类 1-家庭医生 2-肾病专家 3-营养师 4-乡干部 5-卫健委 6-离退休干部

//////////////////////////////////////////////////////////////////家庭医生//////////////////////////////////////////////////////
                if (DB_DOCTER_ROLE_ID == roleId){//是家庭医生
                    //根据用户id查询是否已经绑定了
                    int i = this.consultMapper.findBindNumById(userId,"1");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了家庭医生");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"1",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }


//////////////////////////////////////////////////////////////////专家//////////////////////////////////////////////////////
                }else if (DB_EXPERT_ROLE_ID == roleId){//是专家
                    int i = this.consultMapper.findBindNumById(userId,"2");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了肾病专家");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"2",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }


//////////////////////////////////////////////////////////////////营养师//////////////////////////////////////////////////////
                }else if (DB_NUTRITION_ROLE_ID == roleId){//营养师
                    int i = this.consultMapper.findBindNumById(userId,"3");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了营养师");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"3",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamIdByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }


//////////////////////////////////////////////////////////////////乡干部//////////////////////////////////////////////////////
                }else if (DB_CADRES_ROLE_ID == roleId){//乡干部
                    int i = this.consultMapper.findBindNumById(userId,"4");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了乡干部");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"4",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamIdByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }




//////////////////////////////////////////////////////////////////卫健委//////////////////////////////////////////////////////
                }else if (DB_COMMISSION_ROLE_ID == roleId){//卫健委
                    int i = this.consultMapper.findBindNumById(userId,"5");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了卫健委");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"5",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamIdByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }



//////////////////////////////////////////////////////////////////离退休干部//////////////////////////////////////////////////////
                }else if (DB_RETIRE_ROLE_ID == roleId){//离退休干部
                    int i = this.consultMapper.findBindNumById(userId,"6");
                    if (i>0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已经绑定了离退休干部");
                        return apiResult;
                    }
                    //添加绑定记录
                    int j = this.consultMapper.addBindData(userId,doctorId,"6",nowDate);
                    if (j > 0){
                        //绑定成功
                        //查询医生是否创建有未解散的团队
                        V2Team team = this.teamMapper.findTeamIdByDoctorId(doctorId);
                        if (team == null){
                            //创建单独会话
                            this.groupConversationMapper.addConversion(doctorId,userId,nowDate);
                        }else {
                            //创建群聊会话
                            //查询这个群里所有的人
                            List<Long> personList = this.consultMapper.findPersonListByTeamId(team.getTeamId());
                            if (personList != null && personList.size()!=0){
                                for (Long pId:personList){
                                    if (pId != doctorId){//过滤掉群主
                                        list.add(pId + "");//把群成员 加入群聊
                                    }
                                }
                            }
                            String hxGroupId =imUtil.createGroup(doctorId + "", list, "");
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
                            groupConversation.setUserId(userId);
                            int j2 = this.groupConversationMapper.insert(groupConversation);
                        }
                        continue;
                    }else {
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }

                }


            }
        }

        //查询患者姓名
        String name = this.consultMapper.findUserNameById(userId);

        if(doctorIdList != null && doctorIdList.size()!=0){
            List<String> list1 = new ArrayList<>();
            for (Long id:doctorIdList){
                list1.add(id.toString());
            }
            try {
                //推送给医生
                Map map1 = new HashMap();
                map1.put("appType","2");//推送给医生
                map1.put("days","10");//通知离线保留10天
                map1.put("alias",list1);//推送对象 别名
                map1.put("alert","患者“" + name + "”已成功绑定您为医生");

                Map<String,String> map2 = new HashMap<>();//自定义参数
                map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知 5-绑定亲友通知
                map1.put("params",map2);

                consultCallImService.buildPushObjectAllAliasAlert(map1);
            }catch (Exception e){
                e.printStackTrace();
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("推送消息失败，请重试");
//            return apiResult;
            }
        }


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 用户解绑医生
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult userRelieveDoctor(Map map, HttpServletRequest request) {
        Long userId = Long.valueOf(((Double)map.get("userId")).intValue());
        Long doctorId = Long.valueOf(((Double)map.get("doctorId")).intValue());
        ApiResult apiResult;
        Date date = new Date();

        //查询医生角色
        Long roleId =  this.consultMapper.findRoleById(doctorId);
        //判断用户和这个医生是否有未到期的订单
        int s = this.consultMapper.findOrderById(userId, doctorId);
        int z = 0;
        if(roleId == 29){
            //判断是否有肾病服务包
            z = this.consultMapper.findOrderByIdAndType(userId,2);
        }else if(roleId == 30){
            //判断是否有家庭服务包
            z = this.consultMapper.findOrderByIdAndType(userId,1);
        }
        if (s > 0 || z > 0){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("解绑失败，用户还有未完成的订单");
            return apiResult;
        }

        //解绑操作
        int i = this.consultMapper.relieveDoctor(userId,doctorId,date);
        if (i==0){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("解绑失败，请刷新列表");
            return apiResult;
        }
        //查询医生是否创建有未解散的团队
        V2Team team = this.teamMapper.findTeamByDoctorId(doctorId);
        if (team == null){
            //结束 单独对话
            int j = teamMapper.updateConversionById(userId,doctorId,date);

        }else {
            //结束群聊会话
            //根据团队id和用户id 查询未结束的群会话
            V2GroupConversation groupConversation = this.groupConversationMapper.findConversionById(team.getTeamId(),userId);
            if (groupConversation != null){
                groupConversation.setIsEnd("0");
                groupConversation.setEndTime(date);
                int k = this.groupConversationMapper.updateById(groupConversation);
                if (k>0){
                    if (!imUtil.deleteGroup(groupConversation.getHxGroupId())){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("删除环信群组失败，请重试");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                }
            }

        }
        //查询患者姓名
        String name = this.consultMapper.findUserNameById(userId);
        try {
            //推送给医生
            List<String> doctors = new ArrayList<>();
            doctors.add(doctorId + "");

            Map map1 = new HashMap();
            map1.put("appType","2");//推送给医生
            map1.put("days","10");//通知离线保留10天
            map1.put("alias",doctors);//推送对象 别名
            map1.put("alert","患者“" + name +"”已解绑");

            Map<String,String> map2 = new HashMap<>();//自定义参数
            map2.put("type","4");//消息类型 1-用药 2-系统 3-异常提醒 4-绑定通知
            map1.put("params",map2);
            consultCallImService.buildPushObjectAllAliasAlert(map1);
        }catch (Exception e){
            e.printStackTrace();
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("推送消息失败，请重试");
//            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 查询团队详情和成员信息
     * @param teamId
     * @param request
     * @return
     */
    @Override
    public ApiResult findTeamInfo(Long teamId, HttpServletRequest request) {
        ApiResult apiResult = ApiResult.SUCCESS();
        RespTeamInfo teamInfo = this.teamMapper.findTeamInfoById(teamId);
        if (teamInfo != null){
            teamInfo.setNumber(teamInfo.getList().size());//团员人数
            teamInfo.setRoleId(this.consultMapper.findRoleById(teamInfo.getTeamCaptainId()));//团长角色id
            //获取团长的咨询量和评分
            ApiResult apiResult2 = this.consultCallShopService.findScore(teamInfo.getTeamCaptainId());
            VoScore voScore = gson.fromJson(gson.toJson(apiResult2.getData()),VoScore.class);
            if (voScore != null){
                teamInfo.setInquiry(voScore.getConsultNum());//设置咨询量
                teamInfo.setScore(voScore.getAverageScore());//设置评分
            }
        }
        apiResult.setData(teamInfo);
        return apiResult;
    }

    /**
     * 根据医生id查询医生详情
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult findDoctorInfo(Long userId, HttpServletRequest request) {
        RespDoctorInfo info = this.consultMapper.findDoctorInfoById(userId);
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(info);
        return apiResult;
    }

    /**
     * 查询已绑定的管理机构（卫健委、乡干部、离退休干部）
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult getBindManage(Long userId, HttpServletRequest request) {
        ApiResult apiResult;
        //查询已绑定的管理机构ID和角色（卫健委、乡干部、离退休干部）
        List<Map> idsMap = this.consultMapper.getBindManageIds(userId);

        List<RespDoctorOrTeam> list = new ArrayList<>();
        if (idsMap!=null && idsMap.size()!=0){
            for (Map map:idsMap){
                Long doctorId = (Long) map.get("doctorId");
                String roleName = (String) map.get("roleName");
                RespDoctorOrTeam  respDoctorOrTeam = new RespDoctorOrTeam();
                //查询这个医生有没有创建的未结束的团队
                V2Team team = this.teamMapper.findTeamByDoctorId(doctorId);
                if (team == null){
                    //查询医生信息
                    respDoctorOrTeam = this.teamMapper.findDoctorInfo(doctorId);
                    respDoctorOrTeam.setInfoType("1");//1-个人信息 2-团队信息
                }else {
                    //查询团队信息
                    respDoctorOrTeam = this.teamMapper.findDoctorTeamInfo(doctorId,userId);
                    respDoctorOrTeam.setInfoType("2");//1-个人信息 2-团队信息
                }
                respDoctorOrTeam.setDoctorId(doctorId);
                respDoctorOrTeam.setRoleName(roleName);
                list.add(respDoctorOrTeam);
            }
        }

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }

    /**
     * 查询有没有解绑资格
     *
     * @param userId
     * @param doctorId
     * @param request
     * @return
     */
    @Override
    public ApiResult findUntying(Long userId, Long doctorId, HttpServletRequest request) {
        ApiResult apiResult;
        //判断用户是否有未到期的订单
        int s = this.consultMapper.findOrderById(userId, doctorId);
        if (s > 0) {//还有未完成的订单 不能解绑
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(false);
            return apiResult;
        }
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(true);
        return apiResult;
    }

    /**
     * 查询用户的头像和昵称
     * @param userId
     * @param request
     * @return
     */
    @Override
    public ApiResult findUserInfo(Long userId, HttpServletRequest request) {
        List<RespInfo> respInfos = new ArrayList<>();
        RespInfo respInfo = new RespInfo();
        respInfo.setUserId(userId);
        respInfos.add(respInfo);
        ApiResult apiResult = consultCallUserService.getInfo(gson.toJson(respInfos));

        List<Map> respInfoList = null;
        if (!"".equals(apiResult.getData())){
            respInfoList = (List<Map>) apiResult.getData();
        }
        RespUserInfo info = new RespUserInfo();
        info.setNickName(String.valueOf(respInfoList.get(0).get("nickname")));
        info.setName(String.valueOf(respInfoList.get(0).get("name")));
        info.setHeadUrl(String.valueOf(respInfoList.get(0).get("headUrl")));

        ApiResult apiResult1 = ApiResult.SUCCESS();
        apiResult1.setData(info);
        return apiResult1;
    }


}
