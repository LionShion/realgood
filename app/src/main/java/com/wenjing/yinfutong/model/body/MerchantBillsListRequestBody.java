package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/27.
 */

public class MerchantBillsListRequestBody {
    private String day;
    private String month;
    private String flag;
    private int merchantId;
    private int registerLang;
    private int page;

    public MerchantBillsListRequestBody(String day, String month, String flag, int merchantId, int registerLang, int page) {
        this.day = day;
        this.month = month;
        this.flag = flag;
        this.merchantId = merchantId;
        this.registerLang = registerLang;
        this.page = page;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
