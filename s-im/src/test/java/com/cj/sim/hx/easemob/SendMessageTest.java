package com.cj.sim.hx.easemob;


import com.cj.common.utils.hx.IMUtil;
import com.cj.common.utils.hx.api.impl.EasemobSendMessage;
import org.junit.Test;

import java.util.Map;

/**
 * Created by easemob on 2017/3/22.
 */
public class SendMessageTest {
    private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();

    @Test
    public void sendText() {
       /* Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg("helloword");
        UserName userName = new UserName();
        //消息接受者
        userName.add("aaaa12345685");
        Map<String,Object> ext = new HashMap<>();
        ext.put("test_key","test_value");
        //from 表示消息发送者
        msg.from("b1217").target(userName).targetType("users").msg(msgContent).ext(ext);
        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result);*/

        boolean b = new IMUtil().sendText("b1", "a1", "你好");
        if (b) System.out.println("发送成功");
        else System.out.println("发送失败");
    }

    @Test
    public void sendImage() {

        //首先把图片上传至 环信服务器
        /*RespUploadImgFile respUploadImgFile = new IMUtil().uploadImgFile("D:/1.jpg");
        Map<String, String> imgMap = respUploadImgFile.getEntities().get(0);


        Msg msg = new Msg();
        ImageMsgContent msgContent = new ImageMsgContent();


        msgContent.url(imgMap.get("uuid"))
                .secret(imgMap.get("share-secret"))
                .filename("1.jpg")
                .size(new ImageMsgContent.Size(300, 400))
                .type(MsgContent.TypeEnum.IMG);
        UserName userName = new UserName();
        userName.add("a1");
        msg.from("b1").target(userName).targetType("users").msg(msgContent);
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result.toString());*/


        Map map = new IMUtil().sendImage("b1","a1","d:/11.gif","11.gif",500,500);
        if (map!=null){
            System.out.println("发送成功");
            System.out.println(map.toString());
        }else{
            System.out.println("发送失败");
        }
    }


}
