package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\4\21 0021.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class QRContentBean {

    /**
     * 注意   :
     * 站内支付  result就是订单号
     *
     * 站外支付   就是一个链接(支付宝，微信)
     *
     */

    private int payChannel;
    private String result;

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QRContentBean{" +
                "payChannel=" + payChannel +
                ", result='" + result + '\'' +
                '}';
    }
}
