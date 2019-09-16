package com.cj.common.service;

import javax.servlet.http.HttpServletRequest;

public interface PowerService {

    //判断操作者ID是否和被操作者一致
    public void checkIdalike(HttpServletRequest request,long customerId);
}
