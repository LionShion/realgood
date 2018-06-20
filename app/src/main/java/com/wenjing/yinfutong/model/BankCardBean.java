package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\3\23 0023.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankCardBean implements Parcelable{
    public BankCardBean() {
    }

    /**
     * bankId : 3
     * accountId : 11
     * accountName : null
     * bankNo : 01020000
     * bankName : 工商银行
     * cardNo : 1234567891234567
     * cellphone : null
     * valiCode : null
     * registerLang : null
     * country : null
     * iDNumber : null
     * perLimit : 88000
     * dayLimit : 88000
     * monLimit : 88000
     * bankType :
     * color : "16ca80"
     * imgUrl : ""
     */

    private int bankId;
    private int accountId;
    private String accountName;
    private String bankNo;
    private String bankName;
    private String cardNo;
    private String cellphone;
    private String valiCode;
    private int registerLang;
    private int country;
    private String iDNumber;
    private int perLimit;
    private int dayLimit;
    private int monLimit;
    private int bankType;
    private String color;
    private String imgUrl;

    private boolean isActive;

    protected BankCardBean(Parcel in) {
        bankId = in.readInt();
        accountId = in.readInt();
        accountName = in.readString();
        bankNo = in.readString();
        bankName = in.readString();
        cardNo = in.readString();
        cellphone = in.readString();
        valiCode = in.readString();
        registerLang = in.readInt();
        country = in.readInt();
        iDNumber = in.readString();
        perLimit = in.readInt();
        dayLimit = in.readInt();
        monLimit = in.readInt();
        bankType = in.readInt();
        color = in.readString();
        imgUrl = in.readString();
        isActive = in.readByte() != 0;
    }

    public static final Creator<BankCardBean> CREATOR = new Creator<BankCardBean>() {
        @Override
        public BankCardBean createFromParcel(Parcel in) {
            return new BankCardBean(in);
        }

        @Override
        public BankCardBean[] newArray(int size) {
            return new BankCardBean[size];
        }
    };

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getiDNumber() {
        return iDNumber;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public int getPerLimit() {
        return perLimit;
    }

    public void setPerLimit(int perLimit) {
        this.perLimit = perLimit;
    }

    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    public int getMonLimit() {
        return monLimit;
    }

    public void setMonLimit(int monLimit) {
        this.monLimit = monLimit;
    }

    public int getBankType() {
        return bankType;
    }

    public void setBankType(int bankType) {
        this.bankType = bankType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "BankCardBean{" +
                "bankId=" + bankId +
                ", accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", valiCode='" + valiCode + '\'' +
                ", registerLang=" + registerLang +
                ", country=" + country +
                ", iDNumber='" + iDNumber + '\'' +
                ", perLimit=" + perLimit +
                ", dayLimit=" + dayLimit +
                ", monLimit=" + monLimit +
                ", bankType=" + bankType +
                ", color='" + color + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bankId);
        parcel.writeInt(accountId);
        parcel.writeString(accountName);
        parcel.writeString(bankNo);
        parcel.writeString(bankName);
        parcel.writeString(cardNo);
        parcel.writeString(cellphone);
        parcel.writeString(valiCode);
        parcel.writeInt(registerLang);
        parcel.writeInt(country);
        parcel.writeString(iDNumber);
        parcel.writeInt(perLimit);
        parcel.writeInt(dayLimit);
        parcel.writeInt(monLimit);
        parcel.writeInt(bankType);
        parcel.writeString(color);
        parcel.writeString(imgUrl);
        parcel.writeByte((byte) (isActive ? 1 : 0));
    }
}
