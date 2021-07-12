package com.xyz.pay.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 支付宝支付业务参数
 */
@NoArgsConstructor
@Data
public class AliPayModel implements Serializable {
    /**
     * 商品编码
     */
    private String prodCode;
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 金额单位为元
     */
    private Long amount;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 订单描述
     */
    private String body;
}
