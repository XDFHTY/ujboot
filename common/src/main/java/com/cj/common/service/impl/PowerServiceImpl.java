package com.cj.common.service.impl;

import com.cj.core.exception.MyException;
import com.cj.common.service.PowerService;
import com.cj.core.domain.ApiResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PowerServiceImpl implements PowerService {
    @Override
    public void checkIdalike(HttpServletRequest request, long customerId) {
        long id = Long.parseLong(request.getHeader("id"));
        if (id != customerId) {
            throw new MyException( "操作人ID与被操作id不一致");
        }
    }
}
