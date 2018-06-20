package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\3\26 0026.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantBean {

    /**
     * id : 17
     * customerId : 11
     * receiveTotalAmount : 0
     * receiveTotalFee : 0
     * fee : 1
     * merchantType : 3
     * address : 杭州余杭区
     * name : 我是你
     * cellphone : 110
     * idcard : null
     * nation : 1
     * title : 阿里巴巴
     * bankHolder :
     * bankName :
     * bankBranchName :
     * bankCardNo :
     * businessLicenseUrl :
     * businessPlaceUrl : 201803201639005.jpg
     * qrCodeUrl : http://192.168.1.206/images/MERCHANT8bf95c4dbec94c308dab191b0939cc8e17.jpg
     * status : 2
     * description : null
     * registerLang : null
     */

    private int id;
    private int customerId;
    private int receiveTotalAmount;
    private int receiveTotalFee;
    private int fee;
    private int merchantType;
    private String address;
    private String name;
    private String cellphone;
    private Object idcard;
    private int nation;
    private String title;
    private String bankHolder;
    private String bankName;
    private String bankBranchName;
    private String bankCardNo;
    private String businessLicenseUrl;
    private String businessPlaceUrl;
    private String qrCodeUrl;
    private int status;
    private Object description;
    private Object registerLang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getReceiveTotalAmount() {
        return receiveTotalAmount;
    }

    public void setReceiveTotalAmount(int receiveTotalAmount) {
        this.receiveTotalAmount = receiveTotalAmount;
    }

    public int getReceiveTotalFee() {
        return receiveTotalFee;
    }

    public void setReceiveTotalFee(int receiveTotalFee) {
        this.receiveTotalFee = receiveTotalFee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(int merchantType) {
        this.merchantType = merchantType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Object getIdcard() {
        return idcard;
    }

    public void setIdcard(Object idcard) {
        this.idcard = idcard;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBankHolder() {
        return bankHolder;
    }

    public void setBankHolder(String bankHolder) {
        this.bankHolder = bankHolder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getBusinessPlaceUrl() {
        return businessPlaceUrl;
    }

    public void setBusinessPlaceUrl(String businessPlaceUrl) {
        this.businessPlaceUrl = businessPlaceUrl;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(Object registerLang) {
        this.registerLang = registerLang;
    }

    @Override
    public String toString() {
        return "MerchantBean{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", receiveTotalAmount=" + receiveTotalAmount +
                ", receiveTotalFee=" + receiveTotalFee +
                ", fee=" + fee +
                ", merchantType=" + merchantType +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", idcard=" + idcard +
                ", nation=" + nation +
                ", title='" + title + '\'' +
                ", bankHolder='" + bankHolder + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", businessLicenseUrl='" + businessLicenseUrl + '\'' +
                ", businessPlaceUrl='" + businessPlaceUrl + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", status=" + status +
                ", description=" + description +
                ", registerLang=" + registerLang +
                '}';
    }
}
