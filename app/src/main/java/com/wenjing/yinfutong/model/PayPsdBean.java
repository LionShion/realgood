package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\5\22 0022.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayPsdBean {

    /**
     * count : 4
     */

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PayPsdBean{" +
                "count=" + count +
                '}';
    }
}
