package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wenjing on 2018/3/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalBillsBean {


    private int totalPage;
    private int total;
    private List<ListBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return totalPage;
    }

    public void setTotal(int total) {
        this.totalPage = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ListBean implements Parcelable {
        /**
         * accountId : null
         * registerLang : null
         * accountAmount : 878
         * accountTime : 05-21 15:52
         * orderType : 3
         * orderNo : RGW2018032116002200000500000019
         * orderTypeName : 提现
         * bonusAmount : null
         * merchantName : null
         * distributorName : 486434464616
         * bankInfo : null
         * month : 2018年05月
         * income : 0
         * pay : 878
         * page : null
         * tradeType : null
         * userType : null
         * time : 2018-05-21 15:52:09
         */

        private int accountId;
        private int registerLang;
        private double accountAmount;
        private String accountTime;
        private int orderType;
        private String orderNo;
        private String orderTypeName;
        private double bonusAmount;
        private String merchantName;
        private String distributorName;
        private String bankInfo;
        private String month;
        private double income;
        private double pay;
        private int page;
        private int tradeType;
        private int userType;
        private String time;
        private String payerIcon;

        public ListBean() {
        }

        public ListBean(Parcel in) {
            accountId = in.readInt();
            registerLang = in.readInt();
            accountAmount = in.readDouble();
            accountTime = in.readString();
            orderType = in.readInt();
            orderNo = in.readString();
            orderTypeName = in.readString();
            bonusAmount = in.readDouble();
            merchantName = in.readString();
            distributorName = in.readString();
            bankInfo = in.readString();
            month = in.readString();
            income = in.readDouble();
            pay = in.readDouble();
            page = in.readInt();
            tradeType = in.readInt();
            userType = in.readInt();
            time = in.readString();
            payerIcon = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getRegisterLang() {
            return registerLang;
        }

        public void setRegisterLang(int registerLang) {
            this.registerLang = registerLang;
        }

        public double getAccountAmount() {
            return accountAmount;
        }

        public void setAccountAmount(double accountAmount) {
            this.accountAmount = accountAmount;
        }

        public String getAccountTime() {
            return accountTime;
        }

        public void setAccountTime(String accountTime) {
            this.accountTime = accountTime;
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

        public String getOrderTypeName() {
            return orderTypeName;
        }

        public void setOrderTypeName(String orderTypeName) {
            this.orderTypeName = orderTypeName;
        }

        public double getBonusAmount() {
            return bonusAmount;
        }

        public void setBonusAmount(double bonusAmount) {
            this.bonusAmount = bonusAmount;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getDistributorName() {
            return distributorName;
        }

        public void setDistributorName(String distributorName) {
            this.distributorName = distributorName;
        }

        public String getBankInfo() {
            return bankInfo;
        }

        public void setBankInfo(String bankInfo) {
            this.bankInfo = bankInfo;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public double getPay() {
            return pay;
        }

        public void setPay(double pay) {
            this.pay = pay;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPayerIcon() {
            return payerIcon;
        }

        public void setPayerIcon(String payerIcon) {
            this.payerIcon = payerIcon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(accountId);
            parcel.writeInt(registerLang);
            parcel.writeDouble(accountAmount);
            parcel.writeString(accountTime);
            parcel.writeInt(orderType);
            parcel.writeString(orderNo);
            parcel.writeString(orderTypeName);
            parcel.writeDouble(bonusAmount);
            parcel.writeString(merchantName);
            parcel.writeString(distributorName);
            parcel.writeString(bankInfo);
            parcel.writeString(month);
            parcel.writeDouble(income);
            parcel.writeDouble(pay);
            parcel.writeInt(page);
            parcel.writeInt(tradeType);
            parcel.writeInt(userType);
            parcel.writeString(time);
            parcel.writeString(payerIcon);
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "accountId=" + accountId +
                    ", registerLang=" + registerLang +
                    ", accountAmount=" + accountAmount +
                    ", accountTime='" + accountTime + '\'' +
                    ", orderType=" + orderType +
                    ", orderNo='" + orderNo + '\'' +
                    ", orderTypeName='" + orderTypeName + '\'' +
                    ", bonusAmount=" + bonusAmount +
                    ", merchantName='" + merchantName + '\'' +
                    ", distributorName='" + distributorName + '\'' +
                    ", bankInfo='" + bankInfo + '\'' +
                    ", month='" + month + '\'' +
                    ", income=" + income +
                    ", pay=" + pay +
                    ", page=" + page +
                    ", tradeType=" + tradeType +
                    ", userType=" + userType +
                    ", time='" + time + '\'' +
                    ", payerIcon='" + payerIcon + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PersonalBillsBean{" +
                "totalPage=" + totalPage +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
