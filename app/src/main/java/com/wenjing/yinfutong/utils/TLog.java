package com.wenjing.yinfutong.utils;

import android.util.Log;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;


public class TLog {
    public static boolean DEBUG = AppContext.resources().getBoolean(R.bool.isDebug);

    public TLog() {
    }

    public static final void analytics(String LOG_TAG, String log) {
        if (DEBUG)
            Log.d(LOG_TAG, log);
    }

    public static final void e(String LOG_TAG, String log) {
        if (log != null)
            if (DEBUG)
                Log.e(LOG_TAG, "" + log);
    }

    public static final void d(String LOG_TAG, String log) {
        if (log != null)
            if (DEBUG)
                Log.d(LOG_TAG, "" + log);
    }

    public static final void w(String LOG_TAG, String log) {
        if (log != null)
            if (DEBUG)
                Log.w(LOG_TAG, "" + log);
    }

    public static final void log(String LOG_TAG, String log) {
        if (DEBUG)
            Log.i(LOG_TAG, log);
    }


    public static final void logv(String LOG_TAG, String log) {
        if (DEBUG)
            Log.v(LOG_TAG, log);
    }

    public static final void warn(String LOG_TAG, String log) {
        if (DEBUG)
            Log.w(LOG_TAG, log);
    }

    public static final void log(Object obj , String log){
        if(DEBUG){
            String tag = obj.getClass().getSimpleName();
            TLog.log(tag , log);
        }
    }
    
}
