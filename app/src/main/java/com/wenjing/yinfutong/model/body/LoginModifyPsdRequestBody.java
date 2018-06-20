package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\27 0027.
 */

public class LoginModifyPsdRequestBody {

    /**
     * accountId
     Number
     客户编号
     oldPwd
     String
     旧密码
     newPwd
     String
     新密码

     */
    private int accountId;
    private String oldPwd;
    private String newPwd;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
