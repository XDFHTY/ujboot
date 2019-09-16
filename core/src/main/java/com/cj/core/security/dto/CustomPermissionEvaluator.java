package com.cj.core.security.dto;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * 自定义验证方法
     * @param authentication        登录的时候存储的用户信息
     * @param o    @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第一个参数
     * @param o1            @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第二个参数
     * @return
     */

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
