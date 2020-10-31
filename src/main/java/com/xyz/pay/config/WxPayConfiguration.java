package com.xyz.pay.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 微信支付配置
 * @Author : xyz
 * @Date: 2020-10-29 10:38
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
@AllArgsConstructor
public class WxPayConfiguration {
    /**
     * 微信配置属性
     */
    private WxPayProperties properties;

    /**
     * @return WeChatPayService
     * @description 封装微信支付配置信息
     * @author xyz
     */
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        // 创建微信支付配置对象，添加属性
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        wxPayConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        wxPayConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
        wxPayConfig.setNotifyUrl(StringUtils.trimToNull(this.properties.getNotifyUrl()));
        // 创建微信支付服务对象，添加配置对象
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        return wxPayService;
    }
}
