package com.xyz.pay.domain;

import java.io.Serializable;

/**
 * 订单查询参数
 */
public class AlipayQueryModel implements Serializable {
    /**
     * 支付宝交易号
     */
    private String outTradeNo;
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 商品编码
     */
    private String prodCode;

    public AlipayQueryModel() {
    }

    public AlipayQueryModel(String outTradeNo, String tradeNo, String prodCode) {
        this.outTradeNo = outTradeNo;
        this.tradeNo = tradeNo;
        this.prodCode = prodCode;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
}
