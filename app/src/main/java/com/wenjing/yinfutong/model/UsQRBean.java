package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\4\21 0021.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsQRBean implements Parcelable{

    /**
     * realName : 我是真的
     * amount : 1
     * rebate : 0
     * name : cffc
     * id : 56
     * desc : 其他
     */

    private String realName;
    private double amount;
    private double rebate;
    private String name;
    private int id;
    private String desc;

    public UsQRBean() {
    }

    protected UsQRBean(Parcel in) {
        realName = in.readString();
        amount = in.readDouble();
        rebate = in.readDouble();
        name = in.readString();
        id = in.readInt();
        desc = in.readString();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRebate() {
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static final Creator<UsQRBean> CREATOR = new Creator<UsQRBean>() {
        @Override
        public UsQRBean createFromParcel(Parcel in) {
            return new UsQRBean(in);
        }

        @Override
        public UsQRBean[] newArray(int size) {
            return new UsQRBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(realName);
        parcel.writeDouble(amount);
        parcel.writeDouble(rebate);
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(desc);
    }
}
