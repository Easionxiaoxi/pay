package com.xyz.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.xyz.pay.domain.Result;
import com.xyz.pay.domain.WxPayH5Model;
import com.xyz.pay.domain.WxPayNativeModel;
import com.xyz.pay.domain.WxPayQueryModel;
import com.xyz.pay.service.WeChatPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 微信支付接口实现
 */
@Service
public class WeChatPayServiceImpl implements WeChatPayService {
    /**
     * 注入微信支付接口
     */
    @Resource
    private WxPayService wxService;

    /**
     * web网站下单
     *
     * @param wxPayNativeModel 支付业务参数
     * @return String 返回二维码地址
     */
    @Override
    public Result unifiedOrderByNative(WxPayNativeModel wxPayNativeModel) throws UnknownHostException {
        // 封装下单请求参数
        WxPayUnifiedOrderRequest.WxPayUnifiedOrderRequestBuilder wxPayUnifiedOrderRequestBuilder = WxPayUnifiedOrderRequest.newBuilder();
        wxPayUnifiedOrderRequestBuilder
                .deviceInfo(wxPayNativeModel.getDeviceInfo())
                .body(wxPayNativeModel.getBody())
                .productId(wxPayNativeModel.getProductId())
                .outTradeNo(wxPayNativeModel.getOutTradeNo())
                .totalFee(wxPayNativeModel.getTotalFee())
                .spbillCreateIp(InetAddress.getLocalHost().getHostAddress())
                .tradeType(WxPayConstants.TradeType.NATIVE);
        try {
            // 调用微信统一下单接口，获取二维码url地址，给前端用户扫码支付
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.wxService.unifiedOrder(wxPayUnifiedOrderRequestBuilder.build());
            return Result.success(wxPayUnifiedOrderResult.getCodeURL());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 第三方手机浏览器H5下单
     *
     * @param wxPayH5Model 支付业务参数
     * @return String 手机端微信支付跳转的链接
     */
    @Override
    public Result unifiedOrderByH5(WxPayH5Model wxPayH5Model) throws UnknownHostException {
        // 封装下单请求参数
        JSONObject h5Info = new JSONObject();
        h5Info.put("h5_info", JSON.toJSONString(wxPayH5Model.getH5Info()));
        WxPayUnifiedOrderRequest.WxPayUnifiedOrderRequestBuilder wxPayUnifiedOrderRequestBuilder = WxPayUnifiedOrderRequest.newBuilder();
        wxPayUnifiedOrderRequestBuilder
                .body(wxPayH5Model.getBody())
                .outTradeNo(wxPayH5Model.getOutTradeNo())
                .totalFee(wxPayH5Model.getTotalFee())
                .spbillCreateIp(InetAddress.getLocalHost().getHostAddress())
                .tradeType(WxPayConstants.TradeType.MWEB)
                .sceneInfo(h5Info.toJSONString());
        try {
            // 调用微信统一下单接口，获取手机端微信支付跳转的链接，给手机端用户支付
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.wxService.unifiedOrder(wxPayUnifiedOrderRequestBuilder.build());
            return Result.success(wxPayUnifiedOrderResult.getMwebUrl());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 查询订单---用于补单
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     *
     * @param wxPayQueryModel 查询参数
     * @return 订单信息
     */
    @Override
    public Result queryOrder(WxPayQueryModel wxPayQueryModel) {
        try {
            // 调用微信查单接口，获取订单结果
            WxPayOrderQueryResult wxPayOrderQueryResult = this.wxService.queryOrder(wxPayQueryModel.getTransactionId(), wxPayQueryModel.getOutTradeNo());
            // 根据返回状态码returnCode判断查询成功
            if (WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getReturnCode())) {
                return Result.success(wxPayOrderQueryResult);
            }
            // 根据返回状态码returnCode判断查询失败
            if (!WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getReturnCode())) {
                return Result.fail(wxPayOrderQueryResult.getErrCodeDes());
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
