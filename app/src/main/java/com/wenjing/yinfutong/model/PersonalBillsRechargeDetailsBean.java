package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalBillsRechargeDetailsBean {


        /*
        *  "type": 1,
          "rechargeResourceTr": "银行卡",
          "rechargeResourceName": "中国工商银行",
          "amount": 50.2,
          "bankInfo": "46843118631",
          "bankName": "中国工商银行",
          "timeStr": "2018-03-19 19:36",
          "orderNo": "4d64w46d14a6d",
          "name": "支付宝充值"
        * */

    private int type;
    private String rechargeResourceTr;
    private String rechargeResourceName;
    private double amount;
    private String bankInfo;
    private String bankName;
    private String timeStr;
    private String orderNo;
    private String name;

    public PersonalBillsRechargeDetailsBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRechargeResourceTr() {
        return rechargeResourceTr;
    }

    public void setRechargeResourceTr(String rechargeResourceTr) {
        this.rechargeResourceTr = rechargeResourceTr;
    }

    public String getRechargeResourceName() {
        return rechargeResourceName;
    }

    public void setRechargeResourceName(String rechargeResourceName) {
        this.rechargeResourceName = rechargeResourceName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
