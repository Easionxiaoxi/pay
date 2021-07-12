package com.xyz.pay.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单查询参数
 */
@NoArgsConstructor
@Data
public class WxPayQueryModel implements Serializable {
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 微信交易号
     */
    private String transactionId;
}
