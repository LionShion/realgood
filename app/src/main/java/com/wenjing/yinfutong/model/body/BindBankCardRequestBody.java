package com.wenjing.yinfutong.model.body;

/**
 * Created by ${luoyingtao} on 2018\3\23 0023.
 */

public class BindBankCardRequestBody {
    /**
     * accountId
     Number
     客户编号
     accountName
     String
     姓名
     cardNo
     String
     银行卡号
     cellphone
     String
     预留手机号码
     valiCode
     String
     验证码
     registerLang
     Number
     1
     注册语言：1 - 中文，2 - 英文，3 - 柬埔寨
     IDNumber
     String
     420921198802106345
     身份证号
     attributionId
     Number
     1
     */
    private int accountId;
    private String accountName;
    private String cardNo;
    private String cellphone;
    private String valiCode;
    private int registerLang;
    private String idcard;
    private int attributionId;
    private String bankNo;
    private String bankName;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

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

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getAttributionId() {
        return attributionId;
    }

    public void setAttributionId(int attributionId) {
        this.attributionId = attributionId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BindBankCardRequestBody{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", valiCode='" + valiCode + '\'' +
                ", registerLang=" + registerLang +
                ", idcard='" + idcard + '\'' +
                ", attributionId=" + attributionId +
                ", bankNo='" + bankNo + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
