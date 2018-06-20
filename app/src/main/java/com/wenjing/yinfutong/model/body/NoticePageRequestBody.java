package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/4/18.
 */

public class NoticePageRequestBody {
    private int startPage;
    private int roleFlag;
    private int registerLang;

    public NoticePageRequestBody(int startPage, int roleFlag, int registerLang) {
        this.startPage = startPage;
        this.roleFlag = roleFlag;
        this.registerLang = registerLang;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(int roleFlag) {
        this.roleFlag = roleFlag;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }
}
