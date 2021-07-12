package com.xyz.pay.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信Native支付参数
 */
@NoArgsConstructor
@Data
public class WxPayNativeModel implements Serializable {
    private String deviceInfo = "WEB";
    private String body;
    private String productId;
    private String outTradeNo;
    private Integer totalFee;
}
