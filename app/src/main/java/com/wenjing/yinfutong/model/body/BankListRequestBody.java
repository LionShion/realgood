package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\27 0027.
 */

public class BankListRequestBody {

    private int accountId;
    private int registerLang;

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
}
