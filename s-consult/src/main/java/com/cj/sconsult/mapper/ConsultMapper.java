package com.cj.sconsult.mapper;

import com.cj.core.entity.UserInfo;
import com.cj.core.domain.OldPager;
import com.cj.sconsult.domain.RespConsultant;
import com.cj.sconsult.entity.RespDoctorInfo;
import com.cj.sconsult.entity.VoEvaluate;
import com.cj.sconsult.entity.VoUserBindDoctor;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
public interface ConsultMapper {

    //分页查询咨询员
    List<RespConsultant> findPageConsultant(OldPager oldPager);

    //计数
    int findPageConsultantCount();

    //查询医生是 专家 还是家庭医生
    Long findRoleById(Long doctorId);

    //判断是否有绑定资格
    int findHaveBindPower(@Param("userId") Long userId, @Param("docterType") String docterType);

    //添加申请绑定记录
    int insertBindData(VoUserBindDoctor userBindDoctor);

    //解绑 0-已解绑  1-已绑定 2-绑定中，待同意  3-被拒绝
    int updateBindState1(@Param("userId")Long userId, @Param("doctorId")Long doctorId,
                       @Param("state")String s, @Param("date")Date date);

    //同意或拒绝绑定 0-已解绑  1-已绑定 2-绑定中，待同意  3-被拒绝
    int updateBindState2(@Param("userId")Long userId, @Param("doctorId")Long doctorId,
                         @Param("state")String s, @Param("date")Date date);

    //查询是否有问诊资格 返回身份证地址
    String getInterrogationQualifications(Long userId);

    //对医生评分
    int insertEvaluate(VoEvaluate evaluate);

    //修改评分
    int updateEvaluate(@Param("evaluateId")Long evaluateId, @Param("score")Double score);

    //查询已绑定的家庭医生或专家
    Long getDoctorById(@Param("userId")Long userId, @Param("type")String type);

    //查询医生绑定的患者列表
    List<UserInfo> findBindPatientList(OldPager oldPager);

    //计数
    int findBindPatientListCount(OldPager oldPager);

    //删除加好友记录
    int deleteBindData(Integer userBindDoctorId);

    //根据用户id查询是否已经绑定了
    int findBindNumById(@Param("userId") Long userId, @Param("type") String s);

    //添加绑定记录
    int addBindData(@Param("userId")Long userId, @Param("doctorId")Long doctorId, @Param("type")String s, @Param("date")Date nowDate);

    //解绑操作
    int relieveDoctor(@Param("userId")Long userId, @Param("doctorId")Long doctorId, @Param("date") Date date);

    //根据手机号或姓名搜索关注端角色的信息
    List<RespDoctorInfo> findDoctorInfo(@Param("parameter")String parameter);

    //根据医生id查询医生详情
    RespDoctorInfo findDoctorInfoById(@Param("userId")Long userId);

    //查询已绑定的管理机构ID和角色（卫健委、乡干部、离退休干部）
    List<Map> getBindManageIds(@Param("userId")Long userId);

    //判断用户是否有未到期的订单
    int findOrderById(@Param("userId")Long userId, @Param("doctorId")Long doctorId);

    //查询这个群里所有的人
    List<Long> findPersonListByTeamId( @Param("teamId") Long teamId);

    //判断是否有家庭服务包
    int findOrderByIdAndType(@Param("userId") Long userId, @Param("type")int i);

    //查询患者姓名
    String findUserNameById(@Param("userId")Long userId);
}
