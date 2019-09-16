package com.cj.sim.service.impl;

import com.cj.core.util.JsonUtil;
import com.cj.sim.service.HxUserService;
import com.cj.common.utils.hx.IMUtil;
import com.cj.common.utils.hx.api.impl.EasemobIMUsers;
import com.cj.common.utils.hx.entity.Entities;
import com.cj.common.utils.hx.entity.RespUser;
import com.google.gson.Gson;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 环信用户管理
 * Created by XD on 2019/3/7.
 */
@Service
public class HxUserServiceImpl implements HxUserService {

    private static final Logger logger = LoggerFactory.getLogger(IMUtil.class);

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
    /**
     * 注册环信用户
     * @param userName
     * @param password
     * @return 返回注册成功后的用户名  返回null则是注册失败
     */
    @Override
    public String register(String userName, String password) {
        //注册之前先根据id删除用户
        easemobIMUsers.deleteIMUserByUserName(userName);


        RegisterUsers users = new RegisterUsers();
        User user = new User().username(userName).password(password);
        users.add(user);

        //创建用户
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        if (result != null){
            logger.info(result.toString());
            Assert.assertNotNull(result);

            Gson gson = JsonUtil.gson;
            try {
                RespUser respUser = gson.fromJson(result.toString(), RespUser.class);
                Entities entitie = respUser.getEntities().get(0);

                //用户是否已激活，“true”已激活，“false”封禁，封禁需要通过解禁接口进行解禁，才能正常登录
                if (entitie.getActivated()) {
                    //环信ID
                    String hxId = entitie.getUsername();
                    logger.info("环信注册成功，id==  " + hxId);
                    return hxId;
                } else {
                    //用户被封禁
                    logger.info("用户被封禁");
                    return null;
                }

            }catch (Exception e){
                //注册失败
                logger.info("环信注册失败");
                return null;
            }

        }
        logger.info("环信注册失败");
        return null;
    }
}
