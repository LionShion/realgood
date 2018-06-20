package com.wenjing.yinfutong.model.body;

/**
 * Created by Administrator on 2018\3\14 0014.
 */

public class LoginRequestBody {
    /**
     * accountType
     Number
     1
     1、手机号；2、邮箱地址；3、系统账号
     account
     String
     18513031475
     手机号（不加国际区号）
     password
     String
     e10adc3949ba59abbe56e057f20f883e
     md5加密后
     device
     String
     Acdd-44er4e4re45d544ds4
     loginLang
     Number
     1
     deviceType
     String
     vivo R15
     设备型号
     */
    private int accountType;
    private String account;
    private String password;
    private String device;
    private int loginLang;
    private String deviceType;

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

    public int getLoginLang() {
        return loginLang;
    }

    public void setLoginLang(int loginLang) {
        this.loginLang = loginLang;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
