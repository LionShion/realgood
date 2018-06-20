package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\28 0028.
 */

public class UnbindBankCardRequestBody {

    /**
     *accountId
     Number
     用户id
     bankId
     Number
     用户银行卡id
     registerLang
     Number

     */

    private int accountId;
    private int bankId;
    private int registerLang;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
}
