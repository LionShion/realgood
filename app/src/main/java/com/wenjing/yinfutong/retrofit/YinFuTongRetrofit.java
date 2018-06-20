package com.wenjing.yinfutong.retrofit;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.api.MineApi;
import com.wenjing.yinfutong.retrofit.cookie.PersistentCookieStore;
import com.wenjing.yinfutong.retrofit.interceptor.AddCookiesInterceptor;
import com.wenjing.yinfutong.retrofit.interceptor.ReceivedCookiesInterceptor;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by wenjing on 2017/9/12.
 */

public class YinFuTongRetrofit {

    public static final String BASE_URL = "http://192.168.1.216:8080/";
//    public static final String BASE_URL = "http://192.168.1.29:8080/";

    private Context context = AppContext.context();

    final HomeApi homeApi;
    final MineApi mineApi;

    private static final long TIMEOUT = 15;

    public static YinFuTongRetrofit instance;

    public static YinFuTongRetrofit getInstance() {
        if(instance == null){
            synchronized (YinFuTongRetrofit.class){
                if(instance == null){
                    instance = new YinFuTongRetrofit();
                }
            }
        }
        return instance;
    }

    private YinFuTongRetrofit() {
        OkHttpClient.Builder okHttpClientBuilder = createDefaultOkHttpClientBuilder(context);
        okHttpClientBuilder.cookieJar(new CookiesManager());
        if (YinFuTongFactory.isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        OkHttpClient client = okHttpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        homeApi = retrofit.create(HomeApi.class);
        mineApi = retrofit.create(MineApi.class);
    }

    public HomeApi getHomeApi() {
        return homeApi;
    }

    public MineApi getMineApi() {
        return mineApi;
    }


    public static Cache createDefaultCache(Context context) {
        Cache cacheDir = new Cache(new File(AppContext.instance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
        return cacheDir;
    }


    public OkHttpClient.Builder createDefaultOkHttpClientBuilder(Context context) {

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(createDefaultCache(context))
                .addInterceptor(new ReceivedCookiesInterceptor(AppContext.instance()))
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new AddCookiesInterceptor(AppContext.instance()));
    }

    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(AppContext.instance());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie cookie : cookies) {
                    Log.d("CookieJar", "====================saveFromResponse: " + cookie);
                    AppContext.setAccessToken(String.valueOf(cookie));
                    cookieStore.add(url, cookie);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {

            Log.d("CookieJar", "====================loadForRequest: " + cookieStore.get(url));
            Log.d("CookieJar", "====================loadForRequest---url: " + url);
            Log.d("CookieJar", "loadForRequest---Cookies: " + AppContext.getAccesstoken());
            return cookieStore.get(url);
        }
    }
}
