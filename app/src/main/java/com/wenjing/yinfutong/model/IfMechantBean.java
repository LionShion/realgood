package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/4/2.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IfMechantBean {

    /**
     * merchantId : 48
     * name : 6株婆婆
     * cellphone : 18625916235
     * title : 35哈哈哈
     * status : 2
     */

    private int merchantId;
    private String name;
    private String cellphone;
    private String title;
    private int status;

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
