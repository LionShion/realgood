package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalBillsWithdrawDetailsBean {

        /*
        *     "amount": 100,
              "bankInfo": "647633181311366446",
              "bankName": "中国工商银行",
              "actualAmount": 100,
              "fee": 0,
              "rate": 6.54,
              "orderNo": "46484684613a1",
              "timeStr": "2018-03-19 19:36",
              "country": 1

        * */

    private double amount;
    private String bankInfo;
    private String bankName;
    private double actualAmount;
    private double fee;
    private double rate;
    private String orderNo;
    private String timeStr;
    private int country;

    public PersonalBillsWithdrawDetailsBean() {
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

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

}
