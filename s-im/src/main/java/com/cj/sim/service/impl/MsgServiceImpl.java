package com.cj.sim.service.impl;

import com.cj.common.domain.RespInfo;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.sim.comm.TokenUtil;
import com.cj.sim.entity.ChatMessage;
import com.cj.sim.mapper.ChatMessageMapper;
import com.cj.sim.service.ImCallFileService;
import com.cj.sim.service.ImCallUserService;
import com.cj.sim.service.MsgService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天记录 业务层
 * Created by XD on 2019/2/28.
 */
@Service
//@RefreshScope
public class MsgServiceImpl implements MsgService {


    @Value("${config.hx.ORG_NAME}")
    private String ORG_NAME;

    @Value("${config.hx.APP_NAME}")
    private String APP_NAME;

    @Value("${file.host}")
    private String FILE_HOST;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChatMessageMapper chatMessageMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ImCallUserService imCallUserService;

    @Autowired
    private ImCallFileService imCallFileService;

    private static final Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);


    /**
     * 存放聊天记录
     * @param chatMessageList
     * @return
     */
    @Override
    public int insert(List<ChatMessage> chatMessageList, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("ORG_NAME",ORG_NAME);
        map.put("APP_NAME",APP_NAME);

        if (chatMessageList != null && chatMessageList.size()!=0){
            for (ChatMessage chatMessage:chatMessageList){
                //如果是图片消息
                if ("img".equals(chatMessage.getType())){
                    //判断msgId是否在数据库中存在
                    int i = chatMessageMapper.findCountByMsgId(chatMessage.getMsgId());
                    if (i==0){//数据库中不存在  去环信下载图片
                        //设置 图片的msg 为图片的本地路径
                        chatMessage.setMsg("hx_" + chatMessage.getUuid() + ".jpg");

                        //开启异步线程 去 环信服务器 下载图片 保存到本地
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //downloadFile(chatMessage.getUuid(),header);
                                    map.put("uuid",chatMessage.getUuid());
                                    //restTemplate.postForEntity(FILE_HOST + "api/file/hxUploads",map, ApiResult.class);
                                    imCallFileService.downloadFile(map);
                                } catch (Exception e) {
                                    logger.info("图片从环信下载失败");
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        }

        return chatMessageMapper.insertByList(chatMessageList);
    }








    /**
     * 查询聊天记录
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getMsg(OldPager oldPager) {
        //查询聊天记录总条数
        oldPager.setRecordTotal(this.chatMessageMapper.findMsgCount(oldPager));

        List<ChatMessage> msg = this.chatMessageMapper.findMsg(oldPager);

        if (msg != null && msg.size()!=0){
            for (ChatMessage info:msg){
                if ("img".equals(info.getType())){
                    info.setMsg(FILE_HOST + "s-file/msg_img/" + info.getMsg());
                }
            }
        }

        oldPager.setContent(msg);
        return oldPager;
    }


    /**
     * 发送透传消息
     * map msg 消息内容
     *     toUser 消息接收者
     *     fromUser 消息发送者
     * @param map
     * @return
     */
    public Object sendCmdMsg(Map map) {
        String token = TokenUtil.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);

        String url = "http://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/messages";

        String toUser = (String) map.get("toUser");
        String[] s = {toUser};
        Map map1 = new HashMap();
        map1.put("type","cmd");
        map1.put("action",map.get("msg"));

        Map reqMap = new HashMap();
        reqMap.put("target_type","users");
        reqMap.put("target",s);
        reqMap.put("msg",map1);
        reqMap.put("from",map.get("fromUser"));

        Gson gson = JsonUtil.gson;


        return  restTemplate.postForObject(
                url,
                new HttpEntity<String>(gson.toJson(reqMap), headers)
                , Object.class);
    }

    /**
     * 根据一个环信id 查询 与他聊过天的人
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getUserListByHxId(OldPager oldPager) {
        String hxId = (String) oldPager.getParameters().get("hxId");
        //根据一个环信id 查询 与他聊过天的人
        List<Map> maps = this.chatMessageMapper.getUserListByHxId(oldPager);
        //计数
        oldPager.setRecordTotal(this.chatMessageMapper.getUserListByHxIdCount(oldPager));

        if (maps != null && maps.size()!=0){
            List<RespInfo> respInfos = new ArrayList<>();
            for (Map map:maps){
                RespInfo respInfo = new RespInfo();
                String toUser = (String) map.get("toUser");
                String fromUser = (String) map.get("fromUser");
                //获取对方的id
                if (hxId.equals(fromUser)){
                    respInfo.setUserId(Long.valueOf(toUser));
                }else {
                    respInfo.setUserId(Long.valueOf(fromUser));
                }
                respInfos.add(respInfo);
            }

            ApiResult apiResult = imCallUserService.getInfo(JsonUtil.gson.toJson(respInfos));
            List<Map> respInfoList = (List<Map>) apiResult.getData();
            //双重for循环 设置 头像昵称
            for (RespInfo respInfo:respInfos){
                for (Map map:respInfoList){
                    //long s = ((Double) map.get("userId")).longValue();
                    long s = ((Integer) map.get("userId")).longValue();
                    if (s == respInfo.getUserId()){
                        respInfo.setNickname(String.valueOf(map.get("nickname")));
                        respInfo.setHeadUrl(String.valueOf(map.get("headUrl")));
                        respInfo.setName(String.valueOf(map.get("name")));
                    }
                }
            }
            oldPager.setContent(respInfos);
        }


        return oldPager;
    }

    /**
     * 获取群聊的记录
     * @param pager
     * @return
     */
    @Override
    public OldPager getGroupMsg(OldPager pager) {
        //查询聊天记录总条数
        pager.setRecordTotal(this.chatMessageMapper.findGroupMsgCount(pager));

        List<ChatMessage> msg = this.chatMessageMapper.findGroupMsg(pager);

        if (msg != null && msg.size()!=0){
            for (ChatMessage info:msg){
                if ("img".equals(info.getType())){
                    info.setMsg(FILE_HOST + "s-file/msg_img/" + info.getMsg());
                }
            }
        }

        pager.setContent(msg);
        return pager;
    }

    /**
     * 查询有没有资格退款
     * 群：
     * 用医生id和用户id 查询所有会话集合 拿到所有的hxGroupId
     * 用GroupId 和时间段查询 是否有消息记录 判断发送者的角色是否为医生
     * 个人：
     * 根据医生id和用户id查询这个时间段有没有聊天记录
     */
    @Override
    public Boolean getRefundEligibility(Long userId, Long doctorId, String startDate, String endDate) {

        //群：
        //用医生id和用户id 查询所有会话集合 拿到所有的hxGroupId
        List<String> hxGroupIds = chatMessageMapper.findGroupConversionById(userId,doctorId);
        if (hxGroupIds != null && hxGroupIds.size()!=0){
            for (String hxGroupId:hxGroupIds){
                //用GroupId 和时间段查询 是否有消息记录
                List<Long> doctorIds = this.chatMessageMapper.findMsgByGroupId(hxGroupId,startDate,endDate);
                if (doctorIds!=null && doctorIds.size()!=0){
                    //判断发送者的角色是否为医生
                    for (Long doctorId2:doctorIds){
                        Long roleId = this.chatMessageMapper.findRoleIdByUserId(doctorId2);
                        if (roleId != 31){//发送方不是用户
                            return false;
                        }
                    }
                }

            }
        }

        //个人：
        //根据医生id和用户id查询这个时间段有没有聊天记录,拿到 from_user
        List<Long> doctorIds = this.chatMessageMapper.findMsgByIds(userId,doctorId,startDate,endDate);
        if (doctorIds!=null && doctorIds.size()!=0){
            //判断发送者的角色是否为医生
            for (Long doctorId2:doctorIds){
                Long roleId = this.chatMessageMapper.findRoleIdByUserId(doctorId2);
                if (roleId != 31){//发送方不是用户
                    return false;
                }
            }
        }
        return true;
    }
}
