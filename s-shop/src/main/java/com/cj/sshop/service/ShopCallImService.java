package com.cj.sshop.service;

import com.cj.core.domain.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface ShopCallImService {


    @GetMapping("/s-im/api/v1/msg/getRefundEligibility")
    public ApiResult userToadmin(String json);
}
