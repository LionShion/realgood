package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/27.
 */

public class RechargeOnlineRequestBody {
    private int accountId;
    private double amount;
    private String desc;
    private String sign;
    private int payWay;
    private int bankId;
    private int registerLang;
    private String tradePassword;

    public RechargeOnlineRequestBody(int accountId, double amount, String desc, String sign, int payWay, int bankId, int registerLang, String tradePassword) {
        this.accountId = accountId;
        this.amount = amount;
        this.desc = desc;
        this.sign = sign;
        this.payWay = payWay;
        this.bankId = bankId;
        this.registerLang = registerLang;
        this.tradePassword = tradePassword;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    @Override
    public String toString() {
        return "RechargeOnlineRequestBody{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                ", desc='" + desc + '\'' +
                ", sign='" + sign + '\'' +
                ", payWay=" + payWay +
                ", bankId=" + bankId +
                ", registerLang=" + registerLang +
                ", tradePassword='" + tradePassword + '\'' +
                '}';
    }
}
