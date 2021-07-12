package com.xyz.pay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信H5支付参数
 */
@NoArgsConstructor
@Data
public class WxPayH5Model implements Serializable {
    /**
     * 订单描述
     */
    private String body;
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 金额（分）
     */
    private Integer totalFee;
    /**
     * h5Info
     */
    private H5Info h5Info;

    @NoArgsConstructor
    @Data
    public static class H5Info {
        @JsonProperty("type")
        private String type;
        @JsonProperty("wap_url")
        private String wapUrl;
        @JsonProperty("wap_name")
        private String wapName;
    }
}
