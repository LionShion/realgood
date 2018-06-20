package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuexiaohui on 2017/5/16
 */

public class BaseResult<T> implements Parcelable {


    private String error;
    private int code;
    private String msg;
    private boolean succ;
    private String errors;
    private T data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeByte(this.succ ? (byte) 1 : (byte) 0);
        dest.writeString(this.errors);
    }

    public BaseResult() {
    }

    protected BaseResult(Parcel in) {
        this.error = in.readString();
        this.code = in.readInt();
        this.msg = in.readString();
        this.succ = in.readByte() != 0;
        this.errors = in.readString();
    }

    public static final Creator<BaseResult> CREATOR = new Creator<BaseResult>() {
        @Override
        public BaseResult createFromParcel(Parcel source) {
            return new BaseResult(source);
        }

        @Override
        public BaseResult[] newArray(int size) {
            return new BaseResult[size];
        }
    };
}
