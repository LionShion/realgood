package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.constant.RequestConstant;

/**
 * Created by ${luoyingtao} on 2018\4\3 0003.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionBean implements Parcelable {

    /**
     * id : 1
     * supportArea : 中国大陆
     * supportPre : 86
     */

    private int id;
    private String supportArea;
    private int supportPre;
    private boolean isActive;

    public static RegionBean getDefault(){
        return new RegionBean(RequestConstant.REGION_DEFAULT_ID,
                AppContext.instance().getResources().getString(RequestConstant.REGION_DEFAULTNAME_RESID),
                RequestConstant.REGION_DEFAULT_CODE);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public RegionBean() {
    }

    public RegionBean(int id, String supportArea, int supportPre) {
        this.id = id;
        this.supportArea = supportArea;
        this.supportPre = supportPre;
    }

    protected RegionBean(Parcel in) {
        id = in.readInt();
        supportArea = in.readString();
        supportPre = in.readInt();
    }

    public static final Creator<RegionBean> CREATOR = new Creator<RegionBean>() {
        @Override
        public RegionBean createFromParcel(Parcel in) {
            return new RegionBean(in);
        }

        @Override
        public RegionBean[] newArray(int size) {
            return new RegionBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupportArea() {
        return supportArea;
    }

    public void setSupportArea(String supportArea) {
        this.supportArea = supportArea;
    }

    public int getSupportPre() {
        return supportPre;
    }

    public void setSupportPre(int supportPre) {
        this.supportPre = supportPre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(supportArea);
        parcel.writeInt(supportPre);
    }
}
