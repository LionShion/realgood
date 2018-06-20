package com.wenjing.yinfutong.utils;


import com.wenjing.yinfutong.AppContext;

public class UserAgent {

    public static String getUserAgent(AppContext appContext) {
        StringBuilder ua = new StringBuilder("yinke");
        ua.append('/' + appContext.getPackageInfo().versionName);// App版本 appContext.getPackageInfo().versionCode
        ua.append("/Android");// 手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
        ua.append("/" + appContext.DeviceId());// 客户端唯一标识

//        TLog.error("user_agent信息------------->", ua.toString());
        return ua.toString();
    }
}
