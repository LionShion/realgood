package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wenjing on 2018/3/27.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantBillsListBean {


    /**
     * list : [{"receiveAmount":100,"receiveTime":"03-19 19:36","payerName":"刘德华","payerIcon":"201803221924005.jpg","orderNo":"2015464646"}]
     * count : 20
     * sumAmount : 514
     * totalPage : 5
     */

    private int count;
    private int sumAmount;
    private int totalPage;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
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
         * receiveAmount : 100
         * receiveTime : 03-19 19:36
         * payerName : 刘德华
         * payerIcon : 201803221924005.jpg
         * orderNo : 2015464646
         */

        private int receiveAmount;
        private String receiveTime;
        private String payerName;
        private String payerIcon;
        private String orderNo;
        private int orderType = 4;

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            receiveAmount = in.readInt();
            receiveTime = in.readString();
            payerName = in.readString();
            payerIcon = in.readString();
            orderNo = in.readString();
            orderType = in.readInt();
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

        public int getReceiveAmount() {
            return receiveAmount;
        }

        public void setReceiveAmount(int receiveAmount) {
            this.receiveAmount = receiveAmount;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getPayerName() {
            return payerName;
        }

        public void setPayerName(String payerName) {
            this.payerName = payerName;
        }

        public String getPayerIcon() {
            return payerIcon;
        }

        public void setPayerIcon(String payerIcon) {
            this.payerIcon = payerIcon;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(receiveAmount);
            parcel.writeString(receiveTime);
            parcel.writeString(payerName);
            parcel.writeString(payerIcon);
            parcel.writeString(orderNo);
            parcel.writeInt(orderType);
        }
    }

}
