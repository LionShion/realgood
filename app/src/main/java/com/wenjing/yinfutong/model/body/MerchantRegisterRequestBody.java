package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\26 0026.
 */

public class MerchantRegisterRequestBody {

    /**
     * registerLang : 1
     * name : 张伟
     * cellphone : 15264781384
     * title : 华润万家
     * address : 浙江省杭州市江干区凯迪银座13楼
     * businessPlacePhoto : {}
     * accountId : 28
     */

    private int registerLang;
    private String name;
    private String cellphone;
    private String title;
    private String address;
    private int accountId;

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "MerchantRegisterRequestBody{" +
                "registerLang=" + registerLang +
                ", name='" + name + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
