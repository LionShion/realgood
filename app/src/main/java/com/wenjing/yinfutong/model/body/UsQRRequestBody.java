package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\4\21 0021.
 */

public class UsQRRequestBody {

    private int registerLang;
    private String orderNo;

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "UsQRBean{" +
                "registerLang=" + registerLang +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
