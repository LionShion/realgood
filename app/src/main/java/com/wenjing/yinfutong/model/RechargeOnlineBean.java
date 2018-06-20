package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/27.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeOnlineBean {
    public RechargeOnlineBean() {
    }

    /**
     * orderNo : 充值订单号
     */

    private String orderNo;
    private int count;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
