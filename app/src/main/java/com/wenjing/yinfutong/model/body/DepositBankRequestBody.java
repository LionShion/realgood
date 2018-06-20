package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\4\3 0003.
 */

public class DepositBankRequestBody {
    /**
     * type
     Number
     0
     0:银联卡 1：非银联卡（境外卡）
     registerLang
     Number

     */

    private int type;
    private int registerLang;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }
}
