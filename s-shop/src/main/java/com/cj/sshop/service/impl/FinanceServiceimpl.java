package com.cj.sshop.service.impl;

import com.cj.common.mapper.PublicMapper;
import com.cj.core.entity.DoctorInfo;
import com.cj.core.v2entity.V2Order;
import com.cj.sshop.domain.VoFinanceOrder;
import com.cj.sshop.domain.VoFinanceResp;
import com.cj.sshop.mapper.V2OrderMapper;
import com.cj.sshop.service.FinanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FinanceServiceimpl implements FinanceService {

    @Resource
    private PublicMapper publicMapper;


    @Resource
    private V2OrderMapper orderMapper;

    @Override
    public List<DoctorInfo> findAllByName(String pameter) {
        return publicMapper.findAllByName(pameter);
    }

    @Override
    public VoFinanceResp findAllOrder(Date dateTime, String incomeType, long bindId) {

        List<VoFinanceOrder> voFinanceOrders = orderMapper.findAllOrder(dateTime,incomeType,bindId);
        VoFinanceResp voFinanceResp = new VoFinanceResp();
        voFinanceResp.setVoFinanceOrders(voFinanceOrders);

        BigDecimal total = BigDecimal.valueOf(0);
        BigDecimal bios = BigDecimal.valueOf(0);
        BigDecimal bind = BigDecimal.valueOf(0);
        for (VoFinanceOrder voFinanceOrder : voFinanceOrders){
            total = total.add(new BigDecimal(voFinanceOrder.getActualPay()));
            bios = bios.add(new BigDecimal((voFinanceOrder.getBiosGet())));
        }
        bind = total.subtract(bios);

        voFinanceResp.setTotalProfit(total.toString());
        voFinanceResp.setBiosProfit(bios.toString());
        voFinanceResp.setBindProfit(bind.toString());
        return voFinanceResp;
    }
}
