package com.xyz.pay.service;

import com.xyz.pay.domain.AliPayModel;
import com.xyz.pay.domain.AliPayQueryModel;
import com.xyz.pay.domain.Result;

public interface AliPayService {
    /**
     * 支付宝web网页端支付
     * @param aliPayModel 支付业务参数
     * @return Result
     */
    Result unifiedOrderByWeb(AliPayModel aliPayModel);

    /**
     * 支付宝H5支付
     *
     * @param aliPayModel 支付业务参数
     * @return Result
     */
    Result unifiedOrderByApp(AliPayModel aliPayModel);

    /**
     * 支付宝查单，补单
     *
     * @param alipayQueryModel 查询参数
     * @return Result
     */
    Result queryAliPay(AliPayQueryModel alipayQueryModel);
}
