package com.cj.sim.hx.easemob;

import com.cj.common.utils.hx.IMUtil;
import com.cj.common.utils.hx.api.impl.EasemobIMUsers;
import io.swagger.client.model.NewPassword;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by easemob on 2017/3/21.
 */
public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Test
    public void createUser() {
       /* RegisterUsers users = new RegisterUsers();
       // User user1 = new User().username("aaa123456" + new Random().nextInt(500)).password("123456");
        User user = new User().username("b1").password("123456");
        users.add(user);
        //users.add(user1);

         Object result = easemobIMUsers.createNewIMUserSingle(users);
         if (result != null){
             logger.info(result.toString());
             Assert.assertNotNull(result);

             Gson gson = JsonUtil.gson;
             try {
                 RespUser respUser = gson.fromJson(result.toString(), RespUser.class);
                 Entities entitie = respUser.getEntities().get(0);

                 //用户是否已激活，“true”已激活，“false“封禁，封禁需要通过解禁接口进行解禁，才能正常登录
                 if (entitie.getActivated()) {
                     //环信ID 存入DB
                     String hxId = entitie.getUsername();

                 } else {
                     //用户被封禁
                     System.out.println("用户被封禁");
                 }

             }catch (Exception e){
                 //注册失败
                 logger.info(result.toString());
                 System.out.println("注册失败");
             }

         }else {
             System.out.println("注册失败");
         }*/


        IMUtil imUtil = new IMUtil();
        boolean isOk = imUtil.createUser("a123", "123456");
        System.out.println(isOk);

    }

    @Test
    public void getUserByName() {
        String userName = "stringa";
        Object result = easemobIMUsers.getIMUserByUserName(userName);
        logger.info(result.toString());
    }

    @Test
    public void gerUsers() {
        Object result = easemobIMUsers.getIMUsersBatch(5L, null);
        logger.info(result.toString());
    }

    @Test
    public void changePassword() {
        String userName = "stringa";
        NewPassword psd = new NewPassword().newpassword("123");
        Object result = easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, psd);
        logger.info(result.toString());
    }

    @Test
    public void getFriend() {
        String userName = "stringa";
        Object result = easemobIMUsers.getFriends(userName);
        logger.info(result.toString());
    }
}
