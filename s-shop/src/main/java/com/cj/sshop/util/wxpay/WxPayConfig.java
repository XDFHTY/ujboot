package com.cj.sshop.util.wxpay;


import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

@Data
@Slf4j
@ConfigurationProperties(prefix = "pay.wxpay")
public class WxPayConfig  implements WXPayConfig {


    /** 公众账号ID */
    private String appID;

    /** 商户号 */
    private String mchID;

    /** API 密钥 */
    private String key;

    /** API 沙箱环境密钥 */
    private String sandboxKey;

    /** API证书绝对路径 */
    private String certPath;

    /** 退款异步通知地址 */
    private String notifyUrl;

    private Boolean useSandbox;

    /** HTTP(S) 连接超时时间，单位毫秒 */
    private int httpConnectTimeoutMs = 8000;

    /** HTTP(S) 读数据超时时间，单位毫秒 */
    private int httpReadTimeoutMs = 10000;


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream()  {
        ClassPathResource resource = new ClassPathResource(certPath);
        InputStream inputStream = null;
        try {
//            File certFile = resource.getFile();
            inputStream = resource.getInputStream();
        } catch (FileNotFoundException e) {
            log.error("找不到证书文件, path={}, exception is:{}", certPath, e);
        } catch (IOException e) {
            log.error("证书路径错误");
        }
        return inputStream;
    }

    @Override
    public String getKey(){
        if (useSandbox) {
            return sandboxKey;
        }

        return key;
    }

}
