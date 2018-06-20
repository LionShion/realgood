package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class SMSSendRequestBody {
    /**
     accountType
     Number
     1
     账户类型：1、手机号；2、邮箱
     account
     String
     13968194205
     根据account_type填写相应类型的手机号码 或者邮箱号码
     smsType
     String
     register
     发送消息类型：register：注册消息；login：找回登录密码；trade：充值登录密码；bindcard：绑卡：bindemail：绑email
     sign
     String
     参与签名的参数：accountType, account, smsType
     registerLang
     Number
     语言标识
     attributionId
     Number
     -1
     国际手机区号

     */
    private int accountType;
    private String account;
    private String smsType;
    private String sign;
    private int registerLang;
    private int attributionId;


    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public int getAttributionId() {
        return attributionId;
    }

    public void setAttributionId(int attributionId) {
        this.attributionId = attributionId;
    }

    @Override
    public String toString() {
        return "SMSSendRequestBody{" +
                "accountType=" + accountType +
                ", account='" + account + '\'' +
                ", smsType='" + smsType + '\'' +
                ", sign='" + sign + '\'' +
                ", registerLang=" + registerLang +
                ", attributionId=" + attributionId +
                '}';
    }
}
