package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\30 0030.
 */

public class RetrieveLPsdRequestBody {

    /**
     * cellphone : 15264781384
     * valiCode : 5646
     * newPWD : d4wd646da1d3
     * registerLang : 1
     */

    private String cellphone;
    private String valiCode;
    private String newPWD;
    private int registerLang;

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public String getNewPWD() {
        return newPWD;
    }

    public void setNewPWD(String newPWD) {
        this.newPWD = newPWD;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    @Override
    public String toString() {
        return "RetrieveLPsdRequestBody{" +
                "cellphone='" + cellphone + '\'' +
                ", valiCode='" + valiCode + '\'' +
                ", newPWD='" + newPWD + '\'' +
                ", registerLang=" + registerLang +
                '}';
    }
}
