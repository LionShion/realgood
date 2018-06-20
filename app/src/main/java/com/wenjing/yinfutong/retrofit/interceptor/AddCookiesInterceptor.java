package com.wenjing.yinfutong.retrofit.interceptor;

import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.wenjing.yinfutong.utils.TLog;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuexiaohui on 2017/4/28 13:39
 */

public class AddCookiesInterceptor implements Interceptor {
    public static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        System.out.println("====Cookie:" + "++++++++++");
        HashSet<String> preferences = (HashSet<String>) PreferenceManager
                .getDefaultSharedPreferences(context)
                .getStringSet(PREF_COOKIES, new HashSet<String>());

        String lastCookie = null;
        for (String cookie : preferences) {
            TLog.log("AddCookiesInterceptor","cookie : " + cookie);
            builder.addHeader("Cookie", cookie);
        }
        if (lastCookie != null) {
            builder.addHeader("Cookie", lastCookie);
        }
        /*
        * 415是HTTP协议的状态码，415的含义是不支持的媒体类型(Unsupported media type)，
        * 检查是否在POST请求中加入了header，header中是否包含了正确的Content-Type。
        * */

        TLog.log("cookies","preferences : " + preferences.toArray().toString());

        builder.addHeader("Content-Type","application/json");
        getUUID(builder);

        return chain.proceed(builder.build());
    }
    private void getUUID(Request.Builder builder) {
        //获取设备号
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);

        builder.addHeader("UUID", ANDROID_ID);
    }
}
