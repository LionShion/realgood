package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\4\10 0010.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonPbBean implements Parcelable{

    /**
     * answer : 点击“我的”页面后，跳出注册/登录页面，点击“注册”按钮，并填写手机号码，获取验证码，设置登录密码即可完成注册。
     * ask : 如何注册？
     * faqId : 2
     */

    private String answer;
    private String ask;
    private int faqId;

    public CommonPbBean() {
    }

    protected CommonPbBean(Parcel in) {
        answer = in.readString();
        ask = in.readString();
        faqId = in.readInt();
    }

    public static final Creator<CommonPbBean> CREATOR = new Creator<CommonPbBean>() {
        @Override
        public CommonPbBean createFromParcel(Parcel in) {
            return new CommonPbBean(in);
        }

        @Override
        public CommonPbBean[] newArray(int size) {
            return new CommonPbBean[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public int getFaqId() {
        return faqId;
    }

    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }

    @Override
    public String toString() {
        return "CommonPbBean{" +
                "answer='" + answer + '\'' +
                ", ask='" + ask + '\'' +
                ", faqId=" + faqId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(answer);
        parcel.writeString(ask);
        parcel.writeInt(faqId);
    }
}
