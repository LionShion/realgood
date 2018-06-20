package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\4\11 0011.
 */

public class MerchantFundRequestBody {

    /**
     * merchantId : 5
     * registerLang : 1
     */

    private int merchantId;
    private int registerLang;

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
}
