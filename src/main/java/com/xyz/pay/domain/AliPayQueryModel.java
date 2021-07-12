package com.xyz.pay.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单查询参数
 */
@NoArgsConstructor
@Data
public class AliPayQueryModel implements Serializable {
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 商品编码
     */
    private String prodCode;
}
