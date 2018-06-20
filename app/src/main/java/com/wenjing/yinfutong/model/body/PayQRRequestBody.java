package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/4/3.
 */

public class PayQRRequestBody {

    private int accountId;
    private String tradePassword;
    private int merchantId;
    private double amount;
    private String desc;
    private int bonusId;
    private int registerLang;
    private String sign;


    public PayQRRequestBody(int accountId, String tradePassword, int merchantId, double amount, String desc, int bonusId, int registerLang, String sign) {
        this.accountId = accountId;
        this.tradePassword = tradePassword;
        this.merchantId = merchantId;
        this.amount = amount;
        this.desc = desc;
        this.bonusId = bonusId;
        this.registerLang = registerLang;
        this.sign = sign;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
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

    public int getBonusId() {
        return bonusId;
    }

    public void setBonusId(int bonusId) {
        this.bonusId = bonusId;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PayQRRequestBody{" +
                "accountId=" + accountId +
                ", tradePassword='" + tradePassword + '\'' +
                ", merchantId=" + merchantId +
                ", amount=" + amount +
                ", desc='" + desc + '\'' +
                ", bonusId=" + bonusId +
                ", registerLang=" + registerLang +
                ", sign='" + sign + '\'' +
                '}';
    }
}
