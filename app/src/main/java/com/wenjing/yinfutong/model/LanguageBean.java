package com.wenjing.yinfutong.model;

/**
 * Created by ${luoyingtao} on 2018\4\9 0009.
 */

public class LanguageBean {

    private int langFlag;
    private String langName;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LanguageBean(int langFlag, String langName) {
        this.langFlag = langFlag;
        this.langName = langName;
    }

    public int getLangFlag() {
        return langFlag;
    }

    public void setLangFlag(int langFlag) {
        this.langFlag = langFlag;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

}
