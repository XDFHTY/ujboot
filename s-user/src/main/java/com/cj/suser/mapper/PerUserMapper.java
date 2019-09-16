package com.cj.suser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.domain.Pager;
import com.cj.core.entity.User;
import com.cj.core.entity.UserEquipment;
import com.cj.core.v2entity.V2Order;
import com.cj.suser.domain.BRModel;
import com.cj.suser.domain.FIModel;
import com.cj.suser.domain.FpuModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/6/28 13:53
 **/
public interface PerUserMapper extends BaseMapper<User> {

    /**
     * 新增用户信息
     * @param name
     * @param sex
     * @return
     */
    int insertUserInfo(@Param("id") Long id ,
                       @Param("name") String name ,
                       @Param("sex") String sex);

    /**
     * 逻辑删除用户
     * @param id
     * @return
     */
    int logDeleteUser(Long id);

    /**
     * 修改密码
     * @param id
     * @param userPass
     * @return
     */
    int updatePass(@Param("id") Long id ,
                   @Param("userPass") String userPass);

    /**
     * 分页查询个人端用户列表
     * @param pager
     * @return
     */
    List<FpuModel> findUser(Pager pager);

    /**
     * 分页查询用户订单
     * @param pager
     * @return
     */
    Pager findOrder(Pager pager);

    /**
     * 模糊查询医生
     * @param current
     * @param parameter
     * @return
     */
    List<Map<String,String>> findPeople(@Param("current") int current,
                                  @Param("parameter") String parameter);
    /**
     * 分页查询个人端用户列表
     * @param map
     * @return
     */
    List<FpuModel> find(Map<String,String> map);

    /**
     * 根据id查询设备绑定记录
     * @param userId
     * @return
     */
    List<UserEquipment> findEquipmentById(Long userId);
    /**
     * 根据id查询疾病记录
     * @param userId
     * @return
     */
    List<FIModel> findFIModelById(Long userId);
    /**
     * 根据id查询绑定医生记录
     * @param userId
     * @return
     */
    List<BRModel> findBRModelById(Long userId);

    /**
     * 根据id查询服务包记录
     * @param userId
     * @return
     */
    List<V2Order> findOrderModById(Long userId);
}
