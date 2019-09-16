package com.cj.common.utils.hx;



import com.cj.common.utils.hx.api.impl.EasemobChatGroup;
import com.cj.common.utils.hx.api.impl.EasemobFile;
import com.cj.common.utils.hx.api.impl.EasemobIMUsers;
import com.cj.common.utils.hx.api.impl.EasemobSendMessage;
import com.cj.common.utils.hx.comm.OrgInfo;
import com.cj.common.utils.hx.entity.*;
import com.cj.core.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.client.model.*;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 环信  工具类
 * Created by XD on 2019/2/28.
 */
public class IMUtil {




    private static final Logger logger = LoggerFactory.getLogger(IMUtil.class);

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();

    private EasemobFile easemobFile = new EasemobFile();

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

    /**
     * 创建用户
     * userName 环信唯一标识
     * password 环信登陆密码
     *
     * 返回 true 发送成功    false 发送失败
     */
    public boolean createUser(String userName, String password) {
        RegisterUsers users = new RegisterUsers();
        User user = new User().username(userName).password(password);
        users.add(user);

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
                    //环信ID 存入DB
                    String hxId = entitie.getUsername();
                    logger.info("环信注册成功，id==  " + hxId);
                    return true;
                } else {
                    //用户被封禁
                    logger.info("用户被封禁");
                    return false;
                }

            }catch (Exception e){
                //注册失败
                logger.info("环信注册失败");
                return false;
            }

        }
        logger.info("环信注册失败");
        return false;
    }


    /**
     * 发送文字消息
     * sender 消息发送者 环信username
     * accepter 消息接收者 环信usernam
     * txt  发送的内容
     */
    public boolean sendText(String sender,String accepter,String txt) {
        Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg(txt);
        UserName userName = new UserName();
        //消息接受者
        userName.add(accepter);
        //扩展字段
        Map<String,Object> ext = new HashMap<>();
        ext.put("test_key","test_value");
        //from 表示消息发送者    targetType 发送的目标类型；users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
        //支持扩展字段，通过 ext 属性，APP 可以发送自己专属的消息结构。
        msg.from(sender).target(userName).targetType("users").msg(msgContent).ext(ext);
        logger.info(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);


        if (result != null){
            logger.info(result.toString());
            Gson gson = JsonUtil.gson;
            try {
                RespSendMsg respSendMsg = gson.fromJson(result.toString(),RespSendMsg.class);
                //若 消息接受者 返回的是 success 则发送成功
                if ("success".equals(respSendMsg.getData().get(accepter))) {
                    logger.info("消息发送成功");
                    return true;
                }
            }catch (Exception e){
                logger.info("消息发送失败");
                return false;
            }
        }

        logger.info("消息发送失败");
        return false;
    }


    /**
     * 图片上传
     * path 图片路径
     */
    public RespUploadImgFile uploadImgFile(String path) {
        //String path = TokenUtil.class.getClassLoader().getResource("D:/1.jpg").getPath();
        File file = new File(path);
        Object result = easemobFile.uploadFile(file);
        if (result != null){
            Assert.assertNotNull(result);
            Gson gson = JsonUtil.gson;
            try {
                logger.info("环信 图片上传成功");
                return gson.fromJson(result.toString(),RespUploadImgFile.class);
            }catch (Exception e){
                logger.info("环信 图片上传失败");
                return null;
            }
        }
        logger.info("环信 图片上传失败");
        return null;
    }


    /**
     * 发送图片 消息
     * sender 发送者
     * accepter 接收者
     * imgPath 图片路径
     * imgName 图片名称
     * width height 图片 高 宽
     *
     * 返回 Map null = 失败
     */
    public Map sendImage(String sender,String accepter,
                         String imgPath,String imgName,
                         long width,long height) {

        //首先把图片上传至 环信服务器
        RespUploadImgFile imgInfo = new IMUtil().uploadImgFile(imgPath);
        if (imgInfo != null){
            //获取返回的图片信息
            Map<String, String> imgMap = imgInfo.getEntities().get(0);

            Msg msg = new Msg();
            ImageMsgContent msgContent = new ImageMsgContent();

            //url 成功上传文件返回的UUID
            //secret 成功上传文件后返回的secret
            //fileName 文件名称
            msgContent.url("https://a1.easemob.com/"+ OrgInfo.ORG_NAME +"/"+ OrgInfo.APP_NAME +"/chatfiles/" + imgMap.get("uuid"))
                    .secret(imgMap.get("share-secret"))
                    .filename(imgName)
                    .size(new ImageMsgContent.Size(width, height))
                    .type(MsgContent.TypeEnum.IMG);
            UserName userName = new UserName();
            userName.add(accepter);
            msg.from(sender).target(userName).targetType("users").msg(msgContent);
            Object result = easemobSendMessage.sendMessage(msg);
            if (result!=null){
                logger.info(result.toString());
                Gson gson = JsonUtil.gson;
                try {
                    RespSendMsg info = gson.fromJson(result.toString(),RespSendMsg.class);
                    if ("success".equals(info.getData().get(accepter)))return imgMap;
                }catch (Exception e){
                    logger.info("图片上传失败");
                    return null;
                }
            }
        }else {
            logger.info("图片上传失败");
            return null;
        }
        return null;
    }

    /**
     * 下载图片
     *  uuid = "5cd766c0-3bc4-11e9-909c-2dc35ebab259"
     *  shareSecret = "XNdmyjvEEemhQ8GDCNmpwGIjI0Se57oC6QfoofIv1HjiHooR"
     */
    public void downloadFile(String uuid,String shareSecret) {
        //“thumbnail: true”，
        // 当服务器看到过来的请求的 header 中包括这个的时候，就会返回缩略图，否则返回原始大图。
        Boolean thumbnail = true;
        File result = (File) easemobFile.downloadFile(uuid, shareSecret, thumbnail);
        System.out.println(result);
    }



    /**
     * 创建群组
     * owner 群主
     * users 群成员
     * groupname 群组名称
     *
     * 返回 群组id
     */
    public String createGroup(String owner,List<String> users,String groupname) {

        //添加群成员
        UserName userName = new UserName();
        if (users != null && users.size()!=0){
            for (String user:users){
                userName.add(user);
            }
        }

        Group group = new Group();
        //需要设置群成员
        if (users != null && users.size()!=0){
            group.groupname(groupname)//群名称
                    .desc("")//群描述
                    ._public(true)//是否是公开群
                    .maxusers(2000)//群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000
                    .approval(false)//加入群是否需要群主或者群管理员审批
                    .owner(owner)//群组的管理员
                    .members(userName);//群成员
        }else {//不设置群成员
            group.groupname(groupname)//群名称
                    .desc("")//群描述
                    ._public(true)//是否是公开群
                    .maxusers(2000)//群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000
                    .approval(false)//加入群是否需要群主或者群管理员审批
                    .owner(owner);//群组的管理员
        }

        Object result = easemobChatGroup.createChatGroup(group);
        if (result!=null){
            Gson gson = JsonUtil.gson;
            try {
                RespCreateGroup info = gson.fromJson(result.toString(),RespCreateGroup.class);
                String groupid = info.getData().get("groupid");
                if (groupid!=null && !"".equals(groupid))return groupid;
            }catch (Exception e){
                logger.info("创建群组失败");
                System.out.println(result);
                return null;
            }
        }
        logger.info("创建群组失败");
        return null;
    }

    /**
     * 修改群名称
     * groupId 群id
     * groupName 群名称
     */
    public boolean changeGroupInfo(String groupId,String groupName) {
        ModifyGroup group = new ModifyGroup();
        group.groupname(groupName);
        Object result = easemobChatGroup.modifyChatGroup(groupId, group);
        if (result == null){
            return false;
        }
        String s = result.toString().trim();
        if (s.indexOf("error") != -1){
            System.out.println(result.toString());
            return false;
        }
        return true;
    }

    /**
     * 删除群组
     * groupId 群id
     */
    public boolean deleteGroup(String groupId) {
        Object result = easemobChatGroup.deleteChatGroup(groupId);
        if (result == null){
            return false;
        }
        String s = result.toString().trim();
        if (s.indexOf("error") != -1){
            System.out.println(result.toString());
            return false;
        }
        return true;
    }

    /**
     * 添加单个用户到群组
     */
    public boolean addUserToGroup(String groupId,String userName) {
        Object result = easemobChatGroup.addSingleUserToChatGroup(groupId,userName);
        if (result == null){
            return false;
        }
        String s = result.toString().trim();
        if (s.indexOf("error") != -1){
            System.out.println(result.toString());
            return false;
        }
        return true;
    }

    /**
     * 移除单个成员
     */
    public boolean removeUserFromGroup(String groupId,String userName) {
        Object result = easemobChatGroup.removeSingleUserFromChatGroup(groupId,userName);
        if (result == null){
            return false;
        }
        String s = result.toString().trim();
        if (s.indexOf("error") != -1){
            System.out.println(result.toString());
            return false;
        }
        return true;
    }
}
