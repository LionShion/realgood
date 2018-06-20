package com.wenjing.yinfutong.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/9/15 0015.
 */

public abstract class BaseTimerTextHandler extends Handler{
    public static final String TAG = "BaseTimerTextHandler";

    public static final int DEFAULT_SMSWAIT_USERAGAIN      = 60;//单位：秒

    public static final int WHAT_WAITING_RUN = 0;
    public static final int WHAT_WAITING_COMPLETE =1;

    private int total;
    private int temp;

    private boolean isRunning = true;

    public static final String DEFAULT_UNIT = "s" ;//单位 : 秒

    public BaseTimerTextHandler(int total) {
        this.total = total;
        temp = this.total;
    }

    public BaseTimerTextHandler() {
        total = DEFAULT_SMSWAIT_USERAGAIN;
        temp = this.total;
    }

    public void start(){
        sendEmptyMsgPerSec(WHAT_WAITING_RUN);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch(msg.what){
            case WHAT_WAITING_RUN:
                total--;
                //isRunning控制同步,避免算法完成回来  报空指针
                if(isRunning){
                    if(total<0){
                        sendEmptyMessage(WHAT_WAITING_COMPLETE);//不用加  延迟
                    }else {
                        waitingRun(total);
                        sendEmptyMsgPerSec(WHAT_WAITING_RUN);
                    }
                }

                break;
            case WHAT_WAITING_COMPLETE:
                total = temp;//重置等待时间
                waitingComplete();
                break;
        }

    }

    private void sendEmptyMsgPerSec(int what){
        sendEmptyMessageDelayed(what,1000);
    }

    public abstract void waitingRun(int leaveTime);
    public abstract void waitingComplete();
    protected String getUnit(){
        return DEFAULT_UNIT;
    }

    public void stop() {
        if(isRunning){
            isRunning = false;
        }
    }

}
