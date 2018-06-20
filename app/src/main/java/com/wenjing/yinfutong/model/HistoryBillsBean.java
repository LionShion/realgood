package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by wenjing on 2018/3/26.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryBillsBean {

    /**
     * totalPage : 1
     * type : 1
     * list : [{"date":"2018-04-23","receiveAmount":323,"count":1},{"date":"2018-04-22","receiveAmount":534,"count":2},{"date":"2018-04-21","receiveAmount":78,"count":1},{"date":"2018-04-20","receiveAmount":63.3,"count":1},{"date":"2018-03-24","receiveAmount":1011,"count":1},{"date":"2018-03-23","receiveAmount":1300,"count":2},{"date":"2018-03-20","receiveAmount":88.3,"count":2},{"date":"2018-03-19","receiveAmount":135,"count":1}]
     */

    private int totalPage;
    private int type;
    private List<ListBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        public ListBean() {
        }

        /**
         * date : 2018-04-23
         * receiveAmount : 323
         * count : 1
         */

        private String date;
        private String month;
        private int receiveAmount;
        private int count;

        protected ListBean(Parcel in) {
            date = in.readString();
            month = in.readString();
            receiveAmount = in.readInt();
            count = in.readInt();
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

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getReceiveAmount() {
            return receiveAmount;
        }

        public void setReceiveAmount(int receiveAmount) {
            this.receiveAmount = receiveAmount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(date);
            parcel.writeString(month);
            parcel.writeInt(receiveAmount);
            parcel.writeInt(count);
        }
    }

}
