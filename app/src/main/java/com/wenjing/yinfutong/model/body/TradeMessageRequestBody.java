package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/4/18.
 */

public class TradeMessageRequestBody {
    private int registerLang;
    private int page;

    public TradeMessageRequestBody(int registerLang, int page) {
        this.registerLang = registerLang;
        this.page = page;
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
