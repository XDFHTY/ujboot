package com.cj.sshop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.exception.MyException;
import com.cj.core.v2entity.V2Give;
import com.cj.core.v2entity.V2Good;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.domain.VoFindGood;
import com.cj.sshop.mapper.V2GiveMapper;
import com.cj.sshop.mapper.V2GoodMapper;
import com.cj.sshop.mapper.V2OrderMapper;
import com.cj.sshop.service.GoodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    @Resource
    private V2GoodMapper goodMapper;

    @Resource
    private V2OrderMapper orderMapper;


    @Resource
    private V2GiveMapper giveMapper;


//            26  超级管理员            系统管理员            1
//            27  运营管理员            系统管理员            1
//            28  咨询员              系统管理员            1
//            29  专科医生             用户管理员            1
//            30  家庭医生             用户管理员            1
//            31  用户               用户               1
//            32  营养师              用户管理员            1
//            33  乡干部              用户管理员            1
//            34  卫健委干部            用户管理员            1
//            35  离退休干部            用户管理员            1
//            36  护士               助手               1
//            37  健康管理师            助手               1

    //1-健康管理 ，2-肾病管理 ，3-健康咨询 ，4-肾病咨询 ，5-营养咨询 ，6-其他咨询
    @Override
    @Transactional
    public int createGoods(long id, long roleId) {

        V2Good good = new V2Good();
        good.setSellerId(id);
        good.setGoodPrice(new BigDecimal(1000));
        good.setGoodMsg("没有描述信息");

        int i = 0;

        switch (roleId + "") {
            case "29":

                good.setGoodName("肾病咨询");
                good.setGoodType("4");
                good.setValidDate(2);
                i = goodMapper.insert(good);

                break;
            case "30":
                good.setGoodName("健康咨询");
                good.setGoodType("3");
                good.setValidDate(2);
                i = goodMapper.insert(good);
                break;
            case "32":
                good.setGoodName("营养咨询");
                good.setGoodType("5");
                good.setValidDate(2);
                i = goodMapper.insert(good);
                break;
            case "33":
            case "34":
            case "35":
                good.setGoodName("其他咨询");
                good.setGoodType("6");
                good.setValidDate(2);
                good.setGoodPrice(new BigDecimal(0));
                i = goodMapper.insert(good);
                break;


        }
        return i;
    }

    @Override
    public V2Good findGoodById(HttpServletRequest request) {
        long id = Long.parseLong(request.getHeader("id"));

        return goodMapper.findGoodById(id);
    }

    @Override
    @Transactional
    public int updateGoodPrice(V2Good good) {
        good.setGoodType(null);
        good.setValidDate(null);

        return goodMapper.updateById(good);
    }

    @Override
    public VoFindGood findGood(long id, long doctorId, String goodType) {
        V2Order order = null;
        V2Order order2 = null;
        V2Good good = null;

        List<V2Give> v2Gives = giveMapper.find0Pay(id);
        int num = 0;
        for (V2Give v2Give : v2Gives){
            if (goodType.equals(v2Give.getGoodType())){
                num = v2Give.getGiveNum();
            }
        }

        switch (goodType) {
            case "1":
                order = orderMapper.findOrderByGoodType(id, 0, "1");

                if (order == null) {
                    good = goodMapper.findGood(0, "1");
                } else if (order != null) {
                    throw new MyException(ApiResult.SUCCESS_CODE, "不能重复购买健康管理服务");
                }
                break;
            case "2":
                order = orderMapper.findOrderByGoodType(id, 0, "2");
                if (order == null) {
                    good = goodMapper.findGood(0, "2");
                } else if (order != null) {
                    throw new MyException(ApiResult.SUCCESS_CODE, "不能重复购买肾病管理服务");
                }
                break;
            case "3":
            case "6":
                //查詢健康管理有效訂單
                order = orderMapper.findOrderByGoodType(id, 0, "1");
                if (order == null) {
                    good = goodMapper.findGood(0, "1");
                }else {
                    //查询有效的咨询订单
                    order2 = orderMapper.findOrderByGoodType(id, doctorId, goodType);
                    if (order2==null){
                        good = goodMapper.findGood(doctorId, goodType);
                    }

                }
                break;
            case "4":
            case "5":

                order = orderMapper.findOrderByGoodType(id, 0, "2");
                if (order == null) {
                    good = goodMapper.findGood(0, "2");
                }else {
                    //查询对应订单
                    order2 = orderMapper.findOrderByGoodType(id, doctorId, goodType);
                    if (order2==null){
                        good = goodMapper.findGood(doctorId, goodType);
                    }

                }
                break;

        }
        VoFindGood voFindGood = new VoFindGood();
        voFindGood.setGood(good);
        voFindGood.setNum(num);
        return voFindGood;
    }

    //=============================================后端业务=====================
    @Override
    public Pager findAllGoods(Pager pager) {
        pager.setRecords(goodMapper.findAllGoods(pager));
        return pager;
    }


}
