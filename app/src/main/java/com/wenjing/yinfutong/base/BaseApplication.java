package com.wenjing.yinfutong.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.StringRes;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.utils.TDevice;
import com.wenjing.yinfutong.utils.TextUtil;

public class BaseApplication extends MultiDexApplication {

    private static final long TOAST_BETWEEN_TIME = 2000;

    private static long lastToastTime;//上一次toast显示的时间
    static Resources resources;
    static Context context;
    public static Toast toast;
    private static boolean sIsAtLeastGB;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context = getApplicationContext();
        resources = context.getResources();

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        String PREF_NAME = "silverlock_pref";
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static Resources resources() {
        return resources;
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) context;
    }

    public static final int TOAST_POSITION = Gravity.CENTER;//Gravity.FILL_HORIZONTAL | Gravity.TOP

    public static void showToast(@StringRes int message) {
        makeText(message);
    }

    public static void showToast(String message) {
        if (!TextUtil.isEmpty(message)) {
            makeText(message, Toast.LENGTH_SHORT);
        }
    }

    //    Gravity.FILL_HORIZONTAL| Gravity.TOP
    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, TOAST_POSITION);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, TOAST_POSITION);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, TOAST_POSITION, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, TOAST_POSITION);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        if (message != null && !"".equals(message)) {
            long time = System.currentTimeMillis();
            if (Math.abs(time - lastToastTime) > TOAST_BETWEEN_TIME) {
                View view = LayoutInflater.from(context()).inflate(
                        R.layout.view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                if (toast == null) {
                    toast = new Toast(context());
                }
                toast.setView(view);
                toast.setGravity(gravity, 0,
                        TDevice.getActionBarHeight(context()));

                toast.setDuration(duration);
                toast.show();

                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    public static void makeText(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.context(), message, duration);
            toast.show();
            lastToastTime = System.currentTimeMillis();
            return;
        }
        long time = System.currentTimeMillis();
        if (time - lastToastTime < toast.getDuration()) {
            toast.cancel();
        }

        toast.setText(message);
        toast.setDuration(duration);
        toast.show();
        lastToastTime = System.currentTimeMillis();


    }

    public static void makeText(@StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
