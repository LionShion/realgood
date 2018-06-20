package com.wenjing.yinfutong.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenjing on 2018/1/16.
 */

public class ToSignOrderNO implements Parcelable {

    private int accountId;
    private int merchantId;
    private double amount;

    public ToSignOrderNO(int accountId, int merchantId, double amount) {
        this.accountId = accountId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    protected ToSignOrderNO(Parcel in) {
        accountId = in.readInt();
        merchantId = in.readInt();
        amount = in.readDouble();
    }

    public static final Creator<ToSignOrderNO> CREATOR = new Creator<ToSignOrderNO>() {
        @Override
        public ToSignOrderNO createFromParcel(Parcel in) {
            return new ToSignOrderNO(in);
        }

        @Override
        public ToSignOrderNO[] newArray(int size) {
            return new ToSignOrderNO[size];
        }
    };

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(accountId);
        parcel.writeInt(merchantId);
        parcel.writeDouble(amount);
    }
}
