package com.cj.sim.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 描述:chat_message表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2019-02-28
 */
@ApiModel(value = "ChatMessage 聊天消息实体类")
public class ChatMessage {
    /**
     * 消息记录表
     */
    @ApiModelProperty(name = "id",value = "消息id",dataType = "Long")
    private Long id;

    /**
     * 环信返回的消息id
     */
    @ApiModelProperty(name = "msgId",value = "环信的消息id",dataType = "String")
    private String msgId;

    /**
     * 发送时间
     */
    @ApiModelProperty(name = "timestamp",value = "发送时间",dataType = "Date")
    private Date timestamp;

    /**
     * 消息方向
     */
    @ApiModelProperty(name = "direction",value = "消息方向",dataType = "String")
    private String direction;

    /**
     * 发送人username
     */
    @ApiModelProperty(name = "fromUser",value = "发送人username",dataType = "String")
    private String fromUser;

    /**
     * 接收人username
     */
    @ApiModelProperty(name = "toUser",value = "接收人username",dataType = "String")
    private String toUser;

    /**
     * 消息内容或本地文件路径
     */
    @ApiModelProperty(name = "msg",value = "消息内容或本地文件路径",dataType = "String")
    private String msg;

    /**
     * 消息类型
     */
    @ApiModelProperty(name = "type",value = "消息类型",dataType = "String")
    private String type;

    /**
     * 图片名称，带图片格式
     */
    @ApiModelProperty(name = "fileName",value = "图片名称",dataType = "String")
    private String fileName;

    /**
     * 环信返回的图片属性
     */
    @ApiModelProperty(name = "uuid",value = "环信返回的图片属性",dataType = "String")
    private String uuid;

    /**
     * 环信返回的图片属性
     */
    @ApiModelProperty(name = "secret",value = "环信返回的图片属性",dataType = "String")
    private String secret;

    /**
     * 消息记录表
     * @return id 消息记录表
     */
    public Long getId() {
        return id;
    }

    /**
     * 消息记录表
     * @param id 消息记录表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 环信返回的消息id
     * @return msg_id 环信返回的消息id
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 环信返回的消息id
     * @param msgId 环信返回的消息id
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    /**
     * 发送时间
     * @return timestamp 发送时间
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 发送时间
     * @param timestamp 发送时间
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 消息方向
     * @return direction 消息方向
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 消息方向
     * @param direction 消息方向
     */
    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    /**
     * 发送人username
     * @return from_user 发送人username
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * 发送人username
     * @param fromUser 发送人username
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser == null ? null : fromUser.trim();
    }

    /**
     * 接收人username
     * @return to_user 接收人username
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * 接收人username
     * @param toUser 接收人username
     */
    public void setToUser(String toUser) {
        this.toUser = toUser == null ? null : toUser.trim();
    }

    /**
     * 消息内容或本地文件路径
     * @return msg 消息内容或本地文件路径
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 消息内容或本地文件路径
     * @param msg 消息内容或本地文件路径
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * 消息类型
     * @return type 消息类型
     */
    public String getType() {
        return type;
    }

    /**
     * 消息类型
     * @param type 消息类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 图片名称，带图片格式
     * @return file_name 图片名称，带图片格式
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 图片名称，带图片格式
     * @param fileName 图片名称，带图片格式
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 环信返回的图片属性
     * @return uuid 环信返回的图片属性
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 环信返回的图片属性
     * @param uuid 环信返回的图片属性
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 环信返回的图片属性
     * @return secret 环信返回的图片属性
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 环信返回的图片属性
     * @param secret 环信返回的图片属性
     */
    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }
}