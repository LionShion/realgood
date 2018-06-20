package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/22.
 */

public class PersonalBillsDetailsRequestBody {
    private String orderNo;
    private int orderType;
    private int registerLang;

    public PersonalBillsDetailsRequestBody(String orderNo, int orderType, int registerLang) {
        this.orderNo = orderNo;
        this.orderType = orderType;
        this.registerLang = registerLang;
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

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }
}
