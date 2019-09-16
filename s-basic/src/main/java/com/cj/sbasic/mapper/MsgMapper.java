package com.cj.sbasic.mapper;

import com.cj.core.domain.OldPager;
import com.cj.sbasic.domain.Msg;

import java.util.List;

/**
* Created by Mybatis Generator 2019/03/25
*/
public interface MsgMapper {
    /**
     *通过id删除
     */
    int deleteByPrimaryKey(Long msgId);

    /**
     *插入
     */
    int insert(Msg record);

    /**
     *动态插入
     */
    int insertSelective(Msg record);

    /**
     *通过id查找
     */
    Msg selectByPrimaryKey(Long msgId);

    /**
     *动态更新
     */
    int updateByPrimaryKeySelective(Msg record);
    int updateByPrimaryKeyWithBLOBs(Msg record);

    /**
     *通过主键更新
     */
    int updateByPrimaryKey(Msg record);

    /**
     * 分页查询消息列表
     * @param oldPager
     * @return
     */
    List<Msg> getMsgPage(OldPager oldPager);

    /**
     * 记录条数
     * @param oldPager
     * @return
     */
    int getMsgPageCount(OldPager oldPager);
}