package com.cj.sshop.service;

import com.cj.core.entity.DoctorInfo;
import com.cj.sshop.domain.VoFinanceResp;

import java.util.Date;
import java.util.List;

public interface FinanceService {


    List<DoctorInfo> findAllByName(String pameter);


    VoFinanceResp findAllOrder(Date dateTime, String incomeType, long bindId);
}
