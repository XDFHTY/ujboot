package com.cj.sim.utils.jiguang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by JuLei on 2019/7/23.
 */
@Configuration
@ConfigurationProperties("config.jg")
@Data
public class JgConfig {
    //用户端  测试环境
    public   String APP_KEY_USER;
    public   String MASTER_SECRET_USER;

    //医生端 测试环境
    public   String APP_KEY_DOC;
    public   String MASTER_SECRET_DOC;
}
