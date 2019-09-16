package com.cj.core.filter.dto;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.AuthRoleModulars;
import com.cj.core.domain.Customer;
import com.cj.core.domain.MemoryData;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.SpringUtil;
import com.cj.core.util.jwt.JwtUtil;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Check {
    /**
     * 不校验登录和权限的URL
     * 比如js，国际化文件，页面等
     */
    private static final String[] URIs = {

            "/i18n/**",
            "/*/v2/api-docs",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/favicon.ico",
            "csrf",

            "/swagger-ui.html",
            "/docs.html",


            "/test/**",
            "/*",
            "/static/**",
            "/s-*/*",
            "/s-*/api/*",

            "/s-*/*img/*",
//            "/img/*",
            "/s-*/*idcard/*",
//            "/idcard/*",

//            "/s-zuul/api/*",

            //查询用户协议
            "/s-basic/api/v1/clause/findClauseBySubjectFront",
            //获取验证码
            "/s-user/api/v2/user/getCode",
            //注册
            "/s-user/api/v2/user/register",

            //登录
            "/s-user/api/v2/user/login",
            //找回密码
            "/s-user/api/v2/user/retrieveUserPass",

            //检查更新
            "/s-user/api/v2/user/findVersion",

//
//            //识别身份证
//            "/s-user/api/v1/user/getCardInfo",
//

            //用户查询用药提醒
            "/s-kidney/api/v1/drug/findDrug",

            //查询地区信息
            "/*/api/v1/region/region*",


            //查询医院、科室、疾病
            "/*/api/v1/comment/*",

            //刷新权限信息
            "/s-admin/api/v1/rolepower/readRolePower",
            //管理员登录
            "/s-admin/api/v1/account/ifLogin",

            //保存聊天记录
            "/s-im/api/v1/msg/insert",

            //下载环信图片保存到本地
            "/s-file/api/file/hxUploads",

            "/s-shop/api/v2/orders/u/order/aliNotify",  //支付宝异步回调
            "/s-shop/api/v2/orders/u/order/wxNotify",  //微信异步回调
            "/s-shop/api/v2/u/order/get",  //微信获取沙箱秘钥

            //查文章详情
            "/s-basic/api/v1/article/findForArticleByIDFront",

    };
    String[] uris = {
            //文件上传
            "/s-file/api/file/uploads",

            //身份证照片上传
            "/s-file/api/file/uploadIdCard",

            //发布新版APP
            "/s-admin/api/v1/version/add",
    };

    StringRedisTemplate stringRedisTemplate = null;

    static AntPathMatcher matcher = new AntPathMatcher();



    public ApiResult check(HttpServletRequest request) {
        if (stringRedisTemplate == null) {
            stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        }
        ApiResult apiResult = ApiResult.CODE_200();
        //根据 token+URI 限流



        //拦截请求uri
        String requestURI = request.getRequestURI();
        if (requestURI.indexOf("?") != -1) {
            requestURI = requestURI.substring(0, requestURI.indexOf("?"));
        }
        boolean isToken = true;
        String finalRequestURI = requestURI;
        boolean b1 = Arrays.stream(URIs).anyMatch(uri -> matcher.match(uri, finalRequestURI));
        if (b1) isToken = false;
        if (isToken) {
            Set<String> userRoles = new HashSet<>();
            Set<String> urlRoles = new HashSet<>();
            String str = checkToken(request, userRoles);
            Customer customer = new Customer();
            try {
                customer = JsonUtil.gson.fromJson(str, Customer.class);
                apiResult.setData(str);

            } catch (JsonSyntaxException e) {
               if (str.equals("Redis连接失败,请重试")){
                    apiResult = ApiResult.CODE_404();
                    apiResult.setMsg(str);
                }else {
                    apiResult = ApiResult.CODE_401();
                    apiResult.setMsg(str);

                }
                return apiResult;
            }

            boolean isPower = true;
            boolean b2 = Arrays.stream(uris).anyMatch(uri -> matcher.match(uri, finalRequestURI));
            if (b2) isPower = false;
            if (isPower) {
                if (!checkPower(request, userRoles, urlRoles, requestURI)) {
                    System.out.println("[" + Thread.currentThread().getName() + "]====403: 权限不足");
                    apiResult = ApiResult.CODE_403();
                }
            }
        }

        return apiResult;
    }

    public String checkToken(HttpServletRequest request, Set<String> userRoles) {

        ApiResult apiResult = null;
        String token = request.getHeader("token");

        if (token == null || token.trim().length() == 0) {
            return "用户未提交token";
        }
        String consumerId = JwtUtil.getConsumerId(token);
        if (consumerId == null) {
            return "无效的token";
        }


        Customer customer = new Customer();

        String iKey = "i" + consumerId;
        String str = "";
        try {
            long tt = System.currentTimeMillis();
            str = stringRedisTemplate.opsForValue().get(iKey).trim();
            System.out.println("=======Redis耗时  "+(System.currentTimeMillis()-tt)+" ms");
        } catch (Exception e) {
            System.out.println("[" + Thread.currentThread().getName() + "]===========>>>Redis连接失败" + ",   ikey=" + iKey);
            Boolean b = stringRedisTemplate.hasKey(iKey);
            System.out.println("[" + Thread.currentThread().getName() + "]===========>>>" + iKey + "是否存在" + ": " + b);
            if (b){
                return "Redis连接失败,请重试";
            }else {

                return "token不存在,请重新登录";
            }
        }

        customer = JsonUtil.gson.fromJson(str, Customer.class);
        String oldToken = customer.getToken();
        if (oldToken != null) {
            if (!token.equals(oldToken)) {
                return "账号已在其他设备登录(token失效)";
            }
            new Thread(() -> {
                stringRedisTemplate.expire(iKey, 7l, TimeUnit.DAYS);
            }).start();

            customer.getRoles().forEach(authRole -> {
                userRoles.add(authRole.getRoleName());
            });
            return str;

        } else {
            return "token已过期";
        }

    }

    public boolean checkPower(HttpServletRequest request,
                              Set<String> userRoles,
                              Set<String> urlRoles,
                              String requestURI) {
        List<AuthRoleModulars> authRoleModulars = MemoryData.authRoleModulars;

        if (authRoleModulars == null || authRoleModulars.size() == 0) {

            String json = stringRedisTemplate.opsForValue().get("modulars");

            authRoleModulars = JsonUtil.gson.fromJson(json.trim(), new TypeToken<List<AuthRoleModulars>>() {
            }.getType());
        }


        String requestMethod = request.getMethod();

        String finalRequestURI = requestURI;

        authRoleModulars.forEach(authRoleModulars1 -> {
            authRoleModulars1.getAuthModulars().getChildren().forEach(authModulars2 -> {
                authModulars2.getChildren().forEach(authModulars3 -> {
                    authModulars3.getChildren().forEach(authModulars0 -> {
                        String authUrl = authModulars0.getPageUrl();
                        String authMethod = authModulars0.getPageMethod();
                        if (restFulUrlMatch(requestMethod, authMethod, finalRequestURI, authUrl)) {
                            urlRoles.add(authRoleModulars1.getRoleName());
                        }
                    });
                });
            });
        });

        if (urlRoles.size() == 0) return false;
        boolean b = false;
        b = urlRoles.stream().anyMatch(
                urlRoleName -> userRoles.stream().anyMatch(
                        userRoleName -> userRoleName.equals(urlRoleName))
        );

        return b;
    }

    private static boolean restFulUrlMatch(String reqMethod, String authMethod, String reqUrl, String authUrl) {
        boolean isMethoh = false;
        if ("ALL".equals(authMethod) || reqMethod.equals(authMethod)) isMethoh = true;

        boolean isUrl = matcher.match(authUrl, reqUrl);
        return isMethoh & isUrl;
    }


}
