package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/28.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateBean {

    /**
     * exchangeRate : 当日汇率
     */

    private String exchangeRate;

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
