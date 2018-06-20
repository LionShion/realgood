package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/20.
 */

public class BaseRequestBody {

    private int registerLang;
    private int accountId;

    public BaseRequestBody(int registerLang, int accountId) {
        this.registerLang = registerLang;
        this.accountId = accountId;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "BaseRequestBody{" +
                "registerLang=" + registerLang +
                ", accountId=" + accountId +
                '}';
    }
}
