package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/30.
 */

public class CashOutRequestBody {
    private int registerLang;
    private double amount;
    private int accountId;
    private String bankInfo;
    private String password;
    private int type;
    private String sign;

    public CashOutRequestBody(int registerLang, double amount, int accountId, String bankInfo, String password, int type, String sign) {
        this.registerLang = registerLang;
        this.amount = amount;
        this.accountId = accountId;
        this.bankInfo = bankInfo;
        this.password = password;
        this.type = type;
        this.sign = sign;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CashOutRequestBody{" +
                "registerLang=" + registerLang +
                ", amount=" + amount +
                ", accountId=" + accountId +
                ", bankInfo='" + bankInfo + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", sign='" + sign + '\'' +
                '}';
    }
}
