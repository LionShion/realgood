package com.wenjing.yinfutong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ${luoyingtao} on 2018\4\27 0027.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstantMsgBean {

    /**
     * msgCount : 0
     * noticeCount : 0
     */

    private int msgCount;
    private int noticeCount;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(int noticeCount) {
        this.noticeCount = noticeCount;
    }

    @Override
    public String toString() {
        return "InstantMsgBean{" +
                "msgCount=" + msgCount +
                ", noticeCount=" + noticeCount +
                '}';
    }
}
