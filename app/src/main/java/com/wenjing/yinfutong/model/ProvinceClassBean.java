package com.wenjing.yinfutong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ${luoyingtao} on 2018\3\31 0031.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvinceClassBean {

    /**
     * id : 1
     * name : 浙江省
     * level : 1
     * parentId : 0
     * childList : [{"id":2,"name":"杭州市","level":2,"parentId":1,"childList":[]}]
     */

    private int id;
    private String name;
    private List<ChildListBean> childList;

    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildListBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildListBean> childList) {
        this.childList = childList;
    }

    public static class ChildListBean implements Parcelable{
        /**
         * id : 2
         * name : 杭州市
         * level : 2
         * parentId : 1
         * childList : []
         */

        private int id;
        private String name;
        private List<?> childList;
        private boolean isActive;

        public ChildListBean() {
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        protected ChildListBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
        }

        public static final Creator<ChildListBean> CREATOR = new Creator<ChildListBean>() {
            @Override
            public ChildListBean createFromParcel(Parcel in) {
                return new ChildListBean(in);
            }

            @Override
            public ChildListBean[] newArray(int size) {
                return new ChildListBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<?> getChildList() {
            return childList;
        }

        public void setChildList(List<?> childList) {
            this.childList = childList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(name);
        }
    }
}
