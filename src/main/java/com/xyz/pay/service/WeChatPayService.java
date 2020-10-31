package com.xyz.pay.service;

import java.net.UnknownHostException;

/**
 * @Description : 微信支付接口，统一返回自定义结果
 * @Author : xyz
 * @Date: 2020-10-29 10:28
 */
public interface WeChatPayService {
    /**
     * @param outTradeNo 订单号
     * @param amount     金额
     * @return String 返回二维码地址
     * @description web网站下单
     * @author xyz
     */
    String unifiedOrderByNative(String outTradeNo, Long amount) throws UnknownHostException;

    /**
     * @param outTradeNo 订单号
     * @param amount     金额
     * @return String 手机端微信支付跳转的链接
     * @description 第三方手机浏览器H5下单
     * @author xyz
     */
    String unifiedOrderByH5(String outTradeNo, Long amount) throws UnknownHostException;

    /**
     * 查询订单.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     * @return the wx pay order query result
     */
    void queryOrder(String transactionId, String outTradeNo);
}
