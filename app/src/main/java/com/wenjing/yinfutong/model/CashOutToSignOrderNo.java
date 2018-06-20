package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${luoyingtao} on 2018\5\23 0023.
 */

public class CashOutToSignOrderNo implements Parcelable{
    private double amount;
    private int accountId;
    private String bankInfo;

    public CashOutToSignOrderNo(double amount, int accountId, String bankInfo) {
        this.amount = amount;
        this.accountId = accountId;
        this.bankInfo = bankInfo;
    }

    protected CashOutToSignOrderNo(Parcel in) {
        amount = in.readDouble();
        accountId = in.readInt();
        bankInfo = in.readString();
    }

    public static final Creator<CashOutToSignOrderNo> CREATOR = new Creator<CashOutToSignOrderNo>() {
        @Override
        public CashOutToSignOrderNo createFromParcel(Parcel in) {
            return new CashOutToSignOrderNo(in);
        }

        @Override
        public CashOutToSignOrderNo[] newArray(int size) {
            return new CashOutToSignOrderNo[size];
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

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(amount);
        parcel.writeInt(accountId);
        parcel.writeString(bankInfo);
    }
}
