package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\4\11 0011.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantFundBean {

    private double receiveAmount;
    private String count;
    private double income;
    private int custCount;

    public MerchantFundBean() {
    }

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getCustCount() {
        return custCount;
    }

    public void setCustCount(int custCount) {
        this.custCount = custCount;
    }
}
