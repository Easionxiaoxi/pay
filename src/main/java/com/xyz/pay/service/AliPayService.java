package com.xyz.pay.service;

public interface AliPayService {
    void unifiedOrderByWeb(String outTradeNo, Long amount);

    void unifiedOrderByApp(String outTradeNo, Long amount);

    void queryAliPay(String tradeNo, String outTradeNo);
}
