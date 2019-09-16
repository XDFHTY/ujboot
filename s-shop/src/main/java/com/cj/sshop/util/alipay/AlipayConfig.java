package com.cj.sshop.util.alipay;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.config.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "pay.alipay")
@Configuration
public class AlipayConfig {

	// 1.商户appid
	public  String appid;

	//2.私钥 pkcs8格式的
	public  String rsa_private_key;


	// 3.支付宝公钥
	public  String alipay_public_key;


	// 4.服务器异步通知 页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public  String notify_url;

	//5.页面跳转同步通知 页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public  String return_url;

	// 6.请求支付宝的网关地址
	public  String url;

	// 7.编码
	public  String charset;

	// 8.返回格式
	public  String format;

	// 9.加密类型
	public  String signtype;
}
