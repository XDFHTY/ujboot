package com.cj.core.interceptors;

import com.cj.core.aop.AccessLimit;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.SpringUtil;
import com.cj.core.util.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * api限流
 */

@Slf4j
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate = null;
    private Lock lock = new ReentrantLock();// 锁对象


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (stringRedisTemplate == null) {
            stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        }
        long userId = 0l;

        try {
            userId  = Long.parseLong(request.getHeader("id"));
        }catch (Exception e){

            return true;
        }

        try {
            // Handler 是否为 HandlerMethod 实例
            if (handler instanceof HandlerMethod) {
                // 强转
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取方法
                Method method = handlerMethod.getMethod();
                // 是否有AccessLimit注解
                if (!method.isAnnotationPresent(AccessLimit.class)) {
                    return true;
                }
                // 获取注解内容信息
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if (accessLimit == null) {
                    return true;
                }
                int nums = accessLimit.nums();//请求次数
                int second = accessLimit.second();//请求时间范围
                //根据 ID + API 限流
                String key = userId + request.getRequestURI();
                lock.lock();
                try {
                    //根据key获取已请求次数
                    String snum = stringRedisTemplate.opsForValue().get(key);
                    int num = Integer.parseInt(snum == null ? 0 + "" : snum);
                    if (num == 0) {
                        //set时一定要加过期时间
                        stringRedisTemplate.opsForValue().set(key, 1 + "", second, TimeUnit.SECONDS);
                    } else if (num < nums) {
                        stringRedisTemplate.opsForValue().set(key, (num + 1) + "", second, TimeUnit.SECONDS);
                    } else {
                        ApiResult apiResult = ApiResult.CODE_404();
                        apiResult.setMsg("请求过于频繁");
                        HttpUtil.doReturn(response, apiResult);
                        return false;
                    }
                } finally {
                    lock.unlock();
                }

            }
        } catch (Exception e) {
            ApiResult apiResult = ApiResult.CODE_404();
            apiResult.setMsg("Redis 连接异常");
            HttpUtil.doReturn(response, apiResult);
            return false;
        }
        return true;
    }
}
