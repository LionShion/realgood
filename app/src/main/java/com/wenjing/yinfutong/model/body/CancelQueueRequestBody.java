package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\5\4 0004.
 */

public class CancelQueueRequestBody {

    private int accountId;
    private int distributorId;


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    @Override
    public String toString() {
        return "CancelQueueRequestBody{" +
                "accountId=" + accountId +
                ", distributorId=" + distributorId +
                '}';
    }
}
