package com.cj.core.security.dto;


import com.cj.core.aop.AccessLimit;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Customer;
import com.cj.core.filter.HeaderMapRequestWrapper;
import com.cj.core.filter.dto.Check;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.SpringUtil;
import com.cj.core.util.http.HttpUtil;
import com.cj.core.util.jwt.JwtUtil;
import com.google.gson.Gson;
import org.reflections.Reflections;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MyAuthService implements Filter {

    Check check = new Check();
    Gson gson = JsonUtil.gson;
    private StringRedisTemplate stringRedisTemplate = null;
    private Lock lock = new ReentrantLock();// 锁对象


    /**
     * @param request
     * @param authentication
     * @Description: 判断一个请求是否拥有权限。
     * @auther: csp
     * @date: 2019/1/7 下午9:48
     * @return: boolean
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
//        if (stringRedisTemplate == null) {
//            stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
//        }
//        if (!"GET".equals(request.getMethod())) {
//
//            //ID|SESTIONID+URI限流,2秒1次
//            String token = request.getHeader("token");
//            String id = "";
//            if (token == null || token.trim().length() == 0) {
//                id = request.getSession().getId();
//            } else {
//                id = JwtUtil.getConsumerId(token);
//                if (id == null) {
//                    ApiResult apiResult = ApiResult.CODE_401();
//                    apiResult.setMsg("token异常");
//                    throw new CustomAuthenticationException(gson.toJson(apiResult));
//                }
//            }
//            String key = id + request.getRequestURI();
//
//            lock.lock();
//            try {
//                //根据key获取已请求次数
//                String snum = stringRedisTemplate.opsForValue().get(key);
//                int num = Integer.parseInt(snum == null ? 0 + "" : snum);
//                if (num == 0) {
//                    //set时一定要加过期时间
//                    stringRedisTemplate.opsForValue().set(key, 1 + "", 2, TimeUnit.SECONDS);
//                } else {
//                    ApiResult apiResult = ApiResult.CODE_404();
//                    apiResult.setMsg("请求频繁");
//                    throw new CustomAuthenticationException(gson.toJson(apiResult));
//                }
//            } finally {
//                lock.unlock();
//            }
//        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return true;
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            //check if this uri can be access by anonymous
            return true;
        }

        authentication.getAuthorities();
        String uri = request.getRequestURI();
//        check this uri can be access by this role


        return true;

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String URL = request.getRequestURL().toString();
        System.out.println("[" + Thread.currentThread().getName() + "]====" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "===========请求URL: " + URL);

        if (URL.indexOf("http://localhost:") != -1) {
            filterChain.doFilter(request, response);
        } else {


            long t1 = System.currentTimeMillis();
            ApiResult apiResult = check.check(request);
            if (apiResult.getCode() != 200) {
                throw new CustomAuthenticationException(gson.toJson(apiResult));
            }
            long t2 = System.currentTimeMillis();
            System.out.println("[" + Thread.currentThread().getName() + "]========鉴权耗时: " + (t2 - t1) + "ms");


            HeaderMapRequestWrapper headerMapRequestWrapper = null;
            String s = (String) apiResult.getData();
            if (s != null) {
                Customer customer = gson.fromJson(s, Customer.class);
                request.getSession().setAttribute("name", customer.getCustomerName());


                headerMapRequestWrapper = new HeaderMapRequestWrapper(request, customer);
            }

            filterChain.doFilter(headerMapRequestWrapper == null ? request : headerMapRequestWrapper, response);
        }
    }


}