package com.cj.core.security.config;

import com.cj.core.filter.CorsFilter;
import com.cj.core.security.dto.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Spring Security配置类
 *
 * @author Niu Li
 * @since 2017/6/16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String[] AUTH_WHITELIST = {

            "/i18n/**",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
//
//            "/*",
            "/api",
//            "/api2",
            "/swagger-ui.html",
            "/docs.html",
//            "/file/**",
//            "/static/**",
//            "/test/**",
//            "/favicon.ico",
//
//            "/*/api/v1/demo/**",
//            //刷新权限
//            "/system/api/v1/rolepower/readRolePower",
//            //管理员登录
//            "/system/api/v1/account/ifLogin",
//            //查询地区信息
//            "/common/api/v1/area/*",

    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(AUTH_WHITELIST);
    }

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().access("@myAuthService.canAccess(request,authentication)").and()
                //异常处理,可以再此使用entrypoint来定义错误输出
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                //不需要session来控制,所以这里可以去掉
//                .securityContext().securityContextRepository(new NullSecurityContextRepository())
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //开启匿名访问
                .anonymous()
                .and()
                //退出登录自己来控制
                .logout().disable()
                //因为没用到cookies,所以关闭cookies,防止循环定向
                .csrf().disable()
                //允许跨域
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
        ;


    }
}
