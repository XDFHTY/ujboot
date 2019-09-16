package com.cj.sbasic.service;

import com.cj.core.domain.OldPager;
import com.cj.sbasic.domain.Msg;
import com.cj.sbasic.domain.MsgSendReq;

import javax.servlet.http.HttpServletResponse;

public interface BasicMsgService {
    /**
     * 分页查询消息列表
     * @param oldPager
     * @return
     */
    OldPager getMsgPage(OldPager oldPager);

    /**
     * 删除
     * @param id
     */
    int deleteMsgById(Long id);

    /**
     * 导出消息列表
     * @param oldPager
     * @return
     */
    String exportMsgs(OldPager oldPager, HttpServletResponse response);

    /**
     * 新增一条消息记录
     * @param msg
     */
    int addMsg(Msg msg);

    /**
     * 根据消息id获取到消息
     * @param id
     * @return
     */
    Msg getMsgById(Long id);

    /**
     * 推送消息
     * @return
     */
    boolean sendMsg(MsgSendReq msgSendReq);
}
