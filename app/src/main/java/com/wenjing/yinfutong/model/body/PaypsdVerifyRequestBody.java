package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\4\23 0023.
 */

public class PaypsdVerifyRequestBody {

    /**
     * accountId : 111
     * password : asdaewdsafeafwea
     */

    private int accountId;
    private String password;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PaypsdVerifyRequestBody{" +
                "accountId=" + accountId +
                ", password='" + password + '\'' +
                '}';
    }
}
