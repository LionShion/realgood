package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\4\21 0021.
 */

public class QRContentRequestBody {

    private double receiveAmount;
    private int payChannel;
    private int accountId;
    private int registerLang;

    public double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    @Override
    public String toString() {
        return "QRContentBean{" +
                "receiveAmount=" + receiveAmount +
                ", payChannel=" + payChannel +
                ", accountId=" + accountId +
                ", registerLang=" + registerLang +
                '}';
    }

}
