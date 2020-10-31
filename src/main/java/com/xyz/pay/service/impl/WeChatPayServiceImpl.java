package com.xyz.pay.service.impl;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.xyz.pay.service.WeChatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description : 微信支付接口实现
 * @Author : xyz
 * @Date: 2020-10-29 11:19
 */
@Service
public class WeChatPayServiceImpl implements WeChatPayService {
    /**
     * 注入微信支付接口
     */
    @Autowired
    private WxPayService wxService;

    /**
     * @param outTradeNo 订单号
     * @param totalFee   金额（单位为分）
     * @return String 返回二维码地址
     * @description web网站下单
     * @author xyz
     */
    @Override
    public String unifiedOrderByNative(String outTradeNo, Long totalFee) throws UnknownHostException {
        // 封装下单请求参数
        WxPayUnifiedOrderRequest.WxPayUnifiedOrderRequestBuilder wxPayUnifiedOrderRequestBuilder = WxPayUnifiedOrderRequest.newBuilder();
        wxPayUnifiedOrderRequestBuilder
                .deviceInfo("WEB")
                .body("湖南人才-职称评审")
                .productId("ZCPS_001")
                .outTradeNo(outTradeNo)
                .totalFee(Integer.valueOf(String.valueOf(totalFee)))
                .spbillCreateIp(InetAddress.getLocalHost().getHostAddress())
                .tradeType(WxPayConstants.TradeType.NATIVE);
        try {
            // 调用微信统一下单接口，获取二维码url地址，给前端用户扫码支付
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.wxService.unifiedOrder(wxPayUnifiedOrderRequestBuilder.build());
            return wxPayUnifiedOrderResult.getCodeURL();
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param outTradeNo 订单号
     * @param totalFee     金额（分）
     * @return String 手机端微信支付跳转的链接
     * @description 第三方手机浏览器H5下单
     * @author xyz
     */
    @Override
    public String unifiedOrderByH5(String outTradeNo, Long totalFee) throws UnknownHostException {
        // 封装下单请求参数
        WxPayUnifiedOrderRequest.WxPayUnifiedOrderRequestBuilder wxPayUnifiedOrderRequestBuilder = WxPayUnifiedOrderRequest.newBuilder();
        wxPayUnifiedOrderRequestBuilder
                .body("湖南人才-职称评审")
                .outTradeNo(outTradeNo)
                .totalFee(Integer.valueOf(String.valueOf(totalFee)))
                .spbillCreateIp(InetAddress.getLocalHost().getHostAddress())
                .tradeType(WxPayConstants.TradeType.MWEB)
                .sceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://m.proftest.hnpxw.org\",\"wap_name\": \"湖南专技人员服务管理平台\"}");
        try {
            // 调用微信统一下单接口，获取手机端微信支付跳转的链接，给手机端用户支付
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.wxService.unifiedOrder(wxPayUnifiedOrderRequestBuilder.build());
            return wxPayUnifiedOrderResult.getMwebUrl();
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 查询订单.---用于补单
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     * @return the wx pay order query result
     */
    @Override
    public void queryOrder(String transactionId, String outTradeNo) {
        try {
            // 调用微信查单接口，获取订单结果
            WxPayOrderQueryResult wxPayOrderQueryResult = this.wxService.queryOrder(transactionId, outTradeNo);
            // 根据返回状态码returnCode判断查询成功
            if (WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getReturnCode())) {
                // 获取订单号
                System.out.println(wxPayOrderQueryResult.getOutTradeNo());
                // 根据业务结果resultCode判断支付成功
                if (WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getResultCode())) {
                    // 根据交易状态tradeState判断交易成功
                    if (WxPayConstants.WxpayTradeStatus.SUCCESS.equals(wxPayOrderQueryResult.getTradeState())) {
                        // 获取微信方交易号
                        System.out.println(wxPayOrderQueryResult.getTransactionId());
                        // 获取支付完成时间
                        System.out.println(wxPayOrderQueryResult.getTimeEnd());
                    }
                    // 根据交易状态tradeState判断交易失败
                    if (! WxPayConstants.WxpayTradeStatus.SUCCESS.equals(wxPayOrderQueryResult.getTradeState())) {
                        System.out.println("微信付款失败，失败原因："+ wxPayOrderQueryResult.getTradeStateDesc());
                    }
                }
                // 根据业务结果resultCode判断支付失败
                if (! WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getResultCode())) {
                    System.out.println("微信回调付款失败，失败原因："+wxPayOrderQueryResult.getErrCode() + ":" + wxPayOrderQueryResult.getErrCodeDes());
                }
            }
            // 根据返回状态码returnCode判断查询失败
            if (! WxPayConstants.ResultCode.SUCCESS.equals(wxPayOrderQueryResult.getReturnCode())) {
                System.out.println("微信查询订单失败，失败原因："+ wxPayOrderQueryResult.getErrCode() + ":" + wxPayOrderQueryResult.getErrCodeDes());
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
    }
}
