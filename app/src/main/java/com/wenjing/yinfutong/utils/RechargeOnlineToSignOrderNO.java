package com.wenjing.yinfutong.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenjing on 2018/1/16.
 */

public class RechargeOnlineToSignOrderNO implements Parcelable {

    private int accountId;
    private double amount;

    public RechargeOnlineToSignOrderNO(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    protected RechargeOnlineToSignOrderNO(Parcel in) {
        accountId = in.readInt();
        amount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accountId);
        dest.writeDouble(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RechargeOnlineToSignOrderNO> CREATOR = new Creator<RechargeOnlineToSignOrderNO>() {
        @Override
        public RechargeOnlineToSignOrderNO createFromParcel(Parcel in) {
            return new RechargeOnlineToSignOrderNO(in);
        }

        @Override
        public RechargeOnlineToSignOrderNO[] newArray(int size) {
            return new RechargeOnlineToSignOrderNO[size];
        }
    };

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
