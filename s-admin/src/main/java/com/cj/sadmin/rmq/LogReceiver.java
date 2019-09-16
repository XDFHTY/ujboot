package com.cj.sadmin.rmq;

import com.cj.core.util.JsonUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.entity.OperateLog;
import com.cj.sadmin.service.LogService;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 日志消息接受者 Receiver 注解方式接受消息
 */

@Component
public class LogReceiver {


    Gson gson = JsonUtil.gson;

    @Resource
    private LogService logService;

//    @RabbitListener(queues = "queueLog2")
//    public void process(Channel channel, Message message) throws IOException {
//
//        try {
//            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//
//            logService.addLog(gson.fromJson(new String(message.getBody()),OperateLog.class));
//        } catch (IOException e) {
//            e.printStackTrace();
//            //丢弃这条消息
//            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
//            System.out.println("==========接收日志消息失败");
//        }
//
//    }

    @RabbitListener(queues = "queueLog2")
    public void process(Message message) {
        System.out.println("=======================写入操作日志");
        logService.addLog(gson.fromJson(new String(message.getBody()),OperateLog.class));
    }
}
