package com.wenjing.yinfutong.model.body;

/**
 * Created by wenjing on 2018/3/29.
 */

public class QRCodeRequestBody {
    private int id;
    private int roleType;
    private int registerLang;

    public QRCodeRequestBody(int id, int roleType, int registerLang) {
        this.id = id;
        this.roleType = roleType;
        this.registerLang = registerLang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getRegisterLang() {
        return registerLang;
    }

    public void setRegisterLang(int registerLang) {
        this.registerLang = registerLang;
    }

    @Override
    public String toString() {
        return "QRCodeRequestBody{" +
                "id=" + id +
                ", roleType=" + roleType +
                ", registerLang=" + registerLang +
                '}';
    }
}
