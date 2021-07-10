package com.xyz.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.xyz.pay.config.AliPayProperties;
import com.xyz.pay.domain.AliPayModel;
import com.xyz.pay.domain.Result;
import com.xyz.pay.service.AliPayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 支付宝支付
 */
@Service
@AllArgsConstructor
public class AliPayServiceImpl implements AliPayService {

    /**
     * 支付宝配置属性
     */
    private AliPayProperties alipayProperties;

    /**
     * 支付宝web网页端支付
     *
     * @param aliPayModel 支付业务参数
     * @return Result
     */
    @Override
    public Result unifiedOrderByWeb(AliPayModel aliPayModel) {
        // 支付宝基本配置信息封装
        DefaultAlipayClient.Builder builder = DefaultAlipayClient.builder(this.alipayProperties.getGatewayUrl(), this.alipayProperties.getAppId(), this.alipayProperties.getMerchantPrivateKey());
        builder
                // 公钥
                .alipayPublicKey(this.alipayProperties.getAlipayPublicKey())
                // 签名方式
                .signType(this.alipayProperties.getSignType())
                // 格式
                .format(AlipayConstants.FORMAT_JSON)
                // 编码
                .charset(AlipayConstants.CHARSET_UTF8)
                // 商品编码
                .prodCode(aliPayModel.getProdCode());

        // 支付业务信息封装，web网页端用AlipayTradePagePayRequest对象
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        // 扫码支付成功后，在前端页面同步通知支付结果
        alipayTradePagePayRequest.setReturnUrl(this.alipayProperties.getReturnUrl());
        // 扫码支付成功后，调用后端异步通知支付结果的接口
        alipayTradePagePayRequest.setNotifyUrl(this.alipayProperties.getNotifyUrl());
        // 业务内容封装
        AlipayTradeWapPayModel wapPayModel = new AlipayTradeWapPayModel();
        // 订单号
        wapPayModel.setOutTradeNo(aliPayModel.getOutTradeNo());
        // 金额
        wapPayModel.setTotalAmount(String.valueOf(aliPayModel.getAmount()));
        // 订单标题
        wapPayModel.setSubject(aliPayModel.getSubject());
        // 订单描述
        wapPayModel.setBody(aliPayModel.getBody());
        alipayTradePagePayRequest.setBizModel(wapPayModel);
        try {
            // 调用支付宝客户端接口支付,获取结果
            AlipayTradePagePayResponse alipayTradePagePayResponse = builder.build().pageExecute(alipayTradePagePayRequest);
            if (alipayTradePagePayResponse.isSuccess()) {
                return Result.success(alipayTradePagePayResponse);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 支付宝H5支付
     *
     * @param aliPayModel 支付业务参数
     * @return Result
     */
    @Override
    public Result unifiedOrderByApp(AliPayModel aliPayModel) {
        // 支付宝基本配置信息封装
        DefaultAlipayClient.Builder builder = DefaultAlipayClient.builder(this.alipayProperties.getGatewayUrl(), this.alipayProperties.getAppId(), this.alipayProperties.getMerchantPrivateKey());
        builder
                // 公钥
                .alipayPublicKey(this.alipayProperties.getAlipayPublicKey())
                // 签名方式
                .signType(this.alipayProperties.getSignType())
                // 格式
                .format(AlipayConstants.FORMAT_JSON)
                // 编码
                .charset(AlipayConstants.CHARSET_UTF8)
                // 商品编码
                .prodCode(aliPayModel.getProdCode());

        //支付业务信息封装，APP端用AlipayTradeWapPayRequest对象
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        // 扫码支付成功后，在H5前端页面同步通知支付结果
        alipayTradeWapPayRequest.setReturnUrl(this.alipayProperties.getReturnH5Url());
        // 扫码支付成功后，调用后端异步通知支付结果的接口
        alipayTradeWapPayRequest.setNotifyUrl(this.alipayProperties.getNotifyUrl());
        // 业务内容封装
        AlipayTradeWapPayModel wapPayModel = new AlipayTradeWapPayModel();
        // 订单号
        wapPayModel.setOutTradeNo(aliPayModel.getOutTradeNo());
        // 金额单位为元
        wapPayModel.setTotalAmount(String.valueOf(aliPayModel.getAmount()));
        // 订单标题
        wapPayModel.setSubject(aliPayModel.getSubject());
        // 订单描述
        wapPayModel.setBody(aliPayModel.getBody());
        alipayTradeWapPayRequest.setBizModel(wapPayModel);
        try {
            // 调用支付宝客户端接口支付,获取结果
            AlipayTradeWapPayResponse alipayTradeWapPayResponse = builder.build().pageExecute(alipayTradeWapPayRequest);
            if (alipayTradeWapPayResponse.isSuccess()) {
                return Result.success(alipayTradeWapPayResponse);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 支付宝查单，补单
     *
     * @param tradeNo    支付宝交易号
     * @param outTradeNo 订单号
     * @return Result
     */
    @Override
    public Result queryAliPay(String tradeNo, String outTradeNo) {
        // 支付宝基本配置信息封装
        DefaultAlipayClient.Builder builder = DefaultAlipayClient.builder(this.alipayProperties.getGatewayUrl(), this.alipayProperties.getAppId(), this.alipayProperties.getMerchantPrivateKey());
        builder
                // 公钥
                .alipayPublicKey(this.alipayProperties.getAlipayPublicKey())
                // 签名方式
                .signType(this.alipayProperties.getSignType())
                // 格式
                .format(AlipayConstants.FORMAT_JSON)
                // 编码
                .charset(AlipayConstants.CHARSET_UTF8)
                // 商品编码
                .prodCode("FAST_INSTANT_TRADE_PAY");

        // 查单业务信息封装，APP端用AlipayTradeQueryRequest对象
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", outTradeNo);
        jsonObject.put("trade_no", tradeNo);
        alipayTradeQueryRequest.setBizContent(jsonObject.toJSONString());
        try {
            // 调用支付宝客户端查询接口，,获取结果
            AlipayTradeQueryResponse alipayTradeQueryResponse = builder.build().execute(alipayTradeQueryRequest);
            // 判断交易成功
            if ("10000".equals(alipayTradeQueryResponse.getCode()) && "TRADE_SUCCESS".equals(alipayTradeQueryResponse.getTradeStatus())) {
                return Result.success(alipayTradeQueryResponse);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
