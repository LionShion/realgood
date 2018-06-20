package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/27.
 */

public class MerchantBillsRequestBody {
    private int merchantId;
    private int registerLang;
    private int page;

    public MerchantBillsRequestBody(int merchantId, int registerLang, int page) {
        this.merchantId = merchantId;
        this.registerLang = registerLang;
        this.page = page;
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
