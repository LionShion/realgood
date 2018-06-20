package com.wenjing.yinfutong.retrofit;

import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.api.MineApi;

/**
 * Created by wenjing on 2017/9/12.
 */

public class YinFuTongFactory {
    protected static final Object monitor = new Object();
    static HomeApi sHomeApi = null;
    static MineApi sMineApi = null;

    public static final boolean isDebug = true;

    public static HomeApi getHomeApiSingleton() {
        synchronized (monitor) {
            if (sHomeApi == null) {
                sHomeApi = YinFuTongRetrofit.getInstance().getHomeApi();
            }
            return sHomeApi;
        }
    }


    public static MineApi getMineApiSingleton() {
        synchronized (monitor) {
            if (sMineApi == null) {
                sMineApi = YinFuTongRetrofit.getInstance().getMineApi();
            }
            return sMineApi;
        }
    }


}
