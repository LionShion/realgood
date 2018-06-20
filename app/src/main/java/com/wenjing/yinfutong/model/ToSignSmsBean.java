package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${luoyingtao} on 2018\3\31 0031.
 */

public class ToSignSmsBean implements Parcelable{

    private int accountType;
    private String account;
    private String smsType;

    public ToSignSmsBean(int accountType, String account, String smsType) {
        this.accountType = accountType;
        this.account = account;
        this.smsType = smsType;
    }

    protected ToSignSmsBean(Parcel in) {
        accountType = in.readInt();
        account = in.readString();
        smsType = in.readString();
    }

    public static final Creator<ToSignSmsBean> CREATOR = new Creator<ToSignSmsBean>() {
        @Override
        public ToSignSmsBean createFromParcel(Parcel in) {
            return new ToSignSmsBean(in);
        }

        @Override
        public ToSignSmsBean[] newArray(int size) {
            return new ToSignSmsBean[size];
        }
    };

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

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(accountType);
        parcel.writeString(account);
        parcel.writeString(smsType);
    }
}
