package com.cj.common.utils.hx.entity;

import io.swagger.client.model.Token;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config.hx")
@Data
public class HxConfig {

    public  String ORG_NAME;
    public  String APP_NAME;
    public  String GRANT_TYPE;
    public  String CLIENT_ID;
    public  String CLIENT_SECRET;
    public  Token BODY;
}
