package com.cj.sim.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:conversation表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2019-03-04
 */
@ApiModel(value = "Conversation 会话实体类")
@Data
public class Conversation {
    /**
     * 会话列表
     */
    @ApiModelProperty(name = "conversationId",value = "会话id",dataType = "Long")
    private Long conversationId;

    /**
     * 发送者
     */
    @ApiModelProperty(name = "fromUser",value = "创建会话者",dataType = "String")
    private String fromUser;

    /**
     * 接收者
     */
    @ApiModelProperty(name = "toUser",value = "被创建会话者",dataType = "String")
    private String toUser;

    /**
     * 结束状态 0-已结束 1-未结束
     */
    @ApiModelProperty(name = "endState",value = "结束状态 0-已结束 1-未结束",dataType = "String")
    private String endState;

    /**
     * 发起时间
     */
    @ApiModelProperty(name = "startDate",value = "发起时间",dataType = "Date")
    private Date startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endDate",value = "结束时间",dataType = "Date")
    private Date endDate;

    /**
     * 删除状态 0-已删除 1-正常
     */
    @ApiModelProperty(name = "deleteState",value = "删除状态 0-已删除 1-正常",dataType = "String")
    private String deleteState;


    /**
     * 对方的昵称
     */
    @ApiModelProperty(name = "nickName",value = "对方的昵称",dataType = "String")
    private String nickName;

    /**
     * 对方的姓名
     */
    @ApiModelProperty(name = "name",value = "对方的姓名",dataType = "String")
    private String name;

    /**
     * 对方的头像地址
     */
    @ApiModelProperty(name = "headUrl",value = "对方的头像地址",dataType = "String")
    private String headUrl;

    /**
     * 咨询对象类型 1-医生 2-咨询员
     */
    @ApiModelProperty(name = "doctorType",value = "咨询对象类型 1-医生 2-咨询员",dataType = "String")
    private String type;


    @ApiModelProperty(name = "bindType",value = "是否为绑定关系 0-未绑定  1-已绑定",dataType = "String")
    private String bindType;

    /**
     * 会话类型
     */
    @ApiModelProperty(name = "conversationType",value = "会话类型 1-单独聊天  2-群聊",dataType = "String")
    private String conversationType;

    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodType",value = "商品类型",dataType = "String")
    private String goodType;

    /**
     * 团队id
     */
    @ApiModelProperty(name = "teamId",value = "团队Id",dataType = "Long")
    private Long teamId;

    /**
     * 团长id
     */
    @ApiModelProperty(name = "teamCaptainId",value = "团长id",dataType = "Long")
    private Long teamCaptainId;
    /**
     * 会话列表
     * @return conversation_id 会话列表
     */
    public Long getConversationId() {
        return conversationId;
    }

    /**
     * 会话列表
     * @param conversationId 会话列表
     */
    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * 发送者
     * @return from_user 发送者
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * 发送者
     * @param fromUser 发送者
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser == null ? null : fromUser.trim();
    }

    /**
     * 接收者
     * @return to_user 接收者
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * 接收者
     * @param toUser 接收者
     */
    public void setToUser(String toUser) {
        this.toUser = toUser == null ? null : toUser.trim();
    }

    /**
     * 结束状态 0-已结束 1-未结束
     * @return end_state 结束状态 0-已结束 1-未结束
     */
    public String getEndState() {
        return endState;
    }

    /**
     * 结束状态 0-已结束 1-未结束
     * @param endState 结束状态 0-已结束 1-未结束
     */
    public void setEndState(String endState) {
        this.endState = endState == null ? null : endState.trim();
    }

    /**
     * 发起时间
     * @return start_date 发起时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 发起时间
     * @param startDate 发起时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     * @return end_date 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束时间
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 删除状态 0-已删除 1-正常
     * @return delete_state 删除状态 0-已删除 1-正常
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * 删除状态 0-已删除 1-正常
     * @param deleteState 删除状态 0-已删除 1-正常
     */
    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState == null ? null : deleteState.trim();
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}