package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${luoyingtao} on 2018\5\23 0023.
 */

public class RechargeOfflineToSignOrderNo implements Parcelable{
    private int accountId;
    private int distributorId;
    private double amount;


    public RechargeOfflineToSignOrderNo(int accountId, int distributorId, double amount) {
        this.accountId = accountId;
        this.distributorId = distributorId;
        this.amount = amount;
    }

    protected RechargeOfflineToSignOrderNo(Parcel in) {
        accountId = in.readInt();
        distributorId = in.readInt();
        amount = in.readDouble();
    }

    public static final Creator<RechargeOfflineToSignOrderNo> CREATOR = new Creator<RechargeOfflineToSignOrderNo>() {
        @Override
        public RechargeOfflineToSignOrderNo createFromParcel(Parcel in) {
            return new RechargeOfflineToSignOrderNo(in);
        }

        @Override
        public RechargeOfflineToSignOrderNo[] newArray(int size) {
            return new RechargeOfflineToSignOrderNo[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(accountId);
        parcel.writeInt(distributorId);
        parcel.writeDouble(amount);
    }

    @Override
    public String toString() {
        return "RechargeOfflineToSignOrderNo{" +
                "accountId=" + accountId +
                ", distributorId=" + distributorId +
                ", amount=" + amount +
                '}';
    }
}
