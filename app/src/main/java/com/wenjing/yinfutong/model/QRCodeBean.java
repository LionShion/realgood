package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wenjing on 2018/3/26.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class QRCodeBean {


    /**
     * msg : 成功
     * code : 0
     * data : http://192.168.1.206/images/QRcode/99a291634fed46678febf095c54bb803.jpg
     */

    private String msg;
    private int code;
    private String data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public QRCodeBean() {
    }
}
