package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\15 0015.
 */

public class PersonalModifyRequestBody {


    /**
     * accountId : 客户编号
     * cellphone : 手机号码
     * email : 邮箱账号
     * nickName : 昵称
     * registerLang : 1
     * oldLoginPwd : 464131adada
     * newLoginPwd : adwad561a68d16
     * oldTradePwd : dawoijoad554d6a4d6
     * newTradePwd : dad48466d
     * valiCode : 46431
     */

    private int accountId;
    private String cellphone;
    private String email;
    private String nickName;
    private int registerLang;
    private String oldLoginPwd;
    private String newLoginPwd;
    private String oldTradePwd;
    private String newTradePwd;
    private String valiCode;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getOldLoginPwd() {
        return oldLoginPwd;
    }

    public void setOldLoginPwd(String oldLoginPwd) {
        this.oldLoginPwd = oldLoginPwd;
    }

    public String getNewLoginPwd() {
        return newLoginPwd;
    }

    public void setNewLoginPwd(String newLoginPwd) {
        this.newLoginPwd = newLoginPwd;
    }

    public String getOldTradePwd() {
        return oldTradePwd;
    }

    public void setOldTradePwd(String oldTradePwd) {
        this.oldTradePwd = oldTradePwd;
    }

    public String getNewTradePwd() {
        return newTradePwd;
    }

    public void setNewTradePwd(String newTradePwd) {
        this.newTradePwd = newTradePwd;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }
}
