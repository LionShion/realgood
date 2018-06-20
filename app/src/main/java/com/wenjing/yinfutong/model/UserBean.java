package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2018\3\14 0014.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean{

    /**
     * accountId : 111
     * accountType : 1
     * account : 100001
     * realName : 倪泗云
     * idcard : 320324198401015675
     * traderPassword : 1231dcderezxdaer3543234dsa
     * cellphone : 13968194205
     * email :
     */

    private String accountId;
    private int accountType;
    private String account;
    private String realName;
    private String traderPassword;
    private String cellphone;
    private String email;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTraderPassword() {
        return traderPassword;
    }

    public void setTraderPassword(String traderPassword) {
        this.traderPassword = traderPassword;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "accountId='" + accountId + '\'' +
                ", accountType=" + accountType +
                ", account='" + account + '\'' +
                ", realName='" + realName + '\'' +
                ", traderPassword='" + traderPassword + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
