package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class RegisterRequestBody {
    /**
     accountType
     Number
     1
     1、手机号；2、邮箱地址；3、系统账号
     account
     String
     13968194205
     手机号（不加国际区号）
     password
     String
     26696cdb2364b55a1363ab8d94ea7548
     MD5加密后的字符串
     device
     String
     Acdd-44er4e4re45d544ds4
     设备信息
     registerLang
     Number
     1
     注册语言：1中文；2英文；3柬埔寨文
     attributionId
     Number
     -1
     国际区号Id（手机号注册必传）

     */

    private int accountType;
    private String account;
    private String password;
    private String device;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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
        return "RegisterRequestBody{" +
                "accountType=" + accountType +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", device='" + device + '\'' +
                ", registerLang=" + registerLang +
                ", attributionId=" + attributionId +
                '}';
    }
}
