package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wenjing.yinfutong.utils.TLog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * https://www.cnblogs.com/mengdd/archive/2013/02/13/2911094.html
 *
 * 缓存文件的  存储 只能用 Serializable  用Parcelable一直报  java.io.NotSerializableException
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {


    /**
     * accountId : 111
     * roleFlag : 1
     * account : 100001
     * realName : 倪泗云
     * idcard : 320324198401015675
     * traderPasswordFlag : 1
     * cellphone : 绑定的手机号
     * email : 绑定的邮箱
     * usableAmount : 80
     * merchantId : 25
     * nickName : 卡座
     * photoUrl : 用户图像
     * accountType : 1
     */

    private int accountId;
    private int roleFlag;
    private String account;
    private String realName;
    private String idcard;
    private int traderPasswordFlag;
    private String cellphone;
    private String email;
    private double usableAmount;
    private int merchantId;
    private String nickName;
    private String photoUrl;
    private int accountType;

    public Customer() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(int roleFlag) {
        this.roleFlag = roleFlag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getTraderPasswordFlag() {
        return traderPasswordFlag;
    }

    public void setTraderPasswordFlag(int traderPasswordFlag) {
        this.traderPasswordFlag = traderPasswordFlag;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(double usableAmount) {
        this.usableAmount = usableAmount;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "accountId=" + accountId +
                ", roleFlag=" + roleFlag +
                ", account='" + account + '\'' +
                ", realName='" + realName + '\'' +
                ", idcard='" + idcard + '\'' +
                ", traderPasswordFlag=" + traderPasswordFlag +
                ", cellphone='" + cellphone + '\'' +
                ", email='" + email + '\'' +
                ", usableAmount=" + usableAmount +
                ", merchantId=" + merchantId +
                ", nickName='" + nickName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", accountType=" + accountType +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(accountId);
        out.writeInt(roleFlag);
        writeString(out , account);
        writeString(out , realName);
        writeString(out , idcard);
        out.writeInt(traderPasswordFlag);
        writeString(out , cellphone);
        writeString(out , email);
        out.writeDouble(usableAmount);
        out.writeInt(merchantId);
        writeString(out , nickName);
        writeString(out , photoUrl);
        out.writeInt(accountType);
        TLog.log(this , "我到过  自己的 writeObject()");
    }

    private void readObject(ObjectInputStream in) throws IOException {
        accountId = in.readInt();
        roleFlag = in.readInt();
        account = in.readUTF();
        realName = in.readUTF();
        idcard =in.readUTF();
        traderPasswordFlag = in.readInt();
        cellphone = in.readUTF();
        email = in.readUTF();
        usableAmount = in.readDouble();
        merchantId = in.readInt();
        nickName = in.readUTF();
        photoUrl = in.readUTF();
        accountType = in.readInt();
    }

    private void writeString(ObjectOutputStream out , String value) throws IOException {
        if(TextUtils.isEmpty(value)){
            value = "";
        }
        out.writeUTF(value);
    }
}