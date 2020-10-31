package com.xyz.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description : 微信支付属性类,，用于读取微信支付配置
 * @Author : xyz
 * @Date: 2020-10-29 10:28
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号的appid
     */
    private String appId;
    /**
     * 公众号APP密钥
     */
    private String appKey;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 支付成功回调地址
     */
    private String notifyUrl;
}
