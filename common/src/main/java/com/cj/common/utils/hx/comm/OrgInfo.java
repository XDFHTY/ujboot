package com.cj.common.utils.hx.comm;

import com.cj.common.utils.hx.entity.HxConfig;
import com.cj.core.util.rest.Server;
import io.swagger.client.api.AuthenticationApi;
import io.swagger.client.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by easemob on 2017/3/31.
 */
@Component
public class OrgInfo {
    @Autowired
    private HxConfig hxConfig;

    public static String ORG_NAME;
    public static String APP_NAME;
    public static String GRANT_TYPE;
    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
   // public static final Logger logger = LoggerFactory.getLogger(OrgInfo.class);


    @PostConstruct
    public void init() {
        ORG_NAME = this.hxConfig.ORG_NAME;
        APP_NAME = this.hxConfig.APP_NAME;
        GRANT_TYPE = this.hxConfig.GRANT_TYPE;
        CLIENT_ID = this.hxConfig.CLIENT_ID;
        CLIENT_SECRET = this.hxConfig.CLIENT_SECRET;
    }

//    static {
//        InputStream inputStream = OrgInfo.class.getClassLoader().getResourceAsStream("application.properties");
//        Properties prop = new Properties();
//        try {
//            prop.load(inputStream);
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        ORG_NAME = prop.getProperty("ORG_NAME");
//        APP_NAME = prop.getProperty("APP_NAME");
//    }
}
