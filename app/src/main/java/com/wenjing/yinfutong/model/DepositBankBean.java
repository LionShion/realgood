package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ${luoyingtao} on 2018\3\31 0031.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositBankBean{

    /**
     * paybank : [{"id":1,"bankNo":"01020000","bankName":"工商银行","singleLimit":88000,"dayLimit":88000,"monthLimit":88000,"payChannel":0,"code":"cc5260","imgUrl":"http://192.168.1.206/images/banklogo/1.png","type":0}]
     * type : 0
     */

    private int type;
    private List<PaybankBean> paybank;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PaybankBean> getPaybank() {
        return paybank;
    }

    public void setPaybank(List<PaybankBean> paybank) {
        this.paybank = paybank;
    }

    public static class PaybankBean implements Parcelable{
        /**
         * id : 1
         * bankNo : 01020000
         * bankName : 工商银行
         * singleLimit : 88000
         * dayLimit : 88000
         * monthLimit : 88000
         * payChannel : 0
         * code : cc5260
         * imgUrl : http://192.168.1.206/images/banklogo/1.png
         * type : 0
         */

        private int id;
        private String bankNo;
        private String bankName;
        private int singleLimit;
        private int dayLimit;
        private int monthLimit;
        private int payChannel;
        private String code;
        private String imgUrl;
        private int type;

        public PaybankBean() {
        }

        protected PaybankBean(Parcel in) {
            id = in.readInt();
            bankNo = in.readString();
            bankName = in.readString();
            singleLimit = in.readInt();
            dayLimit = in.readInt();
            monthLimit = in.readInt();
            payChannel = in.readInt();
            code = in.readString();
            imgUrl = in.readString();
            type = in.readInt();
        }

        public static final Creator<PaybankBean> CREATOR = new Creator<PaybankBean>() {
            @Override
            public PaybankBean createFromParcel(Parcel in) {
                return new PaybankBean(in);
            }

            @Override
            public PaybankBean[] newArray(int size) {
                return new PaybankBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSingleLimit() {
            return singleLimit;
        }

        public void setSingleLimit(int singleLimit) {
            this.singleLimit = singleLimit;
        }

        public int getDayLimit() {
            return dayLimit;
        }

        public void setDayLimit(int dayLimit) {
            this.dayLimit = dayLimit;
        }

        public int getMonthLimit() {
            return monthLimit;
        }

        public void setMonthLimit(int monthLimit) {
            this.monthLimit = monthLimit;
        }

        public int getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(int payChannel) {
            this.payChannel = payChannel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(bankNo);
            parcel.writeString(bankName);
            parcel.writeInt(singleLimit);
            parcel.writeInt(dayLimit);
            parcel.writeInt(monthLimit);
            parcel.writeInt(payChannel);
            parcel.writeString(code);
            parcel.writeString(imgUrl);
            parcel.writeInt(type);
        }

        @Override
        public String toString() {
            return "PaybankBean{" +
                    "id=" + id +
                    ", bankNo='" + bankNo + '\'' +
                    ", bankName='" + bankName + '\'' +
                    ", singleLimit=" + singleLimit +
                    ", dayLimit=" + dayLimit +
                    ", monthLimit=" + monthLimit +
                    ", payChannel=" + payChannel +
                    ", code='" + code + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DepositBankBean{" +
                "type=" + type +
                ", paybank=" + paybank +
                '}';
    }
}
