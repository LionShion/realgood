package com.wenjing.yinfutong.retrofit.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wenjing.yinfutong.utils.TLog;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by yuexiaohui on 2017/4/28 13:40
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        HashSet<String> cookies = (HashSet<String>)
                PreferenceManager.getDefaultSharedPreferences(context).
                        getStringSet("PREF_COOKIES", new HashSet<String>());

        String lastCookie = null;

        System.out.println("http login packet request ================== ");

        List<String> headers = originalResponse.headers("Set-Cookie");
        TLog.log("cookies" , "headers : " + headers.toArray().toString() + " , cookies : " + cookies.toArray().toString());
        for (String header : headers) {
            TLog.e("cookies", "-----------------Interceptor拦截到的Cookie3:" + header);
            if(header.indexOf("JSESSIONID") != -1) {
                lastCookie = header;
            }
        }
        if (lastCookie != null) {
            cookies.clear();
            cookies.add(lastCookie);
        }

        SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
        memes.putStringSet("PREF_COOKIES", cookies).apply();
        memes.commit();
       

        TLog.log("cookies" , "cookies : " + cookies.toArray().toString());

        return originalResponse;
    }
}
