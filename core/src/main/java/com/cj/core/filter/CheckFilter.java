package com.cj.core.filter;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Customer;
import com.cj.core.filter.dto.Check;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.http.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CheckFilter implements Filter {

    Gson gson = JsonUtil.gson;
    Check check = new Check();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long t1 = System.currentTimeMillis();
        System.out.println("====================Check URL================");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");


        if (request.getMethod().equals("OPTIONS")) {
            HttpUtil.doReturn(response, ApiResult.SUCCESS());
        } else {

            String URL = request.getRequestURL().toString();
            System.out.println("[" + Thread.currentThread().getName() + "]====" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                    "===========请求URL: " + URL);

            if (URL.indexOf("http://localhost:") != -1) {
                filterChain.doFilter(request, response);
            } else {


                ApiResult apiResult = check.check(request);
                if (apiResult.getCode() != 200) {
                    HttpUtil.doReturn(response, apiResult);
                } else {


                    HeaderMapRequestWrapper headerMapRequestWrapper = null;
                    String s = (String) apiResult.getData();
                    if (s != null) {
                        Customer customer = gson.fromJson(s, Customer.class);
                        request.getSession().setAttribute("name",customer.getCustomerName());


                        headerMapRequestWrapper = new HeaderMapRequestWrapper(request, customer);
                    }
                    long t2 = System.currentTimeMillis();
                    System.out.println("========鉴权耗时: " + (t2 - t1) + "ms");
                    filterChain.doFilter(headerMapRequestWrapper == null ? request : headerMapRequestWrapper, response);

                }
            }
        }
    }
}