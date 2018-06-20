package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class SMSVerifyRequestBody {
    /**
     accountType
     Number
     1
     1 短信验证码 2 邮箱验证码
     account
     String
     8613968194205
     手机号（前缀国际区号），邮箱
     smsCode
     String
     123456
     短信验证码
     smsType
     String
     register
     发送消息类型：register：注册消息；login：找回登录密码；trade：充值登录密码；bindcard：绑卡：bindemail：绑email
     userName
     String
     用户名：找回交易密码验证时需传递这个参数
     idcard
     String
     证件号：找回交易密码验证时需传递这个参数
     */

    private int accountType;
    private String account;
    private String smsCode;
    private String smsType;
    private String userName;
    private String idcard;

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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public String toString() {
        return "SMSVerifyRequestBody{" +
                "accountType=" + accountType +
                ", account='" + account + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", smsType='" + smsType + '\'' +
                ", userName='" + userName + '\'' +
                ", idcard='" + idcard + '\'' +
                '}';
    }
}
