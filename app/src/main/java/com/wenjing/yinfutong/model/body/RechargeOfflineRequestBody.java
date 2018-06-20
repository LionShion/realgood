package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/4/12.
 */

public class RechargeOfflineRequestBody {
    private int accountId;
    private int distributorId;
    private double amount;
    private String desc;
    private String orderNo;
    private int registerLang;
    private String sign;

    public RechargeOfflineRequestBody(int accountId, int distributorId, double amount, String orderNo, int registerLang, String sign) {
        this.accountId = accountId;
        this.distributorId = distributorId;
        this.amount = amount;
        this.orderNo = orderNo;
        this.registerLang = registerLang;
        this.sign = sign;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "RechargeOfflineRequestBody{" +
                "accountId=" + accountId +
                ", distributorId=" + distributorId +
                ", amount=" + amount +
                ", desc='" + desc + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", registerLang=" + registerLang +
                ", sign='" + sign + '\'' +
                '}';
    }
}
