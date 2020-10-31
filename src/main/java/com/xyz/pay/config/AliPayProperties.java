package com.xyz.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description : 支付宝支付属性类，用于读取支付宝支付配置
 * @Author : xyz
 * @Date: 2020-10-29 17:57
 */
@Data
@ConfigurationProperties(prefix = "alipay")
public class AliPayProperties {
    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    public String appId;
    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    public String merchantPrivateKey;
    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public String alipayPublicKey;
    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public String notifyUrl;
    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public String returnUrl;
   /**
     * 页面跳转同步通知H5页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public String returnH5Url;

    /**
     *  签名方式
     */
    public String signType;
    /**
     * 字符编码格式
     */
    public String charset;
    /**
     * 支付宝网关
     */
    public String gatewayUrl;
}
