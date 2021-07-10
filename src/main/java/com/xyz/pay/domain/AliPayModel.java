package com.xyz.pay.domain;

import java.io.Serializable;

/**
 * 支付宝支付业务参数
 */
public class AliPayModel implements Serializable {
    /**
     * 商品编码
     */
    private String prodCode;
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 金额单位为元
     */
    private Long amount;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 订单描述
     */
    private String body;

    public AliPayModel() {
    }

    public AliPayModel(String prodCode, String outTradeNo, Long amount, String subject, String body) {
        this.prodCode = prodCode;
        this.outTradeNo = outTradeNo;
        this.amount = amount;
        this.subject = subject;
        this.body = body;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
