package com.cj.core.filter;


import com.cj.core.domain.Customer;
import com.cj.core.util.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

@Slf4j
public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String> headerMap = new HashMap<>();

    /**
     * construct a wrapper for this request
     *
     * @param request
     */
    public HeaderMapRequestWrapper(HttpServletRequest request, Customer customer) {
        super(request);

        String token = request.getHeader("token");
        if (token != null && !token.equals("null")) {

            headerMap.put("token", token);
            headerMap.put("id", customer.getCustomerId() + "");
            headerMap.put("name", customer.getCustomerName());
            headerMap.put("ikey", "i" + customer.getCustomerId());
            Set<String> userRoleIds = new HashSet<>();
            customer.getRoles().forEach(authRole -> {
                userRoleIds.add(String.valueOf(authRole.getRoleId()));
            });
            String roles = userRoleIds.toString().substring(1, userRoleIds.toString().length() - 1);
            headerMap.put("userRoleIds", roles);

        }

    }

    /**
     * add a header with given name and value
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
//        log.info("getHeader ---===========================>{}"+name);
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }

    /**
     * get the Header names
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            names.add(name);
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
//        log.info("getHeaders --->>>>>>{}",name);
        List<String> values = Collections.list(super.getHeaders(name));
//        log.info("getHeaders --->>>>>>{}",values);
        if (headerMap.containsKey(name)) {
//            log.info("getHeaders --->{}",headerMap.get(name));
            values = Arrays.asList(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}