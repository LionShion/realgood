package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/29.
 *
 * 分销商   线下充值没有金额
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class QRCodeResultBean implements Parcelable{

        /**
         * id : 1
         * name :
         * realName :
         * desc :
         */

    private String desc;
    private int id;
    private String name;
    private String realName;
    private double rebate;
    private String status;
    private int distributorId;

    public QRCodeResultBean() {
    }

    protected QRCodeResultBean(Parcel in) {
        desc = in.readString();
        id = in.readInt();
        name = in.readString();
        realName = in.readString();
        rebate = in.readDouble();
        status = in.readString();
        distributorId = in.readInt();
    }

    public static final Creator<QRCodeResultBean> CREATOR = new Creator<QRCodeResultBean>() {
        @Override
        public QRCodeResultBean createFromParcel(Parcel in) {
            return new QRCodeResultBean(in);
        }

        @Override
        public QRCodeResultBean[] newArray(int size) {
            return new QRCodeResultBean[size];
        }
    };

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public double getRebate() {
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        parcel.writeString(desc);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(realName);
        parcel.writeDouble(rebate);
        parcel.writeString(status);
        parcel.writeInt(distributorId);
    }

    @Override
    public String toString() {
        return "QRCodeResultBean{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", realName='" + realName + '\'' +
                ", rebate=" + rebate +
                ", status='" + status + '\'' +
                ", distributorId=" + distributorId +
                '}';
    }
}
