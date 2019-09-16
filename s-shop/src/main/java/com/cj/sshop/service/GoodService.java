package com.cj.sshop.service;

import com.cj.core.domain.Pager;
import com.cj.core.v2entity.V2Good;
import com.cj.sshop.domain.VoFindGood;

import javax.servlet.http.HttpServletRequest;

public interface GoodService {

    //==============================前端
    int createGoods(long id,long roleId);

    V2Good findGoodById(HttpServletRequest request);



    int updateGoodPrice(V2Good good);



    VoFindGood findGood(long id, long doctorId, String goodType);


    //=============================后台

    Pager findAllGoods(Pager pager);

}
