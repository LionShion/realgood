package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/20.
 */

public class PersonalBillsRequestBody {


    /**
     * accountId : 5
     * registerLang : 1
     * page : 0
     */

    private int accountId;
    private int registerLang;
    private int page;
    private int tradeType;
    private int userType;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public PersonalBillsRequestBody(int accountId, int registerLang, int page, int tradeType, int userType) {
        this.accountId = accountId;
        this.registerLang = registerLang;
        this.page = page;
        this.tradeType = tradeType;
        this.userType = userType;
    }


    @Override
    public String toString() {
        return "PersonalBillsRequestBody{" +
                "accountId=" + accountId +
                ", registerLang=" + registerLang +
                ", page=" + page +
                ", tradeType=" + tradeType +
                ", userType=" + userType +
                '}';
    }
}
