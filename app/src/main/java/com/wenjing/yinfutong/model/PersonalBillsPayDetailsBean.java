package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalBillsPayDetailsBean {

    /*
    *     "orderType": 1,
            "orderNo": "RGP2018041116230100004800000041",
            "nickName": "该上课了",
            "payerId": 48,
            "orderTimeStr": "2018-04-11 16:23",
            "merchantName": "阿里巴巴",
            "orderAmount": 77,
            "receiverId": 49,
            "payType": 0,
            "bonusAmount": 0,
            "payChannel": 0,
            "orderDesc": "Other",
            "status": 2

    * */
    private int orderType;
    private String orderNo;
    private String nickName;
    private int payerId;
    private String orderTimeStr;
    private String merchantName;
    private double orderAmount;
    private int receiverId;
    private int payType;
    private double bonusAmount;
    private int payChannel;
    private String orderDesc;
    private int status;
    private double receiveFee;

    public PersonalBillsPayDetailsBean() {
    }

    public double getReceiveFee() {
        return receiveFee;
    }

    public void setReceiveFee(double receiveFee) {
        this.receiveFee = receiveFee;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
