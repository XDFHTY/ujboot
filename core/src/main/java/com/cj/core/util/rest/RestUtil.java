package com.cj.core.util.rest;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.ObjectUtil;
import com.cj.core.util.SpringUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

@Component
public class RestUtil {

    @Autowired
    private Server server;

    private static Server server2;


    @PostConstruct
    public void init(){
        server2 = this.server;
    }


    private RestTemplate restTemplate = null;


//    public String getport() {
//        Properties pros = new Properties();
//        try {
//            pros.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/application.properties"), "UTF-8"));
//            return pros.getProperty("server.port");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private String url = "http://localhost:";

    //创建请求头
    HttpHeaders headers = new HttpHeaders();


    public ApiResult postJson(String uri, Object o) {
        if (restTemplate == null) {
            restTemplate = (RestTemplate) SpringUtil.getBean("restTemplate");
        }
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(o, headers);
        String port = server2.getPort();
        long t1 = System.currentTimeMillis();
        ApiResult apiResult = restTemplate.postForObject(url + port + uri, httpEntity, ApiResult.class);
        long t2 = System.currentTimeMillis();
        System.out.println("================>>>>调用耗时："+(t2-t1)+" ms");
        return apiResult;
    }
    public String postJson2(String uri, Object o) {
        if (restTemplate == null) {
            restTemplate = (RestTemplate) SpringUtil.getBean("restTemplate");
        }
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(o, headers);
        String port = server2.getPort();
        long t1 = System.currentTimeMillis();
        String apiResult = restTemplate.postForObject(uri, httpEntity, String.class);
        long t2 = System.currentTimeMillis();
        System.out.println("================>>>>调用耗时："+(t2-t1)+" ms");
        return apiResult;
    }

    public ApiResult getJson(String uri, Parameter parameter) {
        if (restTemplate == null) {
            restTemplate = (RestTemplate) SpringUtil.getBean("restTemplate");
        }
        String port = server2.getPort();
        ApiResult apiResult;

        if (parameter == null) {
            apiResult = restTemplate.getForObject(url + port + uri, ApiResult.class);

        } else {
            apiResult = restTemplate.getForObject(url + port + uri +
                    "?parameter={parameter}&parameters={parameters}&json={json}",
                    ApiResult.class, ObjectUtil.object2Map(parameter));

        }
        return apiResult;
    }

}
