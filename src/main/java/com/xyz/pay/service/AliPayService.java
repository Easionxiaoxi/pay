package com.xyz.pay.service;

public interface AliPayService {
    /**
     * 支付宝web网页端支付
     *
     * @param outTradeNo 订单号
     * @param amount     金额
     * @return void
     */
    void unifiedOrderByWeb(String outTradeNo, Long amount);

    /**
     * 支付宝App端H5支付
     *
     * @param outTradeNo 订单号
     * @param amount     金额
     * @return void
     */
    void unifiedOrderByApp(String outTradeNo, Long amount);

    /**
     * 支付宝查单，补单
     *
     * @param tradeNo    支付宝交易号
     * @param outTradeNo 订单号
     * @return void
     */
    void queryAliPay(String tradeNo, String outTradeNo);
}
