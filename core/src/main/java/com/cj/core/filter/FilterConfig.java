package com.cj.core.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * Created by zsl on 2017/9/3.
 */
@Configuration
public class FilterConfig {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Value("${web.upload-path}")
    private String path;


//    @Bean
//    public FilterRegistrationBean CheckFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CheckFilter());//添加过滤器
//        registration.setOrder(Integer.MAX_VALUE - 1);//设置优先级
//        registration.setName("CheckFilter");
//        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//
//        return registration;
//    }


//    @Bean
//    public FilterRegistrationBean CorsFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CorsFilter());//添加过滤器
//        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
////        registration.addInitParameter("name", "value");//添加默认参数
//        registration.setName("CorsFilter");//设置优先级
//        registration.setOrder(Integer.MAX_VALUE-2);//设置优先级
//        return registration;
//    }


    /**
     * 解决tomcat乱找路径和限制文件大小
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
//        //单次上传文件总数最大为15M
//        multipartConfigFactory.setMaxFileSize("15MB");
//        //每次http请求大小最大为15M
//        multipartConfigFactory.setMaxRequestSize("15MB");

        String location = path + "data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        multipartConfigFactory.setLocation(location);

        return multipartConfigFactory.createMultipartConfig();
    }


}